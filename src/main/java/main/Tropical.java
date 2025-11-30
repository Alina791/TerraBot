package main;

import fileio.AirInput;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class Tropical extends Air {
    private double co2Level;

    private static final double OXYGEN_AUX = 2.0;
    private static final double CO2_AUX = 0.01;
    private static final double HUMIDITY_AUX = 0.5;
    private static final double MAX_QUALITY = 100.0;
    private static final double MIN_QUALITY = 0.0;
    private static final double MAX_TOX = 100.0;
    private static final double MIN_TOX = 0.0;
    private static final double TOXICITY_AUX = 82.0;
    private static final double ROUND_FACTOR = 100.0;

    public Tropical(final String name, final double mass, final double humidity,
                    final double temperature, final double oxygenLevel,
                    final double co2Level) {
        super(name, mass, "TropicalAir", humidity, temperature, oxygenLevel);
        this.co2Level = co2Level;
        this.airQuality = calculateAirQuality();
        this.airToxicity = calculateAirToxicity();
    }

    public Tropical(final AirInput input) {
        super(input);
        co2Level = input.getCo2Level();
        this.airQuality = calculateAirQuality();
        this.airToxicity = calculateAirToxicity();
    }

    /** Calculates the air quality score for tropical air. */
    public double calculateAirQuality() {
        double score = getOxygenLevel() * OXYGEN_AUX + getHumidity() * HUMIDITY_AUX
                      - co2Level * CO2_AUX;
        double normalized = Math.max(MIN_QUALITY, Math.min(MAX_QUALITY, score));
        return Math.round(normalized * ROUND_FACTOR) / ROUND_FACTOR;
    }

    /** Calculates the air toxicity based on air quality. */
    public double calculateAirToxicity() {
        double quality = this.airQuality;
        double toxicityAQ = ROUND_FACTOR * (1.0 - quality / TOXICITY_AUX);
        double normalized = Math.max(MIN_TOX, Math.min(MAX_TOX, toxicityAQ));
        return Math.round(normalized * ROUND_FACTOR) / ROUND_FACTOR;
    }

    /**
     * Adds oxygen to the tropical air and updates toxicity accordingly.
     */
    public void addOxygen(final double amount) {
        super.addOxygen(amount);
        this.airToxicity = calculateAirToxicity();
    }
}
