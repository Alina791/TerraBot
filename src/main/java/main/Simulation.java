package main;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fileio.CommandInput;
import fileio.SimulationInput;

import java.util.List;

public class Simulation {

    private final SimulationInput simulation;
    private final TerraMap map;
    private final List<CommandInput> commands;
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public Simulation(SimulationInput simulation, List<CommandInput> commands) {
        this.simulation = simulation;
        this.map = new TerraMap(simulation);
        this.commands = commands;
    }

    private boolean simulationStarted = false;
    private int robotX = 0;
    private int robotY = 0;

    public ArrayNode processCommands() {
        ArrayNode results = MAPPER.createArrayNode();

        for (var cmd : commands) { // folosim lista primită în constructor
            ObjectNode node = MAPPER.createObjectNode();
            node.put("command", cmd.getCommand());

            switch (cmd.getCommand()) {
                case "startSimulation" -> {
                    simulationStarted = true;
                    node.put("message", "Simulation has started.");
                }
                case "endSimulation" -> {
                    simulationStarted = false;
                    node.put("message", "Simulation has ended.");
                }
                case "printEnvConditions" -> {
                    if (simulationStarted) {
                        node.set("output", getEnvConditions());
                    } else {
                        node.put("message", "ERROR: Simulation not started. Cannot perform action");
                    }

                }
                case "printMap" -> {
                    if (simulationStarted) {
                        node.set("output", getMapOverview());
                    } else {
                        node.put("message", "ERROR: Simulation not started. Cannot perform action");
                    }
                }
                case "moveRobot" -> {
                    if (simulationStarted) {
                        robotY++;
                        if (robotY >= map.getHeight()) robotY = 0;
                        node.put("message", "The robot has successfully moved to position (" + robotX + ", " + robotY + ").");
                    } else {
                        node.put("message", "ERROR: Simulation not started. Cannot perform action");
                    }
                }
            }

            node.put("timestamp", cmd.getTimestamp());

            results.add(node);
        }

        return results;
    }

    private ObjectNode getEnvConditions() {
        ObjectNode output = MAPPER.createObjectNode();

        // Soil
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                Soil soil = map.getGrid()[x][y].getSoil();
                if (soil != null) {
                    ObjectNode soilNode = MAPPER.createObjectNode();
                    soilNode.put("type", soil.getType());
                    soilNode.put("name", soil.getName());
                    soilNode.put("mass", soil.getMass());
                    soilNode.put("nitrogen", soil.getNitrogen());
                    soilNode.put("waterRetention", soil.getWaterRetention());
                    soilNode.put("soilpH", soil.getSoilpH());
                    soilNode.put("organicMatter", soil.getOrganicMatter());
                    soilNode.put("soilQuality", soil.getSoilQuality());
                    double waterLogging = soil.getWaterLogging();
                    if (!Double.isNaN(waterLogging)) {
                        soilNode.put("waterLogging", waterLogging);
                    }

                    double leafLitter = soil.getLeafLitter();
                    if (!Double.isNaN(leafLitter)) {
                        soilNode.put("leafLitter", leafLitter);
                    }

                    double salinity = soil.getSalinity();
                    if (!Double.isNaN(salinity)) {
                        soilNode.put("salinity", salinity);
                    }

                    double rootDensity = soil.getRootDensity();
                    if (!Double.isNaN(rootDensity)) {
                        soilNode.put("rootDensity", rootDensity);
                    }

                    double permafrostDepth = soil.getPermafrostDepth();
                    if (!Double.isNaN(permafrostDepth)) {
                        soilNode.put("permafrostDepth", permafrostDepth);
                    }
                    output.set("soil", soilNode);
                    break;
                }
            }
            if (output.has("soil")) break;
        }

        // Plants
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                Plant plant = map.getGrid()[x][y].getPlant();
                if (plant != null) {
                    ObjectNode plantNode = MAPPER.createObjectNode();
                    plantNode.put("type", plant.getType());
                    plantNode.put("name", plant.getName());
                    plantNode.put("mass", plant.getMass());
                    output.set("plants", plantNode);
                    break;
                }
            }
            if (output.has("plants")) break;
        }

        // Animals
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                Animal animal = map.getGrid()[x][y].getAnimal();
                if (animal != null) {
                    ObjectNode animalNode = MAPPER.createObjectNode();
                    animalNode.put("type", animal.getType());
                    animalNode.put("name", animal.getName());
                    animalNode.put("mass", animal.getMass());
                    output.set("animals", animalNode);
                    break;
                }
            }
            if (output.has("animals")) break;
        }

        // Water
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                Water water = map.getGrid()[x][y].getWater();
                if (water != null) {
                    ObjectNode waterNode = MAPPER.createObjectNode();
                    waterNode.put("type", water.getType());
                    waterNode.put("name", water.getName());
                    waterNode.put("mass", water.getMass());
                    output.set("water", waterNode);
                    break;
                }
            }
            if (output.has("water")) break;
        }

        // Air
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                Air air = map.getGrid()[x][y].getAir();
                if (air != null) {
                    ObjectNode airNode = MAPPER.createObjectNode();
                    airNode.put("type", air.getType());
                    airNode.put("name", air.getName());
                    airNode.put("mass", air.getMass());
                    airNode.put("humidity", air.getHumidity());
                    airNode.put("temperature", air.getTemperature());
                    airNode.put("oxygenLevel", air.getOxygenLevel());
                    airNode.put("airQuality", air.getAirQuality());
                    double co2Level = air.getCo2Level();
                    if (!Double.isNaN(co2Level)) {
                        airNode.put("co2Level", co2Level);
                    }

                    double iceCrystalConcentration = air.getIceCrystalConcentration();
                    if (!Double.isNaN(iceCrystalConcentration)) {
                        airNode.put("iceCrystalConcentration", iceCrystalConcentration);
                    }

                    double pollenLevel = air.getPollenLevel();
                    if (!Double.isNaN(pollenLevel)) {
                        airNode.put("pollenLevel", pollenLevel);
                    }

                    double dustParticles = air.getDustParticles();
                    if (!Double.isNaN(dustParticles)) {
                        airNode.put("dustParticles", dustParticles);
                    }

                    double altitude = air.getAltitude();
                    if (!Double.isNaN(altitude)) {
                        airNode.put("altitude", altitude);
                    }
                    output.set("air", airNode);
                    break;
                }
            }
            if (output.has("air")) break;
        }

        return output;
    }

    private ArrayNode getMapOverview() {
        ArrayNode mapNode = MAPPER.createArrayNode();

        for (int y = 0; y < map.getHeight(); y++) {
            for (int x = 0; x < map.getWidth(); x++) {
                Cell cell = map.getGrid()[x][y];
                ObjectNode cellNode = MAPPER.createObjectNode();
                cellNode.putArray("section").add(x).add(y);

                int totalObjects = 0;
                if (cell.getPlant() != null) totalObjects++;
                if (cell.getAnimal() != null) totalObjects++;
                if (cell.getWater() != null) totalObjects++;

                cellNode.put("totalNrOfObjects", totalObjects);
                cellNode.put("airQuality", cell.getAir().getAirQualityLabel());
                cellNode.put("soilQuality", cell.getSoil().getSoilQualityLabel());
                mapNode.add(cellNode);
            }
        }

        return mapNode;
    }


    // getEnvConditions() și getMapOverview() rămân neschimbate
}
