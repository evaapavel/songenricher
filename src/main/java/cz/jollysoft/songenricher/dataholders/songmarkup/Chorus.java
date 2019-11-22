package cz.jollysoft.songenricher.dataholders.songmarkup;



/**
 * Represents a chorus (C, sometimes also C1, C2, ...) in the song lyrics.
 * 
 * @author Pavel Foltyn
 */
public class Chorus extends Section {



    /**
     * Constructor.
     * 
     * @param name Name of this section.
     * @param lyricsText Part of the lyrics belonging to this section.
     */
    public Chorus(String name, String lyricsText) {
        super(name, lyricsText);
    }



}

