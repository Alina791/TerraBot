package simulation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.CommandInput;
import fileio.SimulationInput;

import java.util.List;

public class SimulationManager {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * Manages the execution of multiple environment simulations by processing
     * a list of commands. Each simulation is created on demand when a
     * startSimulation command appears, and remains active until an
     * endSimulation command is received.
     */
    public ArrayNode multipleSimulations(final List<SimulationInput> simulations,
                                         final List<CommandInput> commands) {
        ArrayNode results = MAPPER.createArrayNode();
        Simulation currentSimulation = null;
        int simIndex = 0;

        for (CommandInput cmd : commands) {
            String commandName = cmd.getCommand();
            if ("startSimulation".equals(commandName)) {

                if (currentSimulation != null && currentSimulation.isRunning()) {
                    ObjectNode node = MAPPER.createObjectNode();
                    node.put("command", "startSimulation");
                    node.put("message", "ERROR: Simulation already started."
                             + " Cannot perform action");
                    node.put("timestamp", cmd.getTimestamp());
                    results.add(node);
                    continue;
                }

                if (simIndex >= simulations.size()) {
                    ObjectNode node = MAPPER.createObjectNode();
                    node.put("command", "startSimulation");
                    node.put("message", "ERROR: No more simulations available."
                             + " Cannot perform action");
                    node.put("timestamp", cmd.getTimestamp());
                    results.add(node);
                    continue;
                }
                currentSimulation = new Simulation(simulations.get(simIndex));
                simIndex++;
                results.add(currentSimulation.processCommand(cmd));
                continue;
            }

            if ("endSimulation".equals(commandName)) {
                if (currentSimulation == null || !currentSimulation.isRunning()) {
                    ObjectNode node = MAPPER.createObjectNode();
                    node.put("command", "endSimulation");
                    node.put("message", "ERROR: Simulation not started. Cannot perform action");
                    node.put("timestamp", cmd.getTimestamp());
                    results.add(node);
                    continue;
                }
                results.add(currentSimulation.processCommand(cmd));
                continue;
            }

            if (currentSimulation == null || !currentSimulation.isRunning()) {
                ObjectNode node = MAPPER.createObjectNode();
                node.put("command", commandName);
                node.put("message", "ERROR: Simulation not started. Cannot perform action");
                node.put("timestamp", cmd.getTimestamp());
                results.add(node);
                continue;
            }
            results.add(currentSimulation.processCommand(cmd));
        }
        return results;
    }
}
