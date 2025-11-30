package main;

import fileio.SoilInput;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ForestSoil extends Soil {
    private double leafLitter;

    private static final double ROUND_FACTOR = 100.0;
    private static final double NITROGEN_AUX = 1.2;
    private static final double WATER_RETENTION_AUX = 1.5;
    private static final double ORGANIC_MATTER_AUX = 2;
    private static final double LEAF_LITTER_AUX = 0.3;
    private static final double LEAF_LITTER_STUCK = 0.4;
    private static final double WATER_RETENTION_STUCK = 0.6;
    private static final double POSSIBILITY_STUCK_FOREST = 80.0;
    private static final double MAX_QUALITY = 100.0;
    private static final double MIN_QUALITY = 0.0;

    public ForestSoil(final String name, final double mass, final double nitrogen,
                      final double waterRetention, final double soilPH,
                      final double organicMatter, final double leafLitter) {
        super(name, mass, "ForestSoil", nitrogen, waterRetention, soilPH, organicMatter);
        this.leafLitter = leafLitter;
    }

    public ForestSoil(final SoilInput input) {
        super(input);
        leafLitter = input.getLeafLitter();
        this.soilQuality = calculateSoilQuality();
    }

    /** Calculates the quality of this soil. */
    public double calculateSoilQuality() {
        double score = getNitrogen() * NITROGEN_AUX + getOrganicMatter() * ORGANIC_MATTER_AUX
                       + getWaterRetention() * WATER_RETENTION_AUX
                       + leafLitter * LEAF_LITTER_AUX;
        return Math.round(Math.max(MIN_QUALITY, Math.min(MAX_QUALITY, score))
                          * ROUND_FACTOR) / ROUND_FACTOR;
    }

    /** Calculates the possibility for the robot to get stuck in soil. */
    public double calculatePossibilityToGetStuckInSoil() {
        return (getWaterRetention() * WATER_RETENTION_STUCK + leafLitter
                * LEAF_LITTER_STUCK) / POSSIBILITY_STUCK_FOREST * ROUND_FACTOR;
    }
}
