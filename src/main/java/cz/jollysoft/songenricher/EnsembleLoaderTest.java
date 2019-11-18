package cz.jollysoft.songenricher;

import java.nio.file.Path;
import java.nio.file.Paths;

import cz.jollysoft.songenricher.dataholders.Ensemble;
import cz.jollysoft.songenricher.processors.EnsembleLoader;

/**
 * Tests loading a file's contents into a memory structure.
 * 
 * @author Pavel Foltyn
 */
public class EnsembleLoaderTest {



    /**
     * Main application entry point.
     * 
     * @param args Command line parameters.
     */
    public static void main(String[] args) {

        // Give debug info.
        System.out.println("Starting...");

        // Prepare a path to the file.
        String pathToFile = "src/main/resources/test.txt";

        // Prepare a Path object.
        Path path = Paths.get(pathToFile);

        // Load the contents of the file into an Ensemble object.
        EnsembleLoader ensembleLoader = new EnsembleLoader();
        Ensemble ensemble = ensembleLoader.apply(path);

        // Display the file contents.
        for (String line : ensemble.getLines()) {
            System.out.println(line);
        }

        // Give debug info.
        System.out.println("Done!");

    }



}