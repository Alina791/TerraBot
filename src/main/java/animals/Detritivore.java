package animals;

import fileio.AnimalInput;
import entities.Animal;

public class Detritivore extends Animal {
    private static final double MAX_ATTACK = 100.0;
    private static final double ATTACK_POSSIBILITY = 90.0;
    private static final double DIVISOR = 10.0;

    public Detritivore(final String name, final double mass) {
        super(name, mass, "Detritivores");
    }

    public Detritivore(final AnimalInput input) {
        super(input);
    }

    /** Calculates the probability for the robot to get attacked
     * by this animal.
     */
    public double calculateAttackProbability() {
        return (MAX_ATTACK - ATTACK_POSSIBILITY) / DIVISOR;
    }
}
