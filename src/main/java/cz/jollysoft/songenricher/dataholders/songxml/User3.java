package cz.jollysoft.songenricher.dataholders.songxml;



/**
 * Represents the user 3 of a song.
 * 
 * @author Pavel Foltyn
 */
public class User3 extends SimpleSongElement {



    /**
     * Constructor.
     * 
     * @param parentElement Parent element of this element.
     */
    public User3(SongElement parentElement) {
        this(null, parentElement);
    }



    /**
     * Constructor.
     * 
     * @param innerText Inner text of this element.
     * @param parentElement Parent element of this element.
     */
    public User3(String innerText, SongElement parentElement) {
        super(innerText, parentElement);
    }



    @Override
    public String getName() {
        return "user3";
    }



}