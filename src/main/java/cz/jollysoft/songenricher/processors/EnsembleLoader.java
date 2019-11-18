package cz.jollysoft.songenricher.processors;



import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.Files;
import java.nio.channels.Channel;
import java.nio.channels.SeekableByteChannel;
import java.nio.channels.FileChannel;
import java.util.function.Function;

import cz.jollysoft.songenricher.dataholders.Ensemble;
import cz.jollysoft.songenricher.exceptions.PipelineException;



/**
 * Converts a Path object to something that can be later processed.
 * 
 * @author Pavel Foltyn
 */
public class EnsembleLoader implements Function<Path, Ensemble> {



    /** Contents of the file. */
    private Ensemble ensemble;



    @Override
    public Ensemble apply(Path path) {
        ensemble = new Ensemble(path);
        loadContents();
        return ensemble;
    }



    /**
     * Loads the contents of the file into the Ensemble object.
     */
    private void loadContents() {

        // Prepare a buffer and a channel.
        //try ( Channel fChan = FileChannel.open(ensemble.getPath(), StandardOpenOption.READ) ) {
        //try ( Channel channel = Files.newByteChannel(ensemble.getPath(), StandardOpenOption.READ) ) {
        try ( SeekableByteChannel channel = Files.newByteChannel(ensemble.getPath(), StandardOpenOption.READ) ) {

            // Get a reference to the FileChannel object.
            FileChannel fileChannel = (FileChannel) channel;

            // Load the contents of the file using exclicit read operations.

            // Store the file contents into the Ensemble object.

        } catch (IOException e) {
            throw new PipelineException(String.format("I/O error occurred while trying to load the file '%s'", ensemble.getPath().toString()), e);
        }

    }



}