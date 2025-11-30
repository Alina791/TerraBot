package air;

import fileio.AirInput;
import lombok.Getter;
import lombok.Setter;
import entities.Air;

@Getter
@Setter

public class Temperate extends Air {
    private double pollenLevel;

    private static final double OXYGEN_AUX = 2.0;
    private static final double POLLEN_AUX = 0.1;
    private static final double HUMIDITY_AUX = 0.7;
    private static final double MAX_QUALITY = 100.0;
    private static final double MIN_QUALITY = 0.0;
    private static final double MAX_TOX = 100.0;
    private static final double MIN_TOX = 0.0;
    private static final double TOXICITY_AUX = 84.0;
    private static final double ROUND_FACTOR = 100.0;

    public Temperate(final String name, final double mass, final double humidity,
                     final double temperature, final double oxygenLevel,
                     final double pollenLevel) {
        super(name, mass, "TemperateAir", humidity, temperature, oxygenLevel);
        this.pollenLevel = pollenLevel;
        this.airQuality = calculateAirQuality();
        this.airToxicity = calculateAirToxicity();
    }

    public Temperate(final AirInput input) {
        super(input);
        pollenLevel = input.getPollenLevel();
        this.airQuality = calculateAirQuality();
        this.airToxicity = calculateAirToxicity();
    }

    /** Calculates the air quality score for temperate air. */
    public double calculateAirQuality() {
        double score = getOxygenLevel() * OXYGEN_AUX + getHumidity() * HUMIDITY_AUX
                       - pollenLevel * POLLEN_AUX;
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
     * Adds oxygen to the temperate air and updates toxicity accordingly.
     */
    public void addOxygen(final double amount) {
        super.addOxygen(amount);
        this.airToxicity = calculateAirToxicity();
    }
}
