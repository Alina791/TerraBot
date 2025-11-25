package main;

import fileio.AirInput;
import fileio.PlantInput;

public abstract class Air extends Entity {
    private String type;
    private double humidity;
    private double temperature;
    private double oxygenLevel;

    protected double airQuality;
    protected double airToxicity;
    public Air(AirInput input) {
        this.type = input.getType();
        setMass(input.getMass());
        setName(input.getName());
        humidity = (input.getHumidity());
        temperature = (input.getTemperature());
        oxygenLevel = (input.getOxygenLevel());
        this.airQuality = calculateAirQuality();
        this.airToxicity = calculateAirToxicity();
    }
    public Air(String name, double mass, String type, double humidity,
               double temperature, double oxygenLevel) {
        super(name, mass);
        this.type = type;
        this.humidity = humidity;
        this.temperature = temperature;
        this.oxygenLevel = oxygenLevel;
    }
    public String getType() {
        return type;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getOxygenLevel() {
        return oxygenLevel;
    }
    public abstract double calculateAirQuality();
    public double getAirQuality() {
        return airQuality;
    }

    public abstract double calculateAirToxicity();
    public double getAirToxicity() {
        return airToxicity;
    }

    public double getPollenLevel() { return Double.NaN; }
    public double getIceCrystalConcentration() { return Double.NaN; }
    public double getDustParticles() { return Double.NaN; }
    public double getAltitude() { return Double.NaN; }
    public double getCo2Level() { return Double.NaN; }

    public String getAirQualityLabel() {
        if (airQuality >= 70.0) {
            return "good";
        } else if (airQuality >= 40.0) {
            return "moderate";
        } else {
            return "poor";
        }
    }
}
