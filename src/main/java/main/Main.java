package main;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import fileio.InputLoader;
import fileio.SimulationInput;

import java.io.File;
import java.io.IOException;

public final class Main {

    private Main() { }

    private static final ObjectMapper MAPPER = new ObjectMapper();
    public static final ObjectWriter WRITER = MAPPER.writer().withDefaultPrettyPrinter();

    public static void main(final String[] args) {
        if (args.length < 1) {
            System.err.println("Please provide input JSON file path as argument.");
            return;
        }
        try {
            action(args[0], "out.json"); // poți da un nume default
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Aceasta trebuie să existe pentru TestRunner
    public static void action(final String inputPath,
                              final String outputPath) throws IOException {

        InputLoader inputLoader = new InputLoader(inputPath);
        ArrayNode output = MAPPER.createArrayNode();

        for (SimulationInput sim : inputLoader.getSimulations()) {
            Simulation processor = new Simulation(sim, inputLoader.getCommands());
            ArrayNode simResult = processor.processCommands();
            output.addAll(simResult);
        }

        File outputFile = new File(outputPath);
        outputFile.getParentFile().mkdirs();
        WRITER.writeValue(outputFile, output);
    }
}
