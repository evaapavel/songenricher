package cz.jollysoft.songenricher.processors;



import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.Files;
//import java.nio.channels.Channel;
//import java.nio.channels.SeekableByteChannel;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
//import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import cz.jollysoft.songenricher.dataholders.Ensemble;
import cz.jollysoft.songenricher.exceptions.PipelineException;
import cz.jollysoft.songenricher.transformers.Liner;



/**
 * Converts a Path object to something that can be later processed.
 * 
 * @author Pavel Foltyn
 */
public class EnsembleLoader implements Function<Path, Ensemble> {



    /** Number of bytes to allocate for the ByteBuffer. */
    private static final int BUFFER_SIZE = 128;



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
     * Assume an Ensemble object has already been initialized. Which means it has a Path and an empty list of lines.
     */
    private void loadContents() {

        // Prepare a buffer and a channel.
        //try ( Channel fChan = FileChannel.open(ensemble.getPath(), StandardOpenOption.READ) ) {
        //try ( Channel channel = Files.newByteChannel(ensemble.getPath(), StandardOpenOption.READ) ) {
        //try ( SeekableByteChannel channel = Files.newByteChannel(ensemble.getPath(), StandardOpenOption.READ) ) {
        try ( FileChannel fileChannel = (FileChannel) Files.newByteChannel(ensemble.getPath(), StandardOpenOption.READ) ) {

            //// Get a reference to the FileChannel object.
            //FileChannel fileChannel = (FileChannel) channel;

            // Prepare a buffer.
            //ByteBuffer buffer = new ByteBuffer(BUFFER_SIZE);
            ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);

            // Prepare a line extracting tool.
            Liner liner = new Liner();

            //// Prepare a list of lines.
            //List<String> lines = new ArrayList<>();
            // Use the lines in Ensemble.

            // Load the contents of the file using explicit read operations.
            for (;;) {

                // Load bytes into the buffer.
                int bytesRead = fileChannel.read(buffer);

                // Check for EOF.
                if (bytesRead == -1) {
                    // Stop reading, the file has been fully loaded from the disk.
                    break;
                }

                // Process the bytes read.
                //liner.addBytes(buffer, 0, bytesRead);
                //List<String> linesToAdd = liner.getLines();
                ////lines.addAll(linesToAdd);
                //ensemble.getLines().addAll(linesToAdd);
                processBytes(buffer, bytesRead, liner);

                // Mark the buffer as "empty" via "rewind".
                buffer.rewind();

            }

            // Process the rest of the bytes read.
            // There's nothing to process here since the last operation encountered EOF.
            // Actually, there might still be something to process (for Mac files, if the last line ends with CR).
            processBytes(buffer, 0, liner);

            // Store the file contents into the Ensemble object.
            // This has been done inside the loop already.

        } catch (IOException e) {
            throw new PipelineException(String.format("I/O error occurred while trying to load the file '%s'", ensemble.getPath().toString()), e);
        }

    }



    /**
     * Processes bytes that have just been loaded from the file (channel) into the buffer.
     * 
     * @param buffer Buffer with byte data to be processed.
     * @param bytesRead Number of bytes read during the last read from the channel.
     * @param liner A byte-array-to-line transformer.
     */
    private void processBytes(ByteBuffer buffer, int bytesRead, Liner liner) {

        // Add the bytes just read to the transformer tool.
        if (bytesRead > 0) {
            liner.addBytes(buffer, 0, bytesRead);
        }

        // Try to extract new lines from the transformer.
        //List<String> linesToAdd = liner.getLines();
        boolean forceProcessCR = (bytesRead == 0);
        List<String> linesToAdd = liner.getLines(forceProcessCR);

        // Add the lines to the Ensemble object.
        //lines.addAll(linesToAdd);
        ensemble.getLines().addAll(linesToAdd);

    }



}