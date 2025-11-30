package main;

import fileio.AnimalInput;

public class Carnivore extends Animal {
    private static final double MAX_ATTACK = 100.0;
    private static final double ATTACK_POSSIBILITY = 30.0;
    private static final double DIVISOR = 10.0;

    public Carnivore(final String name, final double mass) {
        super(name, mass, "Carnivores");
    }
    public Carnivore(final AnimalInput input) {
        super(input);
    }

    /**
     * Allows this carnivore to eat a prey animal
     */
    public void eat(final Animal prey) {
        if (prey != null) {

            double preyMass = prey.getMass();
            setMass(getMass() + preyMass);

            prey.setState("dead");

            setState("well-fed");
        }
    }

    /** Calculates the probability for the robot to get attacked
     * by this animal.
     */
    public double calculateAttackProbability() {
        return (MAX_ATTACK - ATTACK_POSSIBILITY) / DIVISOR;
    }
}
