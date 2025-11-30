package main;

import fileio.SoilInput;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class GrasslandSoil extends Soil {
    private double rootDensity;

    private static final double ROUND_FACTOR = 100.0;
    private static final double NITROGEN_AUX = 1.3;
    private static final double ORGANIC_MATTER_AUX = 1.5;
    private static final double ROOT_DENSITY_AUX = 0.8;
    private static final double ROOT_DENSITY_STUCK = 50.0;
    private static final double WATER_RETENTION_STUCK = 0.5;
    private static final double POSSIBILITY_STUCK_GRASSLAND = 75.0;
    private static final double MAX_QUALITY = 100.0;
    private static final double MIN_QUALITY = 0.0;

    public GrasslandSoil(final String name, final double mass, final double nitrogen,
                         final double waterRetention, final double soilPH,
                         final double organicMatter, final double rootDensity) {
        super(name, mass, "ForestSoil", nitrogen, waterRetention, soilPH, organicMatter);
        this.rootDensity = rootDensity;
    }

    public GrasslandSoil(final SoilInput input) {
        super(input);
        rootDensity = input.getRootDensity();
        this.soilQuality = calculateSoilQuality();
    }

    /** Calculates the quality of this soil. */
    public double calculateSoilQuality() {
        double score = getNitrogen() * NITROGEN_AUX + getOrganicMatter() * ORGANIC_MATTER_AUX
                + rootDensity * ROOT_DENSITY_AUX;
        return Math.round(Math.max(MIN_QUALITY, Math.min(MAX_QUALITY, score)) * ROUND_FACTOR)
                / ROUND_FACTOR;
    }

    /** Calculates the possibility for the robot to get stuck in soil. */
    public double calculatePossibilityToGetStuckInSoil() {
        return ((ROOT_DENSITY_STUCK - rootDensity) + getWaterRetention() * WATER_RETENTION_STUCK)
                / POSSIBILITY_STUCK_GRASSLAND * ROUND_FACTOR;
    }
}
