package cz.jollysoft.songenricher.dataholders.songxml;



/**
 * Represents the key of a song.
 * 
 * @author Pavel Foltyn
 */
public class Key extends SimpleSongElement {



    /**
     * Constructor.
     * 
     * @param parentElement Parent element of this element.
     */
    public Key(SongElement parentElement) {
        this(null, parentElement);
    }



    /**
     * Constructor.
     * 
     * @param innerText Inner text of this element.
     * @param parentElement Parent element of this element.
     */
    public Key(String innerText, SongElement parentElement) {
        super(innerText, parentElement);
    }



    @Override
    public String getName() {
        return "key";
    }



}