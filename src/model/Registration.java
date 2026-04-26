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
        // TODO: initialize default values if needed
    }

    public Registration(int id, int userId, int packageId, Date startDate,
                        Date endDate, double total, int status) {
        // TODO: assign fields
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
}
