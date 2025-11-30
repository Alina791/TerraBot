package entities;

import fileio.AirInput;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public abstract class Air extends Entity {
    private String type;
    private double humidity;
    private double temperature;
    private double oxygenLevel;

    public double airQuality;
    protected double airToxicity;

    private static final double GOOD_QUALITY = 70.0;
    private static final double MODERATE_QUALITY = 40.0;
    private static final double ROUND_FACTOR = 100.0;

    public Air(final AirInput input) {
        this.type = input.getType();
        setMass(input.getMass());
        setName(input.getName());
        humidity = (input.getHumidity());
        temperature = (input.getTemperature());
        oxygenLevel = (input.getOxygenLevel());
        this.airQuality = calculateAirQuality();
        this.airToxicity = calculateAirToxicity();
    }
    public Air(final String name, final double mass, final String type, final double humidity,
               final double temperature, final double oxygenLevel) {
        super(name, mass);
        this.type = type;
        this.humidity = humidity;
        this.temperature = temperature;
        this.oxygenLevel = oxygenLevel;
    }

    /** Calculates the quality of the air. */
    public abstract double calculateAirQuality();

    /** Calculates the toxicity of the air. */
    public abstract double calculateAirToxicity();

    /** Gets the pollen level only if supported by subclass. */
    public double getPollenLevel() {
        return Double.NaN;
    }

    /**
     * Gets the ice crystal concentration only if supported by subclass.
     */
    public double getIceCrystalConcentration() {
        return Double.NaN;
    }

    /** Gets the dust particles only if supported by subclass. */
    public double getDustParticles() {
        return Double.NaN;
    }

    /** Gets the altitude only if supported by subclass. */
    public double getAltitude() {
        return Double.NaN;
    }

    /** Gets the co2 level only if supported by subclass. */
    public double getCo2Level() {
        return Double.NaN;
    }

    /** Returns a label describing the air quality. */
    public String getAirQualityLabel() {
        if (airQuality >= GOOD_QUALITY) {
            return "good";
        } else if (airQuality >= MODERATE_QUALITY) {
            return "moderate";
        } else {
            return "poor";
        }
    }

    /** Method for subclasses to override if weather conditions can change. */
    public void changeWeatherCondition() {
    }

    /** Checks whether this air type has a specific feature. */
    public boolean hasFeature(final String feature) {
        return false;
    }

    /** Calculates the oxygen level after environment interactions  with plants. */
    public void addOxygen(final double amount) {
        oxygenLevel = Math.round((oxygenLevel + amount) * ROUND_FACTOR) / ROUND_FACTOR;
        airQuality = calculateAirQuality();
    }

    /** Calculates the humidity after environment interactions  with water. */
    public void addHumidity(final double value) {
        humidity = Math.round((humidity + value) * ROUND_FACTOR) / ROUND_FACTOR;
        airQuality = calculateAirQuality();
    }
}
