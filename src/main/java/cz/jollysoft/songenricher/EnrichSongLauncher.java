package cz.jollysoft.songenricher;


import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.Files;
//import java.nio.file.LinkOption;

//import cz.jollysoft.songenricher.processors.EnsembleLoader;
//import cz.jollysoft.songenricher.processors.EnsembleSaver;
//import cz.jollysoft.songenricher.processors.SongEnricher;
//import cz.jollysoft.songenricher.processors.SongParser;
//import cz.jollysoft.songenricher.processors.SongSerializer;

import static java.nio.file.LinkOption.NOFOLLOW_LINKS;



/**
 * Main application entry point to the whole process of song enrichment.
 * The process has five main steps (for each song, perform the following):
 * <ol>
 * <li>Load song data from the disk file - see {@link cz.jollysoft.songenricher.processors.EnsembleLoader EnsembleLoader}</li>
 * <li>Parse song data in memory and create a sort of DOM out of it - see {@link cz.jollysoft.songenricher.processors.SongParser SongParser}</li>
 * <li>Add useful information to song lyrics and to some song metadata tags - see {@link cz.jollysoft.songenricher.processors.SongEnricher SongEnricher}</li>
 * <li>Transform the song DOM to a simple in-memory object ready to be saved to the disk - see {@link cz.jollysoft.songenricher.processors.SongSerializer SongSerializer}</li>
 * <li>Save the song data back to the disk - see {@link cz.jollysoft.songenricher.processors.EnsembleSaver EnsembleSaver}</li>
 * <ol>
 * 
 * @author Pavel Foltyn
 * @since 2018
 */
public class EnrichSongLauncher {



    /**
     * Displays syntax of this Java app and stops the JVM that the app is running within.
     * 
     * @param errorMessage Additional error message to emit.
     */
    private static void displaySyntaxAndExit(String errorMessage) {

        // Display the syntax of this app.
        System.out.println();
        System.out.println("Syntax:");
        System.out.println("java cz.jollysoft.songenricher.EnrichSongLauncher <songs-input-path> <output-path-for-enriched-songs>");
        System.out.println();
        if (errorMessage != null) {
            System.out.println(errorMessage);
            System.out.println();
        }

        // Stop execution.
        System.exit(1);

    }



    /**
     * Main application entry point.
     * Starts the whole process of song enrichment.
     * 
     * @param args Command line parameters. The first param should be an input path, the second param should be an output path. The input and output paths cannot be the same.
     */
    public static void main(String[] args) {

        // Integrity check.
        if ( ! (args.length == 2) ) {
            displaySyntaxAndExit(null);
        }

        // Get arguments and test their integrity.
        String inputPathAsString = args[0];
        String outputPathAsString = args[1];
        if ( ! ( ! inputPathAsString.equals(outputPathAsString) ) ) {
            displaySyntaxAndExit("The input and output paths MUST be different!");
        }

        // Test for existence of the paths.
        Path inputPath = Paths.get(inputPathAsString);
        Path outputPath = Paths.get(outputPathAsString);
        //if ( ! (inputPath.toFile().exists()) ) {
        //if ( ! (Files.exists(inputPath)) ) {
        if ( ! (Files.exists(inputPath, NOFOLLOW_LINKS)) ) {
            displaySyntaxAndExit(String.format("The path '%s' does not exist.", inputPath.toString()));
        }
        if ( ! (Files.exists(outputPath, NOFOLLOW_LINKS)) ) {
            displaySyntaxAndExit(String.format("The path '%s' does not exist.", outputPath.toString()));
        }

        // The paths MUST be directories.
        if ( ! (Files.isDirectory(inputPath, NOFOLLOW_LINKS)) ) {
            displaySyntaxAndExit(String.format("The path '%s' does not represent a directory.", inputPath.toString()));
        }
        if ( ! (Files.isDirectory(outputPath, NOFOLLOW_LINKS)) ) {
            displaySyntaxAndExit(String.format("The path '%s' does not represent a directory.", outputPath.toString()));
        }

        // Give some debug info.
        System.out.println();
        System.out.println("Starting to enrich songs:");
        System.out.printf("Input directory with songs: %s%n", inputPath.toString());
        System.out.printf("Output directory to store enriched songs: %s%n", outputPath.toString());
        System.out.println();

        // Do the work we're here for.
        EnrichSongOrchestrator enrichSongOrchestrator = new EnrichSongOrchestrator(inputPath, outputPath);
        enrichSongOrchestrator.launchAndOrchestrate();

        // Done!
        System.out.println();
        System.out.println("Song enrichment: DONE.");
        System.out.println();

    }



}
