package cz.jollysoft.songenricher.dataholders;



import java.util.List;
import java.util.ArrayList;
import java.nio.file.Path;



/**
 * Represents a file in the file system.
 * 
 * @author Pavel Foltyn
 */
public class Ensemble {



    /** Path to the file. */
    private Path path;

    /** List of lines of the file. */
    private List<String> lines;



    public Ensemble(Path path) {
        this.path = path;
        lines = new ArrayList<>();

    }



    public Path getPath() {
        return path;
    }

    public List<String> getLines() {
        return lines;
    }



}
