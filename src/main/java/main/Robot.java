package main;

import static java.lang.Long.MAX_VALUE;

public class Robot {
    private int x;
    private int y;
    private int battery;

    private boolean simulationStarted = false;
    private boolean charging = false;
    private int chargingUntilTimestamp = 0;

    private TerraMap map;

    public Robot(int startX, int startY, int battery, TerraMap map) {
        this.x = startX;
        this.y = startY;
        this.battery = battery;
        this.map = map;
    }

    private boolean isInside(int i, int j) {
        return i >= 0 && i < map.getWidth() && j >= 0 && j < map.getHeight();
    }


    public void startSimulation() {
        this.simulationStarted = true;
        this.charging = false;
    }

    public void endSimulation() {
        this.simulationStarted = false;
    }

    public String moveRobot(int timestamp) {
        if (!simulationStarted) {
            return "ERROR: Simulation not started. Cannot perform action";
        }

        if (charging && timestamp < chargingUntilTimestamp) {
            return "ERROR: Robot still charging. Cannot perform action";
        }

        if (charging && timestamp >= chargingUntilTimestamp) {
            charging = false;
        }
//        StringBuilder debugMsg = new StringBuilder();
//        debugMsg.append("Neighboring cell scores (blocking probability):\n");

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
            //debugMsg.append("Cell (").append(nx).append(", ").append(ny).append("): ").append(score).append("\n");
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
//        debugMsg.append("The robot has successfully moved to position (").append(x).append(", ").append(y).append(").");
//        return debugMsg.toString();
    }

    private int calculateCellScore(int i, int j) {
        Cell cell = map.getGrid()[i][j];

        double sum = 0.0;
        int count = 0;

        if (cell.getSoil() != null) {
            double soilScore = cell.getSoil().getPossibilityToGetStuckInSoil();
            if (!Double.isNaN(soilScore)) {
                soilScore = Math.round(soilScore * 100.0) / 100.0;
                sum += soilScore;
                count++;
            }
        }
        if (cell.getAir() != null) {
            double airScore = cell.getAir().getAirToxicity();
            if (!Double.isNaN(airScore)) {
                airScore = Math.round(airScore * 100.0) / 100.0;
                sum += airScore;
                count++;
            }
        }

        if (cell.getAnimal() != null) {
            double animalScore = cell.getAnimal().getAttackProbability();
            if (!Double.isNaN(animalScore)) {
                animalScore = Math.round(animalScore * 100.0) / 100.0;
                sum += animalScore;
                count++;
            }
        }
        if (cell.getPlant() != null) {
            double plantScore = cell.getPlant().getPlantPossibility();
            if (!Double.isNaN(plantScore)) {
                plantScore = Math.round(plantScore * 100.0) / 100.0;
                sum += plantScore;
                count++;
            }
        }


        double mean = sum / count;
        return (int) Math.round(mean);
    }




    public String rechargeBattery(int timestamp, int energyPoints) {
        if (!simulationStarted) return "ERROR: Simulation not started. Cannot perform action";
        if (charging && timestamp < chargingUntilTimestamp)
            return "ERROR: Robot still charging. Cannot perform action";
        charging = true;
        chargingUntilTimestamp = timestamp + energyPoints;
        battery += energyPoints;
        return "Robot battery is charging.";
    }
    public int getX() { return x; }
    public int getY() { return y; }
    public String getEnergyStatus() { return "TerraBot has " + battery + " energy points left."; }
    public boolean isCharging() { return charging; }
}
