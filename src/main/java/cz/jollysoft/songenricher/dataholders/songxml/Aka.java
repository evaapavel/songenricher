package cz.jollysoft.songenricher.dataholders.songxml;



/**
 * Represents the aka of a song.
 * 
 * @author Pavel Foltyn
 */
public class Aka extends SimpleSongElement {



    /**
     * Constructor.
     * 
     * @param parentElement Parent element of this element.
     */
    public Aka(SongElement parentElement) {
        this(null, parentElement);
    }



    /**
     * Constructor.
     * 
     * @param innerText Inner text of this element.
     * @param parentElement Parent element of this element.
     */
    public Aka(String innerText, SongElement parentElement) {
        super(innerText, parentElement);
    }



    @Override
    public String getName() {
        return "aka";
    }



}