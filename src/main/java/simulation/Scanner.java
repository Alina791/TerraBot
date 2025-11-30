package simulation;

import entities.Animal;
import entities.Plant;
import map.Cell;
import map.TerraMap;
import entities.Water;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter

public class Scanner {

    private final Robot robot;
    private final TerraMap map;

    private final Map<String, Object> scannedInventory = new HashMap<>();

    private static final double SCAN_ENERGY = 7;
    private static final int SCAN_ENERGY_INT = 7;

    public Scanner(final Robot robot, final TerraMap map) {
        this.robot = robot;
        this.map = map;
    }

    /**
    * Scans a specific object in a given cell based on its properties
    * (color, smell, sound) and consumes energy.
    */
    public String scanObject(final Cell cell, final String color, final String smell,
                             final String sound, final int timestamp) {

        if (robot.isCharging(timestamp)) {
            return "ERROR: Robot still charging. Cannot perform action";
        }

        if (robot.getBattery() < SCAN_ENERGY) {
            return "ERROR: Not enough battery left. Cannot perform action";
        }

        boolean hasObject = cell.getPlant() != null || cell.getAnimal() != null
                            || cell.getWater() != null;
        if (!hasObject) {
            return "ERROR: Object not found. Cannot perform action";
        }

        robot.consumeEnergy(SCAN_ENERGY_INT);

        String type;
        if ("none".equals(color) && "none".equals(smell) && "none".equals(sound)) {
            type = "water";
        } else if ("pink".equals(color) && "sweet".equals(smell) && "none".equals(sound)) {
            type = "plant";
        } else if ("brown".equals(color) && "earthy".equals(smell) && !"none".equals(sound)) {
            type = "animal";
        } else {
            type = "unknown";
        }

        Object scannedObject = getObjectFromCell(cell, type);
        if (scannedObject != null) {
            scannedInventory.put(type, scannedObject);
        }

        switch (type) {
            case "plant" -> {
                Plant plant = cell.getPlant();
                if (plant != null && !plant.isDead()) {
                    plant.setScanned(true);
                    return "The scanned object is a " + type + ".";
                }
            }
            case "water" -> {
                Water water = cell.getWater();
                if (water != null) {
                    water.setScanned(true);
                    cell.resetWaterCounter();
                    return "The scanned object is " + type + ".";
                }
            }
            case "animal" -> {
                Animal animal = cell.getAnimal();
                if (animal != null) {
                    animal.setScanned(true);
                    return "The scanned object is an " + type + ".";
                }
            }
            default -> {
                return "ERROR: Object not found. Cannot perform action";
            }
        }
        return "ERROR: Object not found. Cannot perform action";
    }

    private Object getObjectFromCell(final Cell cell, final String type) {
        return switch (type) {
            case "plant" -> cell.getPlant();
            case "animal" -> cell.getAnimal();
            case "water" -> cell.getWater();
            default -> null;
        };
    }

    /** Scans the robot's current cell based on its properties. */
    public String scanCurrentCell(final String color, final String smell,
                                  final String sound, final int timestamp) {
        int x = robot.getX();
        int y = robot.getY();
        Cell cell = map.getGrid()[x][y];
        return scanObject(cell, color, smell, sound, timestamp);
    }
}
