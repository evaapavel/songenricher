package cz.jollysoft.songenricher.dataholders.songmarkup;



/**
 * Represents a tag (T) in the song lyrics.
 * 
 * @author Pavel Foltyn
 */
public class Tag extends Section {



    /**
     * Constructor.
     * 
     * @param name Name of this section.
     * @param lyricsText Part of the lyrics belonging to this section.
     */
    public Tag(String name, String lyricsText) {
        super(name, lyricsText);
    }



}

