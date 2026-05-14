package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Package;

public class PackageRepository extends BaseRepository {

    // Thêm gói tập mới
    public int createPackage(Package gymPackage) {
        String sql = "INSERT INTO packages(packageName, duration, price, description, status) VALUES(?, ?, ?, ?, ?)";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            ps.setString(1, gymPackage.getPackageName());
            ps.setInt(2, gymPackage.getDuration());
            ps.setDouble(3, gymPackage.getPrice());
            ps.setString(4, gymPackage.getDescription());
            ps.setInt(5, gymPackage.getStatus());
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

    // Tìm theo ID
    public Package findById(int id) {
        String sql = "SELECT id, packageName, duration, price, description, status FROM packages WHERE id = ?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapPackage(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Lấy tất cả gói tập
    public List<Package> findAll() {
        String sql = "SELECT id, packageName, duration, price, description, status FROM packages ORDER BY id";
        List<Package> packages = new ArrayList<>();
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                packages.add(mapPackage(rs));
            }
        } catch (Exception e) {
            System.out.println("Lỗi lấy danh sách package: " + e.getMessage());
        }
        return packages;
    }

    // Lấy gói tập đang hoạt động
    public List<Package> findActivePackages() {
        String sql = "SELECT id, packageName, duration, price, description, status FROM packages WHERE status = 1 ORDER BY id";
        List<Package> packages = new ArrayList<>();
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                packages.add(mapPackage(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return packages;
    }

    // Cập nhật thông tin gói
    public boolean updatePackage(Package gymPackage) {
        String sql = "UPDATE packages SET packageName = ?, duration = ?, price = ?, description = ?, status = ? WHERE id = ?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, gymPackage.getPackageName());
            ps.setInt(2, gymPackage.getDuration());
            ps.setDouble(3, gymPackage.getPrice());
            ps.setString(4, gymPackage.getDescription());
            ps.setInt(5, gymPackage.getStatus());
            ps.setInt(6, gymPackage.getId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updatePackageStatus(int id, int status) {
        String sql = "UPDATE packages SET status = ? WHERE id = ?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, status);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa gói tập
    public boolean deletePackage(int id) {
        String sql = "DELETE FROM packages WHERE id = ?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private Package mapPackage(ResultSet rs) throws SQLException {
        return new Package(
                rs.getInt("id"),
                rs.getString("packageName"),
                rs.getInt("duration"),
                rs.getDouble("price"),
                rs.getString("description"),
                rs.getInt("status")
        );
    }
}
