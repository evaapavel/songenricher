package cz.jollysoft.songenricher.processors;



import java.io.IOException;
//import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
//import java.nio.channels.ByteChannel;
import java.nio.channels.FileChannel;
//import java.nio.channels.FileLock;
//import java.nio.channels.ReadableByteChannel;
//import java.nio.channels.WritableByteChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.List;
//import java.nio.file.Path;
//import java.util.function.BiConsumer;
import java.util.function.Consumer;

import cz.jollysoft.songenricher.dataholders.Ensemble;
import cz.jollysoft.songenricher.exceptions.PipelineException;

import static cz.jollysoft.songenricher.constants.AppConstants.NEWLINE_SEQUENCE;



//public class EnsembleSaver implements BiConsumer<Ensemble, Path> {
/**
 * Saves the contents of a file to the file system.
 * 
 * @author Pavel Foltyn
 */
public class EnsembleSaver implements Consumer<Ensemble> {



    @Override
    public void accept(Ensemble ensemble) {

        // Create a byte buffer, store all the lines in the buffer, open an output channel and map the buffer to the channel.
        save(ensemble);

    }



    /**
     * Saves the contents of the file (ensemble) to the disk using a channel.
     * 
     * @param ensemble File contents to be saved.
     */
    private void save(Ensemble ensemble) {

        // Prepare a file channel reference.
        FileChannel outputFile = null;

        try {

            // Access the list of the lines, convert it to a single string, and convert the string into an array of bytes.
            List<String> lines = ensemble.getLines();
            String fileContentsAsString = lines.stream()
                .reduce((acc, line) -> acc.concat(NEWLINE_SEQUENCE).concat(line))
                // Here we have an optional.
                .orElse("")
            ;
            byte[] fileContentsAsByteArray = fileContentsAsString.getBytes(Charset.forName("UTF-8"));

            // Prepare a byte buffer with an appropriate size. Fill in the buffer with data.
            //ByteBuffer buffer = ByteBuffer.allocate(fileContentsAsByteArray.length);
            // See below.

            // Open a channel for writing and map the byte buffer to the channel.
            outputFile = (FileChannel) Files.newByteChannel(ensemble.getPath(), StandardOpenOption.CREATE, StandardOpenOption.READ, StandardOpenOption.WRITE);
            MappedByteBuffer buffer = outputFile.map(MapMode.READ_WRITE, 0L, (long) fileContentsAsByteArray.length);

            // Write the file contents to the buffer.
            // The channel-buffer mapping should handle the rest.
            //for (int i = 0; i < fileContentsAsByteArray.length; i++) {
            //    buffer.put(fileContentsAsByteArray[i]);
            //}
            buffer.put(fileContentsAsByteArray);

        } catch (IOException e) {
            throw new PipelineException(String.format("I/O error occurred while trying to save data to the file '%s'", ensemble.getPath().toString()), e);
        } finally {
            // Close the channel.
            if (outputFile != null) {
                try {
                    outputFile.close();
                } catch (IOException e) {
                    System.out.println(String.format("Exception thrown while closing the file '%s'. The exception was: %s", ensemble.getPath().toString(), e.toString()));
                }
            }
        }

        // Done.

    }



}
