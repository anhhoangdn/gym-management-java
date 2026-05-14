package model;

import java.util.Date;

public class Registration {

    private int id;
    private int userId;
    private int packageId;
    private Date startDate;
    private Date endDate;
    private double total;
    private int status;

    public Registration() {
        this.id = 0;
        this.userId = 0;
        this.packageId = 0;
        this.startDate = null;
        this.endDate = null;
        this.total = 0.0;
        this.status = 1;
    }

    public Registration(int id, int userId, int packageId, Date startDate,
                        Date endDate, double total, int status) {
        this.id = id;
        this.userId = userId;
        this.packageId = packageId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.total = total;
        this.status = status;
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    @Override
    public String toString() {
        return "Registration{" +
                "id=" + id +
                ", userId=" + userId +
                ", packageId=" + packageId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", total=" + total +
                ", status=" + getStatusLabel() +
                '}';
    }
}
