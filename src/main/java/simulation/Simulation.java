package simulation;

import air.Desert;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import entities.Air;
import entities.Animal;
import entities.Plant;
import entities.Soil;
import entities.Water;
import fileio.CommandInput;
import fileio.SimulationInput;
import map.Cell;
import map.TerraMap;
import map.Weather;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Simulation {
    private final SimulationInput simulation;
    private final TerraMap map;
    private final Robot robot;
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private final Weather weather = new Weather();
    private boolean simulationStarted = false;
    private final Map<String, List<String>> knowledgeBase = new LinkedHashMap<>();

    private static final double ROUND_FACTOR = 100.0;

    public Simulation(final SimulationInput simulation) {
        this.simulation = simulation;
        this.map = new TerraMap(simulation);
        int energy = simulation.getEnergyPoints();
        this.robot = new Robot(0, 0, energy, map);
    }

    /**
     * Processes an incoming command during the simulation. It updates the weather state,
     * applies effects from previous scans, and executes the logic specific to the command.
     */
    public ObjectNode processCommand(final CommandInput cmd) {
        ObjectNode node = MAPPER.createObjectNode();
        node.put("command", cmd.getCommand());
        weather.updateAfterTimestamp(cmd.getTimestamp());
        map.applyScannedEffects();

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
                if (robot.isCharging(cmd.getTimestamp())) {
                    node.put("message", "ERROR: Robot still charging. "
                            + "Cannot perform action");
                } else {
                    if (simulationStarted) {
                        node.set("output", getEnvConditions());
                    } else {
                        node.put("message", "ERROR: Simulation not started. "
                                + "Cannot perform action");
                    }
                }
            }
            case "printMap" -> {
                if (simulationStarted) {
                    node.set("output", getMapOverview());
                } else {
                    node.put("message", "ERROR: Simulation not started. "
                            + "Cannot perform action");
                }
            }
            case "moveRobot" -> {
                String result = robot.moveRobot(cmd.getTimestamp());
                node.put("message", result);
            }
            case "rechargeBattery" -> {
                String result = robot.rechargeBattery(cmd.getTimestamp(),
                        cmd.getTimeToCharge());
                node.put("message", result);
            }
            case "getEnergyStatus" -> {
                if (simulationStarted) {
                    String result = robot.getEnergyStatus(cmd.getTimestamp());
                    node.put("message", result);
                } else {
                    node.put("message", "ERROR: Simulation not started. "
                            + "Cannot perform action");
                }
            }
            case "changeWeatherConditions" -> {
                if (robot.isCharging(cmd.getTimestamp())) {
                    node.put("message", "ERROR: Robot still charging. "
                            + "Cannot perform action");
                } else {
                    if (simulationStarted) {
                        node.put("message", weather.applyWeatherChange(cmd,
                                cmd.getTimestamp(), map));
                    } else {
                        node.put("message", "ERROR: Simulation not started. "
                                + "Cannot perform action");
                    }
                }
            }
            case "scanObject" -> {
                if (robot.isCharging(cmd.getTimestamp())) {
                    node.put("message", "ERROR: Robot still charging. "
                            + "Cannot perform action");
                } else if (!simulationStarted) {
                    node.put("message", "ERROR: Simulation not started. "
                            + "Cannot perform action");
                } else {
                    String color = cmd.getColor();
                    String smell = cmd.getSmell();
                    String sound = cmd.getSound();

                    String result = robot.getScanner().scanCurrentCell(color,
                            smell, sound, cmd.getTimestamp());
                    node.put("message", result);
                }
            }
            case "learnFact" -> {
                if (robot.isCharging(cmd.getTimestamp())) {
                    node.put("message", "ERROR: Robot still charging. "
                            + "Cannot perform action");
                } else if (!simulationStarted) {
                    node.put("message", "ERROR: Simulation not started. "
                            + "Cannot perform action");
                } else {
                    String topic = cmd.getComponents();
                    String fact = cmd.getSubject();

                    knowledgeBase.putIfAbsent(topic, new ArrayList<>());
                    knowledgeBase.get(topic).add(fact);
                    robot.consumeEnergy(2);
                    node.put("message", "The fact has been successfully saved in the database.");
                }
            }
            case "printKnowledgeBase" -> {
                if (robot.isCharging(cmd.getTimestamp())) {
                    node.put("message", "ERROR: Robot still charging. Cannot perform action");
                } else if (!simulationStarted) {
                    node.put("message", "ERROR: Simulation not started. Cannot perform action");
                } else {
                    ArrayNode output = MAPPER.createArrayNode();
                    for (var entry : knowledgeBase.entrySet()) {
                        ObjectNode topicNode = MAPPER.createObjectNode();
                        topicNode.put("topic", entry.getKey());

                        ArrayNode factsArray = MAPPER.createArrayNode();
                        for (String f : entry.getValue()) {
                            factsArray.add(f);
                        }
                        topicNode.set("facts", factsArray);
                        output.add(topicNode);
                    }
                    node.set("output", output);
                }
            }
            default -> node.put("message", "null");
        }
        node.put("timestamp", cmd.getTimestamp());
        return node;
    }

    /**
     * Generates an object containing the environmental conditions
     * of the robot's current cell.
     */
    private ObjectNode getEnvConditions() {
        ObjectNode output = MAPPER.createObjectNode();
        Cell cell = map.getGrid()[robot.getX()][robot.getY()];
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

        Plant plant = cell.getPlant();
        if (plant != null) {
            ObjectNode plantNode = MAPPER.createObjectNode();
            plantNode.put("type", plant.getType());
            plantNode.put("name", plant.getName());
            plantNode.put("mass", plant.getMass());
            output.set("plants", plantNode);
        }

        Animal animal = cell.getAnimal();
        if (animal != null) {
            ObjectNode animalNode = MAPPER.createObjectNode();
            animalNode.put("type", animal.getType());
            animalNode.put("name", animal.getName());
            animalNode.put("mass", animal.getMass());
            output.set("animals", animalNode);
        }

        Water water = cell.getWater();
        if (water != null) {
            ObjectNode waterNode = MAPPER.createObjectNode();
            waterNode.put("type", water.getType());
            waterNode.put("name", water.getName());
            waterNode.put("mass", water.getMass());
            output.set("water", waterNode);
        }

        Air air = cell.getAir();
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
                airNode.put("co2Level",
                         Math.round(co2Level * ROUND_FACTOR) / ROUND_FACTOR);
            }

            double iceCrystalConcentration = air.getIceCrystalConcentration();
            if (!Double.isNaN(iceCrystalConcentration)) {
                airNode.put("iceCrystalConcentration", iceCrystalConcentration);
            }

            double pollenLevel = air.getPollenLevel();
            if (!Double.isNaN(pollenLevel)) {
                airNode.put("pollenLevel", pollenLevel);
            }

            if (air.hasFeature("desertStorm")) {
                boolean storm = ((Desert) air).getDesertStorm();
                airNode.put("desertStorm", storm);
            } else {
                double dustParticles = air.getDustParticles();
                if (!Double.isNaN(dustParticles)) {
                    airNode.put("dustParticles", dustParticles);
                }
            }
            double altitude = air.getAltitude();
            if (!Double.isNaN(altitude)) {
                airNode.put("altitude", altitude);
            }
            output.set("air", airNode);
        }
        return output;
    }

    /**
     * Generates a simplified overview of the entire map grid.
     */
    private ArrayNode getMapOverview() {
        var mapNode = MAPPER.createArrayNode();
        for (int y = 0; y < map.getHeight(); y++) {
            for (int x = 0; x < map.getWidth(); x++) {
                Cell cell = map.getGrid()[x][y];
                ObjectNode cellNode = MAPPER.createObjectNode();
                cellNode.putArray("section").add(x).add(y);

                int totalObjects = 0;
                if (cell.getPlant() != null) {
                    totalObjects++;
                }
                if (cell.getAnimal() != null) {
                    totalObjects++;
                }
                if (cell.getWater() != null) {
                    totalObjects++;
                }
                cellNode.put("totalNrOfObjects", totalObjects);
                cellNode.put("airQuality", cell.getAir().getAirQualityLabel());
                cellNode.put("soilQuality", cell.getSoil().getSoilQualityLabel());
                mapNode.add(cellNode);
            }
        }
        return mapNode;
    }

    /**
    * Checks if the simulation is currently running.
    */
    public boolean isRunning() {
        return this.simulationStarted;
    }
}
