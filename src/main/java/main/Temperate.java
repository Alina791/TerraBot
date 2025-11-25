package main;

import fileio.AirInput;

public class Temperate extends Air {
    private double pollenLevel;

    public Temperate(String name, double mass, double humidity,
                 double temperature, double oxygenLevel,
                 double pollenLevel) {
        super(name, mass, "TemperateAir", humidity, temperature, oxygenLevel);
        this.pollenLevel = pollenLevel;
        this.airQuality = calculateAirQuality();
        this.airToxicity = calculateAirToxicity();
    }

    public Temperate(AirInput input) {
        super(input);
        pollenLevel = input.getPollenLevel();
        this.airQuality = calculateAirQuality();
        this.airToxicity = calculateAirToxicity();
    }
    public double calculateAirQuality() {
        double score = getOxygenLevel() * 2 + getHumidity() * 0.7
                - pollenLevel * 0.1;
        double normalized = Math.max(0, Math.min(100, score));
        return Math.round(normalized * 100.0) / 100.0;
    }

    public double calculateAirToxicity() {
        double quality = this.airQuality;
        double toxicityAQ = 100 * (1 - quality / 84);
        double normalized = Math.max(0, Math.min(100, toxicityAQ));
        return Math.round(normalized * 100.0) / 100.0;
    }
    public double getPollenLevel() { return pollenLevel; }
}
