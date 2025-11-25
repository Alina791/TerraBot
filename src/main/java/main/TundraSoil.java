package main;

import fileio.AirInput;
import fileio.SoilInput;

import javax.swing.*;

public class TundraSoil extends Soil {
    private double permafrostDepth;

    public TundraSoil(String name, double mass, double nitrogen,
                         double waterReetention, double soilpH,
                         double oragnicMatter, double permafrostDepth) {
        super(name, mass, "ForestSoil", nitrogen, waterReetention, soilpH, oragnicMatter);
        this.permafrostDepth = permafrostDepth;
    }

    public TundraSoil(SoilInput input) {
        super(input);
        permafrostDepth = input.getPermafrostDepth();
        this.soilQuality = calculateSoilQuality();
    }

    public double calculateSoilQuality() {
        double score = getNitrogen() * 0.7 + getOrganicMatter() * 0.5
                - permafrostDepth * 1.5;
        return Math.round(Math.max(0, Math.min(100, score)) * 100.0) / 100.0;
    }
    public double getPermafrostDepth() { return permafrostDepth; }
}
