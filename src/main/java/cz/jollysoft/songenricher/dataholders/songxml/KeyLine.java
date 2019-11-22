package cz.jollysoft.songenricher.dataholders.songxml;



/**
 * Represents the key line of a song.
 * 
 * @author Pavel Foltyn
 */
public class KeyLine extends SimpleSongElement {



    /**
     * Constructor.
     * 
     * @param parentElement Parent element of this element.
     */
    public KeyLine(SongElement parentElement) {
        this(null, parentElement);
    }



    /**
     * Constructor.
     * 
     * @param innerText Inner text of this element.
     * @param parentElement Parent element of this element.
     */
    public KeyLine(String innerText, SongElement parentElement) {
        super(innerText, parentElement);
    }



    @Override
    public String getName() {
        return "key_line";
    }



}