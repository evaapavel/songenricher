package cz.jollysoft.songenricher.dataholders;



import java.util.List;
import java.util.ArrayList;
import java.nio.file.Path;



/**
 * Represents a file in the file system.
 * 
 * @author Pavel Foltyn
 */
public class Ensemble implements Cloneable {



    /** Path to the file. */
    private Path path;

    /** List of lines of the file. */
    private List<String> lines;



    /**
     * Constructor.
     * 
     * @param path Path to the file.
     */
    public Ensemble(Path path) {
        this.path = path;
        lines = new ArrayList<>();
    }



    /**
     * Copy constructor.
     * 
     * @param ensemble Ensemble to copy.
     */
    @SuppressWarnings("unchecked")
    public Ensemble(Ensemble ensemble) throws CloneNotSupportedException {
        this.path = ensemble.path;
        if (ensemble.lines != null) {
            //this.lines = (List<String>) ensemble.lines.clone();
            this.lines = (List<String>) ((ArrayList<String>) ensemble.lines).clone();
        } else {
            this.lines = null;
        }
    }



    public Path getPath() {
        return path;
    }

    //public void setPath(Path path) {
    //    this.path = path;
    //}

    public List<String> getLines() {
        return lines;
    }

    public void setLines(List<String> lines) {
        this.lines = lines;
    }



    //@SuppressWarnings("unchecked")
    @Override
    public Object clone() throws CloneNotSupportedException {
        //Ensemble ensemble = new Ensemble(this.path);
        //if (this.lines != null) {
        //    ensemble.lines = (List<String>) ((ArrayList<String>) this.lines).clone();
        //} else {
        //    ensemble.lines = null;
        //}
        Ensemble ensemble = new Ensemble(this);
        return ensemble;
    }



}
