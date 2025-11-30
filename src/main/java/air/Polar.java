package air;

import fileio.AirInput;
import lombok.Getter;
import lombok.Setter;
import entities.Air;

@Getter
@Setter

public class Polar extends Air {
    private double iceCrystalConcentration;

    private static final double OXYGEN_AUX = 2.0;
    private static final double ICE_CRYSTAL_AUX = 0.05;
    private static final double TEMPERATURE_AUX = 100.0;
    private static final double MAX_QUALITY = 100.0;
    private static final double MIN_QUALITY = 0.0;
    private static final double MAX_TOX = 100.0;
    private static final double MIN_TOX = 0.0;
    private static final double TOXICITY_AUX = 142.0;
    private static final double ROUND_FACTOR = 100.0;

    public Polar(final String name, final double mass, final double humidity,
                 final double temperature, final double oxygenLevel,
                 final double iceCrystalConcentration) {
        super(name, mass, "PolarAir", humidity, temperature, oxygenLevel);
        this.iceCrystalConcentration = iceCrystalConcentration;
        this.airQuality = calculateAirQuality();
        this.airToxicity = calculateAirToxicity();
    }

    public Polar(final AirInput input) {
        super(input);
        iceCrystalConcentration = input.getIceCrystalConcentration();
        this.airQuality = calculateAirQuality();
        this.airToxicity = calculateAirToxicity();
    }

    /** Calculates the air quality score for polar air. */
    public double calculateAirQuality() {
        double score = getOxygenLevel() * OXYGEN_AUX
                        + (TEMPERATURE_AUX - Math.abs(getTemperature()))
                        - iceCrystalConcentration * ICE_CRYSTAL_AUX;
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
     * Adds oxygen to the polar air and updates toxicity accordingly.
     */
    public void addOxygen(final double amount) {
        super.addOxygen(amount);
        this.airToxicity = calculateAirToxicity();
    }
}
