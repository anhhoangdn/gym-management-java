package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Admin;
import model.Member;
import model.User;
import util.PasswordUtil;

public class UserRepository extends BaseRepository {

    public int createUser(User user) {
        String sql = "INSERT INTO users(firstName, lastName, email, phoneNumber, password, type) VALUES(?, ?, ?, ?, ?, ?)";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPhoneNumber());
            ps.setString(5, PasswordUtil.hashPassword(user.getPassword()));
            ps.setInt(6, user.getType());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
            return -1;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create user", e);
        }
    }

    public User findById(int id) {
        String sql = "SELECT id, firstName, lastName, email, phoneNumber, password, type FROM users WHERE id = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapUser(rs);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find user by id", e);
        }
    }

    public User findByEmail(String email) {
        String sql = "SELECT id, firstName, lastName, email, phoneNumber, password, type FROM users WHERE email = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapUser(rs);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find user by email", e);
        }
    }

    public List<Member> findAllMembers() {
        String sql = "SELECT id, firstName, lastName, email, phoneNumber, password, type FROM users WHERE type = 1 ORDER BY id";
        List<Member> members = new ArrayList<>();

           try (Connection con = getConnection();
               PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                members.add(new Member(
                        rs.getInt("id"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("email"),
                        rs.getString("phoneNumber"),
                        rs.getString("password"),
                        rs.getInt("type")
                ));
            }
            return members;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to list members", e);
        }
    }

    public boolean updateUser(User user, boolean updatePassword) {
        String sqlWithoutPassword = "UPDATE users SET firstName = ?, lastName = ?, email = ?, phoneNumber = ?, type = ? WHERE id = ?";
        String sqlWithPassword = "UPDATE users SET firstName = ?, lastName = ?, email = ?, phoneNumber = ?, password = ?, type = ? WHERE id = ?";

           try (Connection con = getConnection();
               PreparedStatement ps = con.prepareStatement(updatePassword ? sqlWithPassword : sqlWithoutPassword)) {
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPhoneNumber());

            if (updatePassword) {
                ps.setString(5, PasswordUtil.hashPassword(user.getPassword()));
                ps.setInt(6, user.getType());
                ps.setInt(7, user.getId());
            } else {
                ps.setInt(5, user.getType());
                ps.setInt(6, user.getId());
            }

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update user", e);
        }
    }

    public boolean deleteUser(int id) {
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete user", e);
        }
    }

    public Admin authenticateAdmin(String email, String rawPassword) {
        String sql = "SELECT id, firstName, lastName, email, phoneNumber, password, type FROM users WHERE email = ? AND type = 0";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }

                String storedPassword = rs.getString("password");
                if (!PasswordUtil.verifyPassword(rawPassword, storedPassword)) {
                    return null;
                }

                return new Admin(
                        rs.getInt("id"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("email"),
                        rs.getString("phoneNumber"),
                        storedPassword,
                        rs.getInt("type")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to authenticate admin", e);
        }
    }

    private User mapUser(ResultSet rs) throws SQLException {
        int type = rs.getInt("type");
        if (type == 0) {
            return new Admin(
                    rs.getInt("id"),
                    rs.getString("firstName"),
                    rs.getString("lastName"),
                    rs.getString("email"),
                    rs.getString("phoneNumber"),
                    rs.getString("password"),
                    type
            );
        }

        return new Member(
                rs.getInt("id"),
                rs.getString("firstName"),
                rs.getString("lastName"),
                rs.getString("email"),
                rs.getString("phoneNumber"),
                rs.getString("password"),
                type
        );
    }
}
