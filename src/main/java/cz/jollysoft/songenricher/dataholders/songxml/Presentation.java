package cz.jollysoft.songenricher.dataholders.songxml;



/**
 * Represents the presentation of a song.
 * 
 * @author Pavel Foltyn
 */
public class Presentation extends SimpleSongElement {



    /**
     * Constructor.
     * 
     * @param parentElement Parent element of this element.
     */
    public Presentation(SongElement parentElement) {
        this(null, parentElement);
    }



    /**
     * Constructor.
     * 
     * @param innerText Inner text of this element.
     * @param parentElement Parent element of this element.
     */
    public Presentation(String innerText, SongElement parentElement) {
        super(innerText, parentElement);
    }



    @Override
    public String getName() {
        return "presentation";
    }



}