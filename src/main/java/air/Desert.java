package air;

import fileio.AirInput;
import entities.Air;

public class Desert extends Air {
    private double dustParticles;
    private boolean desertStorm = false;

    private static final double DUST_AUX = 0.2;
    private static final double TEMP_AUX = 0.3;
    private static final double MAX_QUALITY = 100.0;
    private static final double MIN_QUALITY = 0.0;
    private static final double TOXICITY_AUX = 65.0;
    private static final double ROUND_FACTOR = 100.0;

    public Desert(final String name, final double mass, final double humidity,
                  final double temperature, final double oxygenLevel,
                  final double dustParticles) {
        super(name, mass, "DesertAir", humidity, temperature, oxygenLevel);
        this.dustParticles = dustParticles;
        this.airQuality = calculateAirQuality();
        this.airToxicity = calculateAirToxicity();
    }

    public Desert(final AirInput input) {
        super(input);
        dustParticles = input.getDustParticles();
        this.airQuality = calculateAirQuality();
        this.airToxicity = calculateAirToxicity();
    }

    /** Changes the desert weather condition. */
    public void changeWeatherCondition() {
        this.desertStorm = !this.desertStorm;
    }

    /** Returns whether a desert storm is occurring. */
    public boolean getDesertStorm() {
        return desertStorm;
    }

    /** Checks if the desert has a given feature. */
    public boolean hasFeature(final String feature) {
        return (feature.equals("desertStorm"));
    }

    /** Calculates the air quality score for desert. */
    public double calculateAirQuality() {
        double score = getOxygenLevel() * 2.0 - dustParticles * DUST_AUX
                - getTemperature() * TEMP_AUX;
        double normalized = Math.max(MIN_QUALITY, Math.min(MAX_QUALITY, score));
        return Math.round(normalized * ROUND_FACTOR) / ROUND_FACTOR;
    }

    /** Calculates the air toxicity based on air quality. */
    public double calculateAirToxicity() {
        double quality = this.airQuality;
        double toxicityAQ = MAX_QUALITY * (1.0 - quality / TOXICITY_AUX);
        double normalized = Math.max(MIN_QUALITY, Math.min(MAX_QUALITY, toxicityAQ));
        return Math.round(normalized * ROUND_FACTOR) / ROUND_FACTOR;
    }

    /**
    * Adds oxygen to the desert air and updates toxicity accordingly.
    */
    public void addOxygen(final double amount) {
        super.addOxygen(amount);
        this.airToxicity = calculateAirToxicity();
    }
}
