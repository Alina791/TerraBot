package plants;

import fileio.PlantInput;
import entities.Plant;

public class Mosses extends Plant {
    private static final double YOUNG_OXYGEN = 1.0;
    private static final double MATURE_OXYGEN = 1.5;
    private static final double OLD_OXYGEN = 1.2;
    private static final double ATTACK_POSSIBILITY = 40.0;
    private static final double DIVISOR = 100.0;

    public Mosses(final String name, final double mass) {
        super(name, mass, "Mosses");
    }
    public Mosses(final PlantInput input) {
        super(input, "Mosses");
    }

    /**
     * Returns the amount of oxygen produced by the mosses
     * depending on their age.
     */
    public double getOxygen() {
        return switch (getAge()) {
            case "young" -> YOUNG_OXYGEN;
            case "mature" -> MATURE_OXYGEN;
            case "old" -> OLD_OXYGEN;
            default -> 0.0;
        };
    }

    /** Calculates the possibility for the robot to get attacked
     * by this plant.
     */
    public double calculatePlantPossibility() {
        return ATTACK_POSSIBILITY / DIVISOR;
    }
}
