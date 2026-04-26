package model;

public class Package {

    private int id;
    private String packageName;
    private int duration; // duration in months
    private double price;
    private String description;
    private int status;

    public Package() {
        // TODO: initialize default values if needed
    }

    public Package(int id, String packageName, int duration, double price,
                   String description, int status) {
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
}
