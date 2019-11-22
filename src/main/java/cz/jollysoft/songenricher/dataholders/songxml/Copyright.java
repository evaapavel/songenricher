package cz.jollysoft.songenricher.dataholders.songxml;



/**
 * Represents the copyright of a song.
 * 
 * @author Pavel Foltyn
 */
public class Copyright extends SimpleSongElement {



    /**
     * Constructor.
     * 
     * @param parentElement Parent element of this element.
     */
    public Copyright(SongElement parentElement) {
        this(null, parentElement);
    }



    /**
     * Constructor.
     * 
     * @param innerText Inner text of this element.
     * @param parentElement Parent element of this element.
     */
    public Copyright(String innerText, SongElement parentElement) {
        super(innerText, parentElement);
    }



    @Override
    public String getName() {
        return "copyright";
    }



}