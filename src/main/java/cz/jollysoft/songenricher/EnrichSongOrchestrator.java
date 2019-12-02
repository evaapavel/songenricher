package cz.jollysoft.songenricher;



import java.nio.file.Files;
import java.nio.file.Path;

import cz.jollysoft.songenricher.processors.EnsembleLoader;
import cz.jollysoft.songenricher.processors.EnsembleSaver;
import cz.jollysoft.songenricher.processors.SongEnricher;
import cz.jollysoft.songenricher.processors.SongParser;
import cz.jollysoft.songenricher.processors.SongSerializer;

import static java.nio.file.LinkOption.NOFOLLOW_LINKS;

import java.io.IOException;



/**
 * Orchestrates the whole process of song enrichment.
 * 
 * @author Pavel Foltyn
 */
public class EnrichSongOrchestrator {



    /** Input directory with song files. */
    private Path inputPath;

    /** Output directory to store files with enriched song data. */
    private Path outputPath;



    /**
     * Constructor.
     * 
     * @param inputPath Input path to get song files from.
     * @param outputPath Output path to store new song files with enriched data into.
     */
    public EnrichSongOrchestrator(Path inputPath, Path outputPath) {
        this.inputPath = inputPath;
        this.outputPath = outputPath;
    }



    /**
     * Launch the enrichment process and process all the songs.
     */
    public void launchAndOrchestrate() {

        // Prepare all the processors.
        EnsembleLoader ensembleLoader = new EnsembleLoader();
        SongParser songParser = new SongParser();
        SongEnricher songEnricher = new SongEnricher(inputPath, outputPath);
        SongSerializer songSerializer = new SongSerializer();
        EnsembleSaver ensembleSaver = new EnsembleSaver();

        // Handle an IOException that might occur.
        try {

            // Start the process as a stream.
            //Files.newDirectoryStream(inputPath)
            Files.list(inputPath)
                // Process files, not directories.
                //.filter(Files::isRegularFile)
                .filter(p -> Files.isRegularFile(p, NOFOLLOW_LINKS))
                // Debug info.
                .peek(p -> { System.out.printf("About to load file: %s%n", p.toString()); })
                // Load a file. (Here we've got a Path.)
                //.map(ensembleLoader)
                .map(p -> ensembleLoader.apply(p))
                // Debug info.
                .peek(e -> { System.out.printf("About to analyze file contents of the file: %s%n", e.getPath().toString()); })
                // Parse the file into a song. (Here we've got an Ensemble.)
                //.map(songParser)
                .map(e -> songParser.apply(e))
                // Debug info.
                .peek(s -> { System.out.printf("About to enrich data in the song: %s%n", s.getSongRoot().getTitle().getInnerText()); })
                // Enrich the song. (Here we've got a Song.)
                //.map(songEnricher)
                .map(s -> songEnricher.apply(s))
                // Debug info.
                .peek(s -> { System.out.printf("About to synthesize data of the enriched song: %s%n", s.getSongRoot().getTitle().getInnerText()); })
                // Synthesize the song into text lines. (Here we've still got a Song, but it should be ANOTHER INSTANCE of the Song class.)
                //.map(songSerializer)
                .map(s -> songSerializer.apply(s))
                // Debug info.
                .peek(e -> { System.out.printf("About to save enriched song data to the file: %s%n", e.getPath().toString()); })
                // Store the song to the output directory. (Here we've got an Ensemble.)
                //.forEach(ensembleSaver)
                .forEach(e -> ensembleSaver.accept(e));
            ;

        } catch (IOException e) {
            System.out.printf("Cannot list the directory: %s%n", inputPath.toString());
            throw new RuntimeException(String.format("A problem occurred while trying to list the contents of the given input directory: %s", inputPath.toString()), e);
        }

        // Done.

    }



}
