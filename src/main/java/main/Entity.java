package main;

public abstract class Entity {
    private String name;
    private double mass;

    public Entity() {
        this.name = null;
        this.mass = 0;
    }
    public Entity(String name, double mass) {
        this.name = name;
        this.mass = mass;
    }

    public String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }

    public double getMass() {
        return mass;
    }

    protected void setMass(double mass) {
        this.mass = mass;
    }
}
