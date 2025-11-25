package main;

import fileio.AirInput;

public class Desert extends Air {
    private double dustParticles;

    public Desert(String name, double mass, double humidity,
                    double temperature, double oxygenLevel,
                    double dustParticles) {
        super(name, mass, "DesertAir", humidity, temperature, oxygenLevel);
        this.dustParticles = dustParticles;
        this.airQuality = calculateAirQuality();
        this.airToxicity = calculateAirToxicity();
    }

    public Desert(AirInput input) {
        super(input);
        dustParticles = input.getDustParticles();
        this.airQuality = calculateAirQuality();
        this.airToxicity = calculateAirToxicity();
    }

    public double calculateAirQuality() {
        double score = getOxygenLevel() * 2 + dustParticles * 0.2
                - getTemperature() * 0.3;
        double normalized = Math.max(0, Math.min(100, score));
        return Math.round(normalized * 100.0) / 100.0;
    }

    public double calculateAirToxicity() {
        double quality = this.airQuality;
        double toxicityAQ = 100 * (1 - quality / 65);
        double normalized = Math.max(0, Math.min(100, toxicityAQ));
        return Math.round(normalized * 100.0) / 100.0;
    }
    public double getDustParticles() { return dustParticles; }
}
