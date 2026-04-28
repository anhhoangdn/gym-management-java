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
        // TODO: return id
        return id;
    }

    public void setId(int id) {
        // TODO: set id
        this.id = id;
    }

    public int getUserId() {
        // TODO: return userId
        return userId;
    }

    public void setUserId(int userId) {
        // TODO: set userId
        this.userId = userId;
    }

    public int getPackageId() {
        // TODO: return packageId
        return packageId;
    }

    public void setPackageId(int packageId) {
        // TODO: set packageId
        this.packageId = packageId;
    }

    public Date getStartDate() {
        // TODO: return startDate
        return startDate;
    }

    public void setStartDate(Date startDate) {
        // TODO: set startDate
        this.startDate = startDate;
    }

    public Date getEndDate() {
        // TODO: return endDate
        return endDate;
    }

    public void setEndDate(Date endDate) {
        // TODO: set endDate
        this.endDate = endDate;
    }

    public double getTotal() {
        // TODO: return total
        return total;
    }

    public void setTotal(double total) {
        // TODO: set total
        this.total = total;
    }

    public int getStatus() {
        // TODO: return status
        return status;
    }

    public void setStatus(int status) {
        // TODO: set status
        this.status = status;
    }

    public String getStatusLabel() {
        return status == 1 ? "ACTIVE" : "CANCELLED";
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
