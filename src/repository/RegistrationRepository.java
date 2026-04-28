package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Registration;

public class RegistrationRepository extends BaseRepository {

    public int createRegistration(Registration registration) {
        String sql = "INSERT INTO registrations(userId, packageId, startDate, endDate, total, status) VALUES(?, ?, ?, ?, ?, ?)";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, registration.getUserId());
            ps.setInt(2, registration.getPackageId());
            ps.setDate(3, new java.sql.Date(registration.getStartDate().getTime()));
            ps.setDate(4, new java.sql.Date(registration.getEndDate().getTime()));
            ps.setDouble(5, registration.getTotal());
            ps.setInt(6, registration.getStatus());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
            return -1;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create registration", e);
        }
    }

    public Registration findById(int id) {
        String sql = "SELECT id, userId, packageId, startDate, endDate, total, status FROM registrations WHERE id = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRegistration(rs);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find registration by id", e);
        }
    }

    public List<Registration> findAll() {
        String sql = "SELECT id, userId, packageId, startDate, endDate, total, status FROM registrations ORDER BY id";
        List<Registration> registrations = new ArrayList<>();

           try (Connection con = getConnection();
               PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                registrations.add(mapRegistration(rs));
            }
            return registrations;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to list registrations", e);
        }
    }

    public List<Registration> findByUserId(int userId) {
        String sql = "SELECT id, userId, packageId, startDate, endDate, total, status FROM registrations WHERE userId = ? ORDER BY id";
        List<Registration> registrations = new ArrayList<>();

           try (Connection con = getConnection();
               PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    registrations.add(mapRegistration(rs));
                }
            }
            return registrations;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to list registrations by userId", e);
        }
    }

    public boolean updateRegistration(Registration registration) {
        String sql = "UPDATE registrations SET userId = ?, packageId = ?, startDate = ?, endDate = ?, total = ?, status = ? WHERE id = ?";

           try (Connection con = getConnection();
               PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, registration.getUserId());
            ps.setInt(2, registration.getPackageId());
            ps.setDate(3, new java.sql.Date(registration.getStartDate().getTime()));
            ps.setDate(4, new java.sql.Date(registration.getEndDate().getTime()));
            ps.setDouble(5, registration.getTotal());
            ps.setInt(6, registration.getStatus());
            ps.setInt(7, registration.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update registration", e);
        }
    }

    public boolean renewRegistration(int id, java.util.Date newEndDate, double newTotal) {
        String sql = "UPDATE registrations SET endDate = ?, total = ?, status = 1 WHERE id = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, new java.sql.Date(newEndDate.getTime()));
            ps.setDouble(2, newTotal);
            ps.setInt(3, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to renew registration", e);
        }
    }

    public boolean cancelRegistration(int id) {
        String sql = "UPDATE registrations SET status = 0 WHERE id = ?";

           try (Connection con = getConnection();
               PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to cancel registration", e);
        }
    }

    public boolean deleteRegistration(int id) {
        String sql = "DELETE FROM registrations WHERE id = ?";

           try (Connection con = getConnection();
               PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete registration", e);
        }
    }

    private Registration mapRegistration(ResultSet rs) throws SQLException {
        return new Registration(
                rs.getInt("id"),
                rs.getInt("userId"),
                rs.getInt("packageId"),
                rs.getDate("startDate"),
                rs.getDate("endDate"),
                rs.getDouble("total"),
                rs.getInt("status")
        );
    }
}
