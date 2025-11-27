package main;

import fileio.AirInput;

public class Tropical extends Air {
    private double co2Level;

    public Tropical(String name, double mass, double humidity,
                    double temperature, double oxygenLevel,
                    double co2Level) {
        super(name, mass, "TropicalAir", humidity, temperature, oxygenLevel);
        this.co2Level = co2Level;
        this.airQuality = calculateAirQuality();
        this.airToxicity = calculateAirToxicity();
    }

    public Tropical(AirInput input) {
        super(input);
        co2Level = input.getCo2Level();
        this.airQuality = calculateAirQuality();
        this.airToxicity = calculateAirToxicity();
    }

    public double calculateAirQuality() {
        double score = getOxygenLevel() * 2.0 + getHumidity() * 0.5
                - co2Level * 0.01;
        double normalized = Math.max(0.0, Math.min(100.0, score));
        return Math.round(normalized * 100.0) / 100.0;
    }

    public double calculateAirToxicity() {
        double quality = this.airQuality;
        double toxicityAQ = 100.0 * (1.0 - quality / 82.0);
        double normalized = Math.max(0.0, Math.min(100.0, toxicityAQ));
        return Math.round(normalized * 100.0) / 100.0;
    }
    public double getCo2Level() { return co2Level; }
}
