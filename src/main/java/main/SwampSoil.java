package main;

import fileio.SoilInput;

public class SwampSoil extends Soil {
    private double waterLogging;

    public double getWaterLogging() { return waterLogging; }

    public SwampSoil(String name, double mass, double nitrogen,
                      double waterReetention, double soilpH,
                      double oragnicMatter, double waterLogging) {
        super(name, mass, "ForestSoil", nitrogen, waterReetention, soilpH, oragnicMatter);
        this.waterLogging = waterLogging;
    }

    public SwampSoil(SoilInput input) {
        super(input);
        waterLogging = input.getWaterLogging();
        this.soilQuality = calculateSoilQuality();
    }

    public double calculateSoilQuality() {
        double score = getNitrogen() * 1.1 + getOrganicMatter() * 2.2
                - waterLogging * 5;
        return Math.round(Math.max(0, Math.min(100, score)) * 100.0) / 100.0;
    }
    public double calculatePossibilityToGetStuckInSoil() {
        return waterLogging * 10;
    }
}
