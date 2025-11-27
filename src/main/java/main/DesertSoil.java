package main;

import fileio.AirInput;
import fileio.SoilInput;

public class DesertSoil extends Soil {
    private double salinity;

    public DesertSoil(String name, double mass, double nitrogen,
                     double waterReetention, double soilpH,
                     double oragnicMatter, double salinity) {
        super(name, mass, "ForestSoil", nitrogen, waterReetention, soilpH, oragnicMatter);
        this.salinity = salinity;
    }

    public DesertSoil(SoilInput input) {
        super(input);
        salinity = input.getSalinity();
        this.soilQuality = calculateSoilQuality();
    }

    public double calculateSoilQuality() {
        double score = getNitrogen() * 0.5 + getWaterRetention() * 0.3
                - salinity * 2;
        return Math.round(Math.max(0, Math.min(100, score)) * 100.0) / 100.0;
    }
    public double getSalinity() { return salinity; }
    public double calculatePossibilityToGetStuckInSoil() {
        return (100.0 - getWaterRetention() + salinity) / 100.0 * 100.0;
    }
}
