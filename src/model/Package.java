package model;

public class Package {

    private int id;
    private String packageName;
    private int duration; // duration in months
    private double price;
    private String description;
    private int status;

    public Package() {
        this.id = 0;
        this.packageName = "";
        this.duration = 0;
        this.price = 0.0;
        this.description = "";
        this.status = 1;
    }

    public Package(int id, String packageName, int duration, double price,
                   String description, int status) {
        this.id = id;
        this.packageName = packageName;
        this.duration = duration;
        this.price = price;
        this.description = description;
        this.status = status;
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusLabel() {
        return status == 1 ? "ACTIVE" : "INACTIVE";
    }

    @Override
    public String toString() {
        return "Package{" +
                "id=" + id +
                ", packageName='" + packageName + '\'' +
                ", duration=" + duration +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", status=" + getStatusLabel() +
                '}';
    }
}
