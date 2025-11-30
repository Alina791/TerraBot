package main;

import fileio.SoilInput;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class TundraSoil extends Soil {
    private double permafrostDepth;

    private static final double ROUND_FACTOR = 100.0;
    private static final double NITROGEN_AUX = 0.7;
    private static final double ORGANIC_MATTER_AUX = 0.5;
    private static final double PERMA_FROST_AUX = 1.5;
    private static final double POSSIBILITY_STUCK_TUNDRA = 50.0;
    private static final double MAX_QUALITY = 100.0;
    private static final double MIN_QUALITY = 0.0;

    public TundraSoil(final String name, final double mass, final double nitrogen,
                      final double waterRetention, final double soilPH,
                      final double organicMatter, final double permafrostDepth) {
        super(name, mass, "ForestSoil", nitrogen, waterRetention, soilPH, organicMatter);
        this.permafrostDepth = permafrostDepth;
    }

    public TundraSoil(final SoilInput input) {
        super(input);
        permafrostDepth = input.getPermafrostDepth();
        this.soilQuality = calculateSoilQuality();
    }

    /** Calculates the quality of this soil. */
    public double calculateSoilQuality() {
        double score = getNitrogen() * NITROGEN_AUX + getOrganicMatter() * ORGANIC_MATTER_AUX
                - permafrostDepth * PERMA_FROST_AUX;
        return Math.round(Math.max(MIN_QUALITY, Math.min(MAX_QUALITY, score))
                          * ROUND_FACTOR) / ROUND_FACTOR;
    }

    /** Calculates the possibility for the robot to get stuck in soil. */
    public double calculatePossibilityToGetStuckInSoil() {
        return (POSSIBILITY_STUCK_TUNDRA - permafrostDepth) / POSSIBILITY_STUCK_TUNDRA
                * ROUND_FACTOR;
    }
}
