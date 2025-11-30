package soils;

import fileio.SoilInput;
import lombok.Getter;
import lombok.Setter;
import entities.Soil;

@Getter
@Setter

public class DesertSoil extends Soil {
    private double salinity;

    private static final double ROUND_FACTOR = 100.0;
    private static final double NITROGEN_AUX = 0.5;
    private static final double WATER_RETENTION_AUX = 0.3;
    private static final double MAX_QUALITY = 100.0;
    private static final double MIN_QUALITY = 0.0;

    public DesertSoil(final String name, final double mass,
                      final double nitrogen, final double waterRetention,
                      final double soilPH, final double organicMatter,
                      final double salinity) {
        super(name, mass, "ForestSoil", nitrogen, waterRetention, soilPH, organicMatter);
        this.salinity = salinity;
    }

    public DesertSoil(final SoilInput input) {
        super(input);
        salinity = input.getSalinity();
        this.soilQuality = calculateSoilQuality();
    }

    /** Calculates the quality of this soil. */
    public double calculateSoilQuality() {
        double score = getNitrogen() * NITROGEN_AUX + getWaterRetention()
                        * WATER_RETENTION_AUX - salinity * 2;
        return Math.round(Math.max(MIN_QUALITY, Math.min(MAX_QUALITY, score))
                            * ROUND_FACTOR) / ROUND_FACTOR;
    }

    /** Calculates the possibility for the robot to get stuck in soil. */
    public double calculatePossibilityToGetStuckInSoil() {
        return (MAX_QUALITY - getWaterRetention() + salinity) / ROUND_FACTOR * ROUND_FACTOR;
    }
}
