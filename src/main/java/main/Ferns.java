package main;

import fileio.PlantInput;

public class Ferns extends Plant {
    private static final double YOUNG_OXYGEN = 0.2;
    private static final double MATURE_OXYGEN = 0.7;
    private static final double OLD_OXYGEN = 0.4;
    private static final double ATTACK_POSSIBILITY = 30.0;
    private static final double DIVISOR = 100.0;

    public Ferns(final String name, final double mass) {
        super(name, mass, "Ferns");
    }

    public Ferns(final PlantInput input) {
        super(input, "Ferns");
    }

    /**
     * Returns the amount of oxygen produced by the ferns depending on its age.
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
