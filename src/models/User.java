package models;

public class User {
    private String name;
    private int age;
    private String id;
    private double totalConsumption;

    public User(String name, int age, String id) {
        this.name = name;
        this.age = age;
        this.id = id;
    }

    // Getters et Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public double getTotalConsumption() {
        return totalConsumption;
    }
    public void setTotalConsumption(double totalConsumption) {
        this.totalConsumption = totalConsumption;
    }
}
