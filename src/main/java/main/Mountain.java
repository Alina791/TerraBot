package main;

import fileio.AirInput;

public class Mountain extends Air {
    private double altitude;

    public Mountain(String name, double mass, double humidity,
                    double temperature, double oxygenLevel,
                    double altitude) {
        super(name, mass, "MountainAir", humidity, temperature, oxygenLevel);
        this.altitude = altitude;
        this.airQuality = calculateAirQuality();
        this.airToxicity = calculateAirToxicity();
    }

    public Mountain(AirInput input) {
        super(input);
        altitude = input.getAltitude();
        this.airQuality = calculateAirQuality();
        this.airToxicity = calculateAirToxicity();
    }

    public double calculateAirQuality() {
        double oxygenFactor = getOxygenLevel() - (altitude / 1000.0) * 0.5;
        double score = oxygenFactor * 2.0 + getHumidity() * 0.6;
        double normalized = Math.max(0.0, Math.min(100.0, score));
        return Math.round(normalized * 100.0) / 100.0;
    }

    public double calculateAirToxicity() {
        double quality = this.airQuality;
        double toxicityAQ = 100.0 * (1.0 - quality / 78.0);
        double normalized = Math.max(0.0, Math.min(100.0, toxicityAQ));
        return Math.round(normalized * 100.0) / 100.0;

    }
    public double getAltitude() { return altitude; }
}
