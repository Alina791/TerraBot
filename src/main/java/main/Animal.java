package main;

import fileio.AnimalInput;
import fileio.PlantInput;

public abstract class Animal extends Entity{
    private String type;
    private String state;
    private boolean scanned;
    private double intakeRate;
    private double attackProbability;

    public Animal(String name, double mass, String type) {
        super(name, mass);
        this.type = type;
        this.state = "hungry";
        this.scanned = false;
        this.intakeRate = 0.08;
    }

    public Animal(AnimalInput input) {
        this.type = input.getType();
        setMass(input.getMass());
        setName(input.getName());
    }

    public String getType() {
        return type;
    }

    protected void setType(String type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public boolean isScanned(boolean scanned) {
        return scanned;
    }

    public double getIntakeRate() {
        return intakeRate;
    }

    protected void setIntakeRate(double intakeRate) {
        this.intakeRate = intakeRate;
    }

    public void feed(double food) {
        setMass(getMass() + food);
        this.state = "well_fed";
    }

    public boolean Fertilizer() {
        return "well-fed".equals(state);
    }

    public abstract double calculateAttackProbability();
    public double getAttackProbability() {
        return calculateAttackProbability();
    }

}
