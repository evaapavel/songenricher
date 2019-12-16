package cz.jollysoft.songenricher.dataholders.songmarkup;



/**
 * Represents a bridge (B) in the song lyrics.
 * 
 * @author Pavel Foltyn
 */
public class Bridge extends Section {



    /**
     * Constructor.
     * 
     * @param name Name of this section.
     * @param lyricsText Part of the lyrics belonging to this section.
     */
    public Bridge(String name, String lyricsText) {
        super(name, lyricsText);
    }



    /**
     * Copy constructor.
     * 
     * @param sectionToCopy Section to copy.
     */
    public Bridge(Bridge sectionToCopy) {
        super(sectionToCopy);
    }



    @Override
    public Object clone() throws CloneNotSupportedException {
        Bridge section = new Bridge(this);
        return section;
    }



}

