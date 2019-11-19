package cz.jollysoft.songenricher.dataholders;



/**
 * Represents a song to be adjusted.
 * 
 * @author Pavel Foltyn
 */
public class Song {



    /** File contents of this song. */
    private Ensemble ensemble;



    /**
     * Constructor.
     * 
     * @param ensemble File contents of this song.
     */
    public Song(Ensemble ensemble) {
        this.ensemble = ensemble;
    }



    public Ensemble getEnsemble() {
        return ensemble;
    }



}
