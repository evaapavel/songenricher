package cz.jollysoft.songenricher;



import java.nio.file.Path;
import java.nio.file.Paths;



/**
 * Tests the EnrichSongOrchestrator.
 * 
 * @author Pavel Foltyn
 */
public class EnrichSongOrchestratorTest {



    /**
     * Main application entry point.
     * 
     * @param args Command line parameters.
     */
    public static void main(String[] args) {

        // Give debug info.
        System.out.println();
        System.out.println("Starting...");
        System.out.println();

        // Prepare test data.
        String inputPathAsString = "src/main/resources/TestInput";
        String outputPathAsString = "src/main/resources/TestOutput";
        Path inputPath = Paths.get(inputPathAsString);
        Path outputPath = Paths.get(outputPathAsString);

        // Prepare an instance of the EnrichSongOrchestrator class.
        EnrichSongOrchestrator enrichSongOrchestrator = new EnrichSongOrchestrator(inputPath, outputPath);

        // Start the orchestrator.
        enrichSongOrchestrator.launchAndOrchestrate();

        // Done.

        // Give debug info.
        System.out.println();
        System.out.println("Done!");
        System.out.println();

    }



}