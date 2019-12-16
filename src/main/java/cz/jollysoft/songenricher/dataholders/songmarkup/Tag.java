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



    /**
     * Copy constructor.
     * 
     * @param sectionToCopy Section to copy.
     */
    public Tag(Tag sectionToCopy) {
        super(sectionToCopy);
    }



    @Override
    public Object clone() throws CloneNotSupportedException {
        Tag section = new Tag(this);
        return section;
    }



}

