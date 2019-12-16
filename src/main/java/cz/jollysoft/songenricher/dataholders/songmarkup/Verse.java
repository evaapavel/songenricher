package cz.jollysoft.songenricher.dataholders.songmarkup;



/**
 * Represents a verse (V1, V2, ..., sometimes also V1A, V1B, V2A, V2B, ...) in the song lyrics.
 * 
 * @author Pavel Foltyn
 */
public class Verse extends Section {



    /**
     * Constructor.
     * 
     * @param name Name of this section.
     * @param lyricsText Part of the lyrics belonging to this section.
     */
    public Verse(String name, String lyricsText) {
        super(name, lyricsText);
    }



    /**
     * Copy constructor.
     * 
     * @param sectionToCopy Section to copy.
     */
    public Verse(Verse sectionToCopy) {
        super(sectionToCopy);
    }



    @Override
    public Object clone() throws CloneNotSupportedException {
        Verse section = new Verse(this);
        return section;
    }



}

