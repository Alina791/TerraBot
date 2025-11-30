package map;

import entities.Air;
import fileio.CommandInput;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter

public class Weather {

    private boolean active = false;
    private String weatherType;
    private int appliedAtTimestamp = -1;

    private final Map<Cell, Double> previousQualities = new HashMap<>();

    private static final double DESERT_AUX = 30;
    private static final double POLAR_AUX = 20;

    /**
     * Resets the weather state, deactivating any current weather effects.
     */
    public void deactivate() {
        active = false;
        weatherType = null;
        appliedAtTimestamp = -1;
        previousQualities.clear();
    }


    /** Applies a specified weather change across the map, affecting air quality
    * in specific air types and storing the previous air quality values.
    */
    public String applyWeatherChange(final CommandInput cmd, final int timestamp,
                                     final TerraMap map) {
        String type = cmd.getType();
        boolean affected = false;

        active = true;
        weatherType = type;
        appliedAtTimestamp = timestamp;
        previousQualities.clear();

        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                Cell cell = map.getGrid()[x][y];
                Air air = cell.getAir();
                if (air == null) {
                    continue;
                }

                switch (type) {
                    case "peopleHiking":
                        if (air.getType().equals("MountainAir")) {
                            double prev = air.getAirQuality();
                            previousQualities.put(cell, prev);

                            double newQuality = prev - (cmd.getNumberOfHikers() / 2.0);
                            if (newQuality < 0) {
                                newQuality = 0;
                            }
                            air.airQuality = newQuality;
                            affected = true;
                        }
                        break;

                    case "desertStorm":
                        if (air.getType().equals("DesertAir")) {
                            double prev = air.getAirQuality();
                            previousQualities.put(cell, prev);

                            double newQuality = prev - DESERT_AUX;
                            if (newQuality < 0) {
                                newQuality = 0;
                            }
                            air.airQuality = newQuality;
                            air.changeWeatherCondition();
                            affected = true;
                        }
                        break;

                    case "polarStorm":
                        if (air.getType().equals("PolarAir")) {
                            double prev = air.getAirQuality();
                            previousQualities.put(cell, prev);

                            double newQuality = prev - POLAR_AUX;
                            if (newQuality < 0) {
                                newQuality = 0;
                            }
                            air.airQuality = newQuality;
                            affected = true;
                        }
                        break;

                    default:

                }
            }
        }

        if (!affected) {
            deactivate();
            return "ERROR: The weather change does not affect the environment. "
                    + "Cannot perform action";
        }
        return "The weather has changed.";
    }

    /** Updates the air quality of affected cells two timestamps after the weather
     * was applied, then deactivates the weather effect.
     */
    public void updateAfterTimestamp(final int currentTimestamp) {
        if (!active) {
            return;
        }
        if (currentTimestamp - appliedAtTimestamp != 2) {
            return;
        }

        for (var entry : previousQualities.entrySet()) {
            Cell cell = entry.getKey();
            Air air = cell.getAir();
            if (air != null) {
                air.airQuality = air.calculateAirQuality();
            }
        }
        deactivate();
    }
}
