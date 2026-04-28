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
        // TODO: return id
        return id;
    }

    public void setId(int id) {
        // TODO: set id
        this.id = id;
    }

    public String getPackageName() {
        // TODO: return packageName
        return packageName;
    }

    public void setPackageName(String packageName) {
        // TODO: set packageName
        this.packageName = packageName;
    }

    public int getDuration() {
        // TODO: return duration
        return duration;
    }

    public void setDuration(int duration) {
        // TODO: set duration (in months)
        this.duration = duration;
    }

    public double getPrice() {
        // TODO: return price
        return price;
    }

    public void setPrice(double price) {
        // TODO: set price
        this.price = price;
    }

    public String getDescription() {
        // TODO: return description
        return description;
    }

    public void setDescription(String description) {
        // TODO: set description
        this.description = description;
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
