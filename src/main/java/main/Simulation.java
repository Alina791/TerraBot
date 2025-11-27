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
    private final Robot robot;
    private final List<CommandInput> commands;
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public Simulation(SimulationInput simulation, List<CommandInput> commands) {
        this.simulation = simulation;
        this.map = new TerraMap(simulation);
        this.commands = commands;
        int energy = simulation.getEnergyPoints();
        this.robot = new Robot(0, 0, energy, map);
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
                    robot.startSimulation();
                    simulationStarted = true;
                    node.put("message", "Simulation has started.");
                }
                case "endSimulation" -> {
                    robot.endSimulation();
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
                    String result = robot.moveRobot(cmd.getTimestamp());
                    node.put("message", result);
                }
                case "rechargeBattery" -> {
                    String result = robot.rechargeBattery(cmd.getTimestamp(), simulation.getEnergyPoints());
                    node.put("message", result);
                }
                case "getEnergyStatus" -> {
                    String result = robot.getEnergyStatus();
                    node.put("message", result);
                }

            }

            node.put("timestamp", cmd.getTimestamp());

            results.add(node);
        }

        return results;
    }

    private ObjectNode getEnvConditions() {
        ObjectNode output = MAPPER.createObjectNode();
        Cell cell = map.getGrid()[robot.getX()][robot.getY()];
        // Soil
               // Soil soil = map.getGrid()[x][y].getSoil();
        Soil soil = cell.getSoil();
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
                }

        // Plants

                Plant plant = cell.getPlant();
                if (plant != null) {
                    ObjectNode plantNode = MAPPER.createObjectNode();
                    plantNode.put("type", plant.getType());
                    plantNode.put("name", plant.getName());
                    plantNode.put("mass", plant.getMass());
                    output.set("plants", plantNode);
                }

        // Animals
                Animal animal = cell.getAnimal();
                if (animal != null) {
                    ObjectNode animalNode = MAPPER.createObjectNode();
                    animalNode.put("type", animal.getType());
                    animalNode.put("name", animal.getName());
                    animalNode.put("mass", animal.getMass());
                    output.set("animals", animalNode);
                }

        // Water
        Water water = cell.getWater();
                if (water != null) {
                    ObjectNode waterNode = MAPPER.createObjectNode();
                    waterNode.put("type", water.getType());
                    waterNode.put("name", water.getName());
                    waterNode.put("mass", water.getMass());
                    output.set("water", waterNode);
                }

        // Air
        Air air = cell.getAir();
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
