package air;

import fileio.AirInput;
import lombok.Getter;
import lombok.Setter;
import entities.Air;

@Getter
@Setter

public class Mountain extends Air {
    private static final double ALTITUDE_AUX = 0.5;
    private static final double ALTITUDE_DIVISOR = 1000.0;
    private static final double HUMIDITY_AUX = 0.6;
    private static final double OXYGEN_AUX = 2.0;
    private static final double MAX_QUALITY = 100.0;
    private static final double MIN_QUALITY = 0.0;
    private static final double MAX_TOX = 100.0;
    private static final double MIN_TOX = 0.0;
    private static final double TOXICITY_AUX = 78.0;
    private static final double ROUND_FACTOR = 100.0;
    private double altitude;

    public Mountain(final String name, final double mass, final double humidity,
                    final double temperature, final double oxygenLevel,
                    final double altitude) {
        super(name, mass, "MountainAir", humidity, temperature, oxygenLevel);
        this.altitude = altitude;
        this.airQuality = calculateAirQuality();
        this.airToxicity = calculateAirToxicity();
    }

    public Mountain(final AirInput input) {
        super(input);
        altitude = input.getAltitude();
        this.airQuality = calculateAirQuality();
        this.airToxicity = calculateAirToxicity();
    }

    /**
     * Calculates the air quality score for mountain.
     */
    public double calculateAirQuality() {
        double oxygenFactor = getOxygenLevel() - (altitude / ALTITUDE_DIVISOR) * ALTITUDE_AUX;
        double score = oxygenFactor * OXYGEN_AUX + getHumidity() * HUMIDITY_AUX;
        double normalized = Math.max(MIN_QUALITY, Math.min(MAX_QUALITY, score));
        return Math.round(normalized * ROUND_FACTOR) / ROUND_FACTOR;
    }

    /**
     * Calculates the air toxicity based on air quality.
     */
    public double calculateAirToxicity() {
        double quality = this.airQuality;
        double toxicityAQ = ROUND_FACTOR * (1.0 - quality / TOXICITY_AUX);
        double normalized = Math.max(MIN_TOX, Math.min(MAX_TOX, toxicityAQ));
        return Math.round(normalized * ROUND_FACTOR) / ROUND_FACTOR;

    }

    /**
     * Adds oxygen to the mountain air and updates toxicity accordingly.
     */
    public void addOxygen(final double amount) {
        super.addOxygen(amount);
        this.airToxicity = calculateAirToxicity();
    }
}
