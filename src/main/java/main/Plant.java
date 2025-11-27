package main;

import fileio.PlantInput;
public abstract class Plant extends Entity {
    private String type;
    private String age;
    private double growth;
    private double plantPossibility;

    public Plant(String name, double mass, String type) {
        super(name, mass);
        this.type = type;
        this.age = "young";
        this.growth = 0.0;
    }

    public Plant(PlantInput input) {
        this.type = input.getType();
        setMass(input.getMass());
        setName(input.getName());
    }

    public String getType() {
        return type;
    }

    public String getAge() {
        return age;
    }

    protected void setAge(String age) {
        this.age = age;
    }

    public void grow(double oxygen) {
        growth = growth + oxygen;
        if ("young".equals(age) && growth > 1.0) {
            age = "mature";
            growth = 0.0;
        } else if ("mature".equals(age) && growth > 1.0) {
            age = "old";
            growth = 0.0;
        } else if ("old".equals(age) && growth > 1.0) {
            age = "dead";
        }
    }
    public abstract double calculatePlantPossibility();
    public double getPlantPossibility() {
        return calculatePlantPossibility();
    }

    public boolean isDead() {
        return "dead".equals(age);
    }
}
