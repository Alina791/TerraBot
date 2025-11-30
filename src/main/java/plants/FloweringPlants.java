package plants;

import fileio.PlantInput;
import entities.Plant;

public class FloweringPlants extends Plant {
    private static final double YOUNG_OXYGEN = 6.2;
    private static final double MATURE_OXYGEN = 6.7;
    private static final double OLD_OXYGEN = 6.4;
    private static final double ATTACK_POSSIBILITY = 90.0;
    private static final double DIVISOR = 100.0;

    public FloweringPlants(final String name, final double mass) {
        super(name, mass, "FloweringPlants");
    }
    public FloweringPlants(final PlantInput input) {
        super(input, "FloweringPlants");
    }

    /**
     * Returns the amount of oxygen produced by the flowering plants
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
