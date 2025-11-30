package animals;

import fileio.AnimalInput;
import entities.Animal;

public class Parasite extends Animal {
    private static final double MAX_ATTACK = 100.0;
    private static final double ATTACK_POSSIBILITY = 10.0;
    private static final double DIVISOR = 10.0;

    public Parasite(final String name, final double mass) {
        super(name, mass, "Parasites");
    }

    public Parasite(final AnimalInput input) {
        super(input);
    }

    /**
     * Allows this parasite to harm an animal
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
