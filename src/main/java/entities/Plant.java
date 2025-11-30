package entities;

import fileio.PlantInput;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public abstract class Plant extends Entity {
    private String type;
    private String age;
    private double growth;
    private boolean scanned = false;

    public Plant(final String name, final double mass, final String type) {
        super(name, mass);
        this.type = type;
        this.age = "young";
        this.growth = 0.0;
    }

    public Plant(final PlantInput input, final String type) {
        super(input.getName(), input.getMass());
        this.type = type;
        this.age = "young";
        this.growth = 0.0;
    }

    /** Updates the plant's age depending on their growth. */
    public void grow(final double oxygenEnvironment) {
        growth = growth + oxygenEnvironment;
        if ("young".equals(age) && growth >= 1.0) {
            age = "mature";
            growth = 0.0;
        } else if ("mature".equals(age) && growth >= 1.0) {
            age = "old";
            growth = 0.0;
        } else if ("old".equals(age) && growth >= 1.0) {
            age = "dead";
        }
    }

    /** Calculates the probability for the robot to get attacked
     * by this plant.
     */
    public abstract double calculatePlantPossibility();

    /** Returns the possibility for the robot to get attacked
     * by this plant.
     */
    public double getPlantPossibility() {
        return calculatePlantPossibility();
    }

    /** Checks if the plant is dead. */
    public boolean isDead() {
        return "dead".equals(age);
    }

    /** Checks whether the plant was scanned. */
    public boolean getScanned() {
        return scanned;
    }

    /**
     * Returns the amount of oxygen produced by the plant
     */
    public abstract double getOxygen();
}
