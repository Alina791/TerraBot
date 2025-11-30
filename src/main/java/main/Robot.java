package main;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class Robot {
    private int x;
    private int y;
    private int battery;

    private boolean simulationStarted = false;
    private boolean charging = false;
    private int chargingUntilTimestamp = 0;
    private int lastEnergyUpdateTimestamp = 0;
    private final Scanner scanner;
    private TerraMap map;

    private static final double ROUND_FACTOR = 100.0;

    public Robot(final int startX, final int startY, final int battery, final TerraMap map) {
        this.x = startX;
        this.y = startY;
        this.battery = battery;
        this.map = map;
        this.scanner = new Scanner(this, map);
    }

    /** Reduces the robot's battery by the specified amount. */
    public void consumeEnergy(final int amount) {
        battery = battery - amount;
    }

    /** Instructs the scanner to scan the specified cell object. */
    public String scanObject(final Cell cell, final String color,
                             final String smell, final String sound) {
        return scanner.scanObject(cell, color, smell, sound);
    }

    private boolean isInside(final int i, final int j) {
        return i >= 0 && i < map.getWidth() && j >= 0 && j < map.getHeight();
    }

    /** Initializes the simulation for the robot. */
    public void startSimulation(final int timestamp) {
        this.simulationStarted = true;
        this.charging = false;
        this.lastEnergyUpdateTimestamp = timestamp;
    }

    /** Ends the robot's simulation. */
    public void endSimulation() {
        this.simulationStarted = false;
    }

    /** Attempts to move the robot to the adjacent cell
     * with the lowest calculated score.
     */
    public String moveRobot(final int timestamp) {
        if (!simulationStarted) {
            return "ERROR: Simulation not started. Cannot perform action";
        }

        if (charging && timestamp < chargingUntilTimestamp) {
            return "ERROR: Robot still charging. Cannot perform action";
        }

        if (charging) {
            charging = false;
        }

        int[][] directions = {
                {1, 0},
                {0, 1},
                {-1, 0},
                {0, -1}
        };
        int minScore = Integer.MAX_VALUE;
        int nextX = x;
        int nextY = y;

        for (int[] d : directions) {
            int ny = y + d[0];
            int nx = x + d[1];

            if (!isInside(nx, ny)) {
                continue;
            }

            int score = calculateCellScore(nx, ny);
            if (score < minScore) {
                minScore = score;
                nextX = nx;
                nextY = ny;
            }
        }
        if (battery < minScore) {
            return "ERROR: Not enough battery left. Cannot perform action";
        }
        x = nextX;
        y = nextY;
        battery = battery - minScore;
        return "The robot has successfully moved to position (" + x + ", " + y + ").";

    }

    private int calculateCellScore(final int i, final int j) {
        Cell cell = map.getGrid()[i][j];
        double sum = 0.0;
        int count = 0;

        if (cell.getSoil() != null) {
            double soilScore = cell.getSoil().getPossibilityToGetStuckInSoil();
            if (!Double.isNaN(soilScore)) {
                soilScore = Math.round(soilScore * ROUND_FACTOR) / ROUND_FACTOR;
                sum = sum + soilScore;
                count++;
            }
        }
        if (cell.getAir() != null) {
            double airScore = cell.getAir().getAirToxicity();
            if (!Double.isNaN(airScore)) {
                airScore = Math.round(airScore * ROUND_FACTOR) / ROUND_FACTOR;
                sum = sum + airScore;
                count++;
            }
        }

        if (cell.getAnimal() != null) {
            double animalScore = cell.getAnimal().getAttackProbability();
            if (!Double.isNaN(animalScore)) {
                animalScore = Math.round(animalScore * ROUND_FACTOR) / ROUND_FACTOR;
                sum = sum + animalScore;
                count++;
            }
        }
        if (cell.getPlant() != null) {
            double plantScore = cell.getPlant().getPlantPossibility();
            if (!Double.isNaN(plantScore)) {
                plantScore = Math.round(plantScore * ROUND_FACTOR) / ROUND_FACTOR;
                sum = sum + plantScore;
                count++;
            }
        }

        double mean = sum / count;
        return (int) Math.round(mean);
    }

    /** Starts the robot's battery recharging process. */
    public String rechargeBattery(final int timestamp, final int duration) {
        if (!simulationStarted) {
            return "ERROR: Simulation not started. Cannot perform action";
        }
        if (isCharging(timestamp)) {
            return "ERROR: Robot still charging. Cannot perform action";
        }
        charging = true;
        chargingUntilTimestamp = timestamp + duration;
        battery = battery + duration;
        return "Robot battery is charging.";
    }

    /** Gets the robot's current energy status. */
    public String getEnergyStatus(final int timestamp) {
        if (isCharging(timestamp)) {
            return "ERROR: Robot still charging. Cannot perform action";
        }
        return "TerraBot has " + battery + " energy points left.";
    }

    /** Checks if the robot is currently in a charging state
     * and updates the state if charging is complete.
     */
    public boolean isCharging(final int timestamp) {
        if (charging && timestamp >= chargingUntilTimestamp) {
            charging = false;
        }
        return charging;
    }
}
