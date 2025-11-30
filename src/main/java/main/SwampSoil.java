package main;

import fileio.SoilInput;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class SwampSoil extends Soil {
    private double waterLogging;

    private static final double ROUND_FACTOR = 100.0;
    private static final double NITROGEN_AUX = 1.1;
    private static final double ORGANIC_MATTER_AUX = 2.2;
    private static final double WATER_LOGGING_AUX = 5;
    private static final double POSSIBILITY_STUCK_SWAMP = 10.0;
    private static final double MAX_QUALITY = 100.0;
    private static final double MIN_QUALITY = 0.0;

    public SwampSoil(final String name, final double mass, final double nitrogen,
                      final double waterRetention, final double soilPH,
                      final double organicMatter, final double waterLogging) {
        super(name, mass, "ForestSoil", nitrogen, waterRetention, soilPH, organicMatter);
        this.waterLogging = waterLogging;
    }

    public SwampSoil(final SoilInput input) {
        super(input);
        waterLogging = input.getWaterLogging();
        this.soilQuality = calculateSoilQuality();
    }

    /** Calculates the quality of this soil. */
    public double calculateSoilQuality() {
        double score = getNitrogen() * NITROGEN_AUX + getOrganicMatter() * ORGANIC_MATTER_AUX
                      - waterLogging * WATER_LOGGING_AUX;
        return Math.round(Math.max(MIN_QUALITY, Math.min(MAX_QUALITY, score))
                          * ROUND_FACTOR) / ROUND_FACTOR;
    }

    /** Calculates the possibility for the robot to get stuck in soil. */
    public double calculatePossibilityToGetStuckInSoil() {
        return waterLogging * POSSIBILITY_STUCK_SWAMP;
    }
}
