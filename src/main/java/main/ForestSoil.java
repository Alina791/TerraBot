package main;

import fileio.SoilInput;

public class ForestSoil extends Soil {
    private double leafLitter;

    public ForestSoil(String name, double mass, double nitrogen,
                      double waterRetention, double soilpH,
                      double oragnicMatter, double leafLitter) {
        super(name, mass, "ForestSoil", nitrogen, waterRetention, soilpH, oragnicMatter);
        this.leafLitter = leafLitter;
    }

    public ForestSoil(SoilInput input) {
        super(input);
        leafLitter = input.getLeafLitter();
        this.soilQuality = calculateSoilQuality();
    }

    public double calculateSoilQuality() {
        double score = getNitrogen() * 1.2 + getOrganicMatter() * 2 + getWaterRetention() * 1.5 +
                leafLitter * 0.3;
        return Math.round(Math.max(0, Math.min(100, score)) * 100.0) / 100.0;
    }
    public double getLeafLitter() { return leafLitter; }
    public double calculatePossibilityToGetStuckInSoil() {
        return (getWaterRetention() * 0.6 + leafLitter * 0.4) / 80.0 * 100.0;
    }
}
