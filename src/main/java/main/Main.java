package main;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import fileio.InputLoader;
import simulation.SimulationManager;

import java.io.File;
import java.io.IOException;

public final class Main {

    private Main() {
    }

    private static final ObjectMapper MAPPER = new ObjectMapper();
    public static final ObjectWriter WRITER = MAPPER.writer().withDefaultPrettyPrinter();

    /**
     * Entry point for running simulations and writing the results to a file.
     */
    public static void action(final String inputPath,
                              final String outputPath) throws IOException {

        InputLoader inputLoader = new InputLoader(inputPath);

        SimulationManager manager = new SimulationManager();
        ArrayNode output = manager.multipleSimulations(
                inputLoader.getSimulations(),
                inputLoader.getCommands()
        );

        File outputFile = new File(outputPath);
        outputFile.getParentFile().mkdirs();
        WRITER.writeValue(outputFile, output);
    }
}
