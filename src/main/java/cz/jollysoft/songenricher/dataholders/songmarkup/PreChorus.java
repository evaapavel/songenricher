package cz.jollysoft.songenricher.dataholders.songmarkup;



/**
 * Represents a pre-chorus (P) in the song lyrics.
 * 
 * @author Pavel Foltyn
 */
public class PreChorus extends Section {



    /**
     * Constructor.
     * 
     * @param name Name of this section.
     * @param lyricsText Part of the lyrics belonging to this section.
     */
    public PreChorus(String name, String lyricsText) {
        super(name, lyricsText);
    }



    /**
     * Copy constructor.
     * 
     * @param sectionToCopy Section to copy.
     */
    public PreChorus(PreChorus sectionToCopy) {
        super(sectionToCopy);
    }



    @Override
    public Object clone() throws CloneNotSupportedException {
        PreChorus section = new PreChorus(this);
        return section;
    }



}

