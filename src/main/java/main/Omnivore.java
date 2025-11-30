package main;

import fileio.AnimalInput;

public class Omnivore extends Animal {
    private static final double MAX_ATTACK = 100.0;
    private static final double ATTACK_POSSIBILITY = 60.0;
    private static final double DIVISOR = 10.0;

    public Omnivore(final String name, final double mass) {
        super(name, mass, "Omnivores");
    }
    public Omnivore(final AnimalInput input) {
        super(input);
    }

    /** Calculates the probability for the robot to get attacked
     * by this animal.
     */
    public double calculateAttackProbability() {
        return (MAX_ATTACK - ATTACK_POSSIBILITY) / DIVISOR;
    }
}
