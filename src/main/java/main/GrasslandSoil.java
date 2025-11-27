package main;

import fileio.SoilInput;

import javax.swing.*;

public class GrasslandSoil extends Soil {
    private double rootDensity;

    public GrasslandSoil(String name, double mass, double nitrogen,
                     double waterReetention, double soilpH,
                     double oragnicMatter, double rootDensity) {
        super(name, mass, "ForestSoil", nitrogen, waterReetention, soilpH, oragnicMatter);
        this.rootDensity = rootDensity;
    }

    public GrasslandSoil(SoilInput input) {
        super(input);
        rootDensity = input.getRootDensity();
        this.soilQuality = calculateSoilQuality();
    }

    public double calculateSoilQuality() {
        double score = getNitrogen() * 1.3 + getOrganicMatter() * 1.5
                + rootDensity * 0.8;
        return Math.round(Math.max(0, Math.min(100, score)) * 100.0) / 100.0;
    }
    public double getRootDensity() { return rootDensity; }
    public double calculatePossibilityToGetStuckInSoil() {
        return ((50.0 - rootDensity) + getWaterRetention() * 0.5) / 75.0 * 100.0;
    }
}
