package main;

import fileio.AirInput;

public class Polar extends Air {
    private double iceCrystalConcentration;

    public Polar(String name, double mass, double humidity,
                    double temperature, double oxygenLevel,
                    double iceCrystalConcentration) {
        super(name, mass, "PolarAir", humidity, temperature, oxygenLevel);
        this.iceCrystalConcentration = iceCrystalConcentration;
        this.airQuality = calculateAirQuality();
        this.airToxicity = calculateAirToxicity();
    }

    public Polar(AirInput input) {
        super(input);
        iceCrystalConcentration = input.getIceCrystalConcentration();
        this.airQuality = calculateAirQuality();
        this.airToxicity = calculateAirToxicity();
    }

    public double calculateAirQuality() {
        double score = getOxygenLevel() * 2.0 + (100.0 - Math.abs(getTemperature()))
                - iceCrystalConcentration * 0.05;
        double normalized = Math.max(0, Math.min(100.0, score));
        return Math.round(normalized * 100.0) / 100.0;
    }

    public double calculateAirToxicity() {
        double quality = this.airQuality;
        double toxicityAQ = 100.0 * (1.0 - quality / 142.0);
        double normalized = Math.max(0.0, Math.min(100.0, toxicityAQ));
        return Math.round(normalized * 100.0) / 100.0;
    }
    public double getIceCrystalConcentration() { return iceCrystalConcentration; }
}
