package cz.jollysoft.songenricher.dataholders.songxml;



/**
 * Represents the CCLI (song file version, date of the last modification) of a song.
 * 
 * @author Pavel Foltyn
 */
public class Ccli extends SimpleSongElement {



    /**
     * Constructor.
     * 
     * @param parentElement Parent element of this element.
     */
    public Ccli(SongElement parentElement) {
        this(null, parentElement);
    }



    /**
     * Constructor.
     * 
     * @param innerText Inner text of this element.
     * @param parentElement Parent element of this element.
     */
    public Ccli(String innerText, SongElement parentElement) {
        super(innerText, parentElement);
    }



    @Override
    public String getName() {
        return "ccli";
    }



}