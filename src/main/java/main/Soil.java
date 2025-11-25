package main;

import fileio.AirInput;
import fileio.SoilInput;

public abstract class Soil extends Entity {
    private String type;
    private double nitrogen;
    private double waterRetention;
    private double soilpH;
    private double organicMatter;
    protected double soilQuality;

    public Soil(SoilInput input) {
        this.type = input.getType();
        setMass(input.getMass());
        setName(input.getName());
        nitrogen = (input.getNitrogen());
        waterRetention = (input.getWaterRetention());
        soilpH = (input.getSoilpH());
        organicMatter = (input.getOrganicMatter());
        this.soilQuality = calculateSoilQuality();
    }

    public Soil(String name, double mass, String type,
                double nitrogen, double waterRetention,
                double soilpH, double organicMatter) {
        super(name, mass);
        this.type = type;
        this.nitrogen = nitrogen;
        this.waterRetention = waterRetention;
        this.soilpH = soilpH;
        this.organicMatter = organicMatter;
        this.soilQuality = calculateSoilQuality();
    }

    public abstract double calculateSoilQuality();

    public String getType() {
        return type;
    }

    public double getNitrogen() {
        return nitrogen;
    }

    public double getWaterRetention() {
        return waterRetention;
    }

    public double getSoilpH() {
        return soilpH;
    }

    public double getOrganicMatter() {
        return organicMatter;
    }

    public double getSoilQuality() {
        return soilQuality;
    }

    public double getWaterLogging() { return Double.NaN; }
    public double getLeafLitter() { return Double.NaN; }
    public double getSalinity() { return Double.NaN; }
    public double getRootDensity() { return Double.NaN; }
    public double getPermafrostDepth() { return Double.NaN; }


    public String getSoilQualityLabel() {
        if (soilQuality >= 70.0) {
            return "good";
        } else if (soilQuality >= 40.0) {
            return "moderate";
        } else {
            return "poor";
        }
    }
}
