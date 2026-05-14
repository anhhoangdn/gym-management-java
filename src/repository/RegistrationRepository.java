package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Registration;
import model.RegistrationRow;

public class RegistrationRepository extends BaseRepository {

    private static final String BASE_JOIN_SQL =
            "SELECT r.id, r.userId, r.packageId, " +
            "u.firstName, u.lastName, u.email, " +
            "p.packageName, r.startDate, r.endDate, r.total, r.status " +
            "FROM registrations r " +
            "JOIN users u    ON u.id = r.userId " +
            "JOIN packages p ON p.id = r.packageId ";

    // Lấy tất cả đăng ký kèm thông tin user + package (để hiển thị bảng)
    public List<RegistrationRow> findAllJoined() {
        String sql = BASE_JOIN_SQL + "ORDER BY r.id";
        List<RegistrationRow> rows = new ArrayList<>();
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                rows.add(mapRow(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rows;
    }

    // Tìm đăng ký theo tên/email hội viên. field = "name" | "email"
    public List<RegistrationRow> searchByMemberKeyword(String field, String keyword) {
        String safeKeyword = keyword == null ? "" : keyword.trim();
        if (safeKeyword.isEmpty()) {
            return findAllJoined();
        }

        String pattern = "%" + safeKeyword + "%";
        String where;
        if ("email".equalsIgnoreCase(field)) {
            where = "WHERE u.email LIKE ? ";
        } else {
            where = "WHERE (u.firstName LIKE ? OR u.lastName LIKE ? " +
                    "OR CONCAT(u.firstName, ' ', u.lastName) LIKE ?) ";
        }

        String sql = BASE_JOIN_SQL + where + "ORDER BY r.id";
        List<RegistrationRow> rows = new ArrayList<>();
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
                rows.add(mapRow(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rows;
    }

    // Tìm các đăng ký ACTIVE hiệu lực của 1 user (chưa hết hạn).
    // Đăng ký hủy đã bị xóa cứng nên chỉ cần check endDate.
    public List<RegistrationRow> findActiveByUserId(int userId) {
        String sql = BASE_JOIN_SQL +
                "WHERE r.userId = ? AND r.endDate >= CURDATE() " +
                "ORDER BY r.id";
        List<RegistrationRow> rows = new ArrayList<>();
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                rows.add(mapRow(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rows;
    }

    private RegistrationRow mapRow(ResultSet rs) throws SQLException {
        return new RegistrationRow(
                rs.getInt("id"),
                rs.getInt("userId"),
                rs.getInt("packageId"),
                rs.getString("firstName"),
                rs.getString("lastName"),
                rs.getString("email"),
                rs.getString("packageName"),
                rs.getDate("startDate"),
                rs.getDate("endDate"),
                rs.getDouble("total"),
                rs.getInt("status")
        );
    }

    // Tạo đăng ký mới
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

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    // Tìm đăng ký theo id
    public Registration findById(int id) {
        String sql = "SELECT id, userId, packageId, startDate, endDate, total, status FROM registrations WHERE id = ?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapRegistration(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Lấy tất cả đăng ký
    public List<Registration> findAll() {
        String sql = "SELECT id, userId, packageId, startDate, endDate, total, status FROM registrations ORDER BY id";
        List<Registration> registrations = new ArrayList<>();
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                registrations.add(mapRegistration(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return registrations;
    }

    // Gia hạn gói tập
    public boolean renewRegistration(int id, java.util.Date newEndDate, double newTotal) {
        String sql = "UPDATE registrations SET endDate = ?, total = ?, status = 1 WHERE id = ?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setDate(1, new java.sql.Date(newEndDate.getTime()));
            ps.setDouble(2, newTotal);
            ps.setInt(3, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa đăng ký
    public boolean deleteRegistration(int id) {
        String sql = "DELETE FROM registrations WHERE id = ?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
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
