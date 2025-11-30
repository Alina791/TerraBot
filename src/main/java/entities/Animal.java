package entities;

import fileio.AnimalInput;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public abstract class Animal extends Entity {
    private String type;
    private String state;
    private double intakeRate;
    private boolean scanned = false;

    private static final double INTAKE_RATE = 0.08;

    public Animal(final String name, final double mass, final String type) {
        super(name, mass);
        this.type = type;
        this.state = "hungry";
        this.scanned = false;
        this.intakeRate = INTAKE_RATE;
    }

    public Animal(final AnimalInput input) {
        this.type = input.getType();
        setMass(input.getMass());
        setName(input.getName());
    }

    /** Calculates the probability for the robot to get attacked
     * by this animal.
     */
    public abstract double calculateAttackProbability();

    /** Returns the probability for the robot to get attacked
     * by this animal.
     */
    public double getAttackProbability() {
        return calculateAttackProbability();
    }
}
