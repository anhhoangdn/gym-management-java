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

    // Tạo người dùng mới (Member hoặc Admin)
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

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return -1;
        } catch (Exception e) {
            System.out.println("Lỗi khi thêm user: " + e.getMessage());
            e.printStackTrace();
            return -1;
        }
    }

    public User findById(int id) {
        String sql = "SELECT id, firstName, lastName, email, phoneNumber, password, type FROM users WHERE id = ?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapUser(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public User findByEmail(String email) {
        String sql = "SELECT id, firstName, lastName, email, phoneNumber, password, type FROM users WHERE email = ?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapUser(rs);
            }
        } catch (Exception e) {
            System.out.println("Lỗi tìm email: " + e.getMessage());
        }
        return null;
    }

    // Tìm hội viên theo tên hoặc email (LIKE %kw%). field = "name" | "email"
    public List<Member> searchMembers(String field, String keyword) {
        String safeKeyword = keyword == null ? "" : keyword.trim();
        if (safeKeyword.isEmpty()) {
            return findAllMembers();
        }

        String pattern = "%" + safeKeyword + "%";
        String sql;
        if ("email".equalsIgnoreCase(field)) {
            sql = "SELECT id, firstName, lastName, email, phoneNumber, password, type FROM users " +
                  "WHERE type = 1 AND email LIKE ? ORDER BY id";
        } else {
            sql = "SELECT id, firstName, lastName, email, phoneNumber, password, type FROM users " +
                  "WHERE type = 1 AND (firstName LIKE ? OR lastName LIKE ? " +
                  "OR CONCAT(firstName, ' ', lastName) LIKE ?) ORDER BY id";
        }

        List<Member> members = new ArrayList<>();
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            if ("email".equalsIgnoreCase(field)) {
                ps.setString(1, pattern);
            } else {
                ps.setString(1, pattern);
                ps.setString(2, pattern);
                ps.setString(3, pattern);
            }

            ResultSet rs = ps.executeQuery();
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return members;
    }

    // Lấy danh sách thành viên
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return members;
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
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteUser(int id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Đăng nhập admin
    public Admin authenticateAdmin(String email, String rawPassword) {
        String sql = "SELECT id, firstName, lastName, email, phoneNumber, password, type FROM users WHERE email = ? AND type = 0";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                String storedPassword = rs.getString("password");
                if (PasswordUtil.verifyPassword(rawPassword, storedPassword)) {
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
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Hàm phụ dùng để map dữ liệu từ ResultSet sang Object
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
