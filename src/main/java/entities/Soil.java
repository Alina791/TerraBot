package entities;

import fileio.SoilInput;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public abstract class Soil extends Entity {
    private String type;
    private double nitrogen;
    private double waterRetention;
    private double soilpH;
    private double organicMatter;
    protected double soilQuality;

    private static final double ROUND_FACTOR = 100.0;
    private static final double GOOD_QUALITY = 70.0;
    private static final double MODERATE_QUALITY = 40.0;

    public Soil(final String name, final double mass, final String type,
                final double nitrogen, final double waterRetention,
                final double soilpH, final double organicMatter) {
        super(name, mass);
        this.type = type;
        this.nitrogen = nitrogen;
        this.waterRetention = waterRetention;
        this.soilpH = soilpH;
        this.organicMatter = organicMatter;
        this.soilQuality = calculateSoilQuality();
    }

    public Soil(final SoilInput input) {
        this.type = input.getType();
        setMass(input.getMass());
        setName(input.getName());
        nitrogen = (input.getNitrogen());
        waterRetention = (input.getWaterRetention());
        soilpH = (input.getSoilpH());
        organicMatter = (input.getOrganicMatter());
        this.soilQuality = calculateSoilQuality();
    }

    /** Calculates the quality of the soil. */
    public abstract double calculateSoilQuality();

    /** Gets the water logging only if supported by subclass. */
    public double getWaterLogging() {
        return Double.NaN;
    }

    /** Gets the leaf litter only if supported by subclass. */
    public double getLeafLitter() {
        return Double.NaN;
    }

    /** Gets the salinity only if supported by subclass. */
    public double getSalinity() {
        return Double.NaN;
    }

    /** Gets the root density only if supported by subclass. */
    public double getRootDensity() {
        return Double.NaN;
    }

    /** Gets the permafrost depth only if supported by subclass. */
    public double getPermafrostDepth() {
        return Double.NaN;
    }

    /** Returns a label describing soil quality. */
    public String getSoilQualityLabel() {
        if (soilQuality >= GOOD_QUALITY) {
            return "good";
        } else if (soilQuality >= MODERATE_QUALITY) {
            return "moderate";
        } else {
            return "poor";
        }
    }

    /** Calculates the possibility for the robot to get stuck in soil. */
    public abstract double calculatePossibilityToGetStuckInSoil();

    /** Returns the possibility for the robot to get stuck
     * in this soil.
     */
    public double getPossibilityToGetStuckInSoil() {
        return calculatePossibilityToGetStuckInSoil();
    }

    /** Calculates the water retention level after environment interactions  with water. */
    public void addWaterRetention(final double value) {
        waterRetention = Math.round((waterRetention + value) * ROUND_FACTOR) / ROUND_FACTOR;
        soilQuality = calculateSoilQuality();
    }
}
