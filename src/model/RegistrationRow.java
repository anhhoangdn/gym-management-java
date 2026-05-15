package model;

import java.util.Date;

/*
 DTO dùng cho bảng quản lý đăng ký vì gộp sẵn thông tin user + package
 để hiển thị để khỏi cần query lại.
 */
public class RegistrationRow {

    private final int id;
    private final int userId;
    private final int packageId;
    private final String userFirstName;
    private final String userLastName;
    private final String userEmail;
    private final String packageName;
    private final Date startDate;
    private final Date endDate;
    private final double total;
    private final int status;

    public RegistrationRow(int id, int userId, int packageId,
                           String userFirstName, String userLastName, String userEmail,
                           String packageName,
                           Date startDate, Date endDate, double total, int status) {
        this.id = id;
        this.userId = userId;
        this.packageId = packageId;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.userEmail = userEmail;
        this.packageName = packageName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.total = total;
        this.status = status;
    }

    public int getId() { return id; }
    public int getUserId() { return userId; }
    public int getPackageId() { return packageId; }
    public String getUserFirstName() { return userFirstName; }
    public String getUserLastName() { return userLastName; }
    public String getUserEmail() { return userEmail; }
    public String getPackageName() { return packageName; }
    public Date getStartDate() { return startDate; }
    public Date getEndDate() { return endDate; }
    public double getTotal() { return total; }
    public int getStatus() { return status; }

    public String getFullName() {
        String first = userFirstName == null ? "" : userFirstName.trim();
        String last = userLastName == null ? "" : userLastName.trim();
        String name = (first + " " + last).trim();
        return name.isEmpty() ? "(Không rõ)" : name;
    }

    public String getStatusLabel() {
        return status == 1 ? "ACTIVE" : "CANCELLED";
    }

    /** Đăng ký đã hết hạn theo thời gian thực (endDate < hôm nay). */
    public boolean isExpired() {
        if (endDate == null) {
            return false;
        }
        java.time.LocalDate end = new java.sql.Date(endDate.getTime()).toLocalDate();
        return end.isBefore(java.time.LocalDate.now());
    }

    /** Active hiệu lực = chưa hết hạn (đăng ký đã hủy thì admin xóa hẳn rồi). */
    public boolean isEffectivelyActive() {
        return !isExpired();
    }

    /** Nhãn hiển thị: ACTIVE / HẾT HẠN. */
    public String getEffectiveStatusLabel() {
        return isExpired() ? "HẾT HẠN" : "ACTIVE";
    }
}
