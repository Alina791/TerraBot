package plants;

import fileio.PlantInput;
import entities.Plant;

public class Algae extends Plant {
    private static final double YOUNG_OXYGEN = 0.7;
    private static final double MATURE_OXYGEN = 1.2;
    private static final double OLD_OXYGEN = 0.9;
    private static final double ATTACK_POSSIBILITY = 20.0;
    private static final double DIVISOR = 100.0;

    public Algae(final String name, final double mass) {
        super(name, mass, "Algae");
    }

    public Algae(final PlantInput input) {
        super(input, "Algae");
    }

    /**
     * Returns the amount of oxygen produced by the algae depending on its age.
     */
    public double getOxygen() {
        return switch (getAge()) {
            case "young" -> YOUNG_OXYGEN;
            case "mature" -> MATURE_OXYGEN;
            case "old" -> OLD_OXYGEN;
            default -> 0;
        };
    }

    /** Calculates the possibility for the robot to get attacked
     * by this plant.
     */
    public double calculatePlantPossibility() {
        return ATTACK_POSSIBILITY / DIVISOR;
    }
}
