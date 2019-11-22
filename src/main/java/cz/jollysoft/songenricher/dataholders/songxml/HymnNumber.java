package cz.jollysoft.songenricher.dataholders.songxml;



/**
 * Represents the hymn number of a song.
 * 
 * @author Pavel Foltyn
 */
public class HymnNumber extends SimpleSongElement {



    /**
     * Constructor.
     * 
     * @param parentElement Parent element of this element.
     */
    public HymnNumber(SongElement parentElement) {
        this(null, parentElement);
    }



    /**
     * Constructor.
     * 
     * @param innerText Inner text of this element.
     * @param parentElement Parent element of this element.
     */
    public HymnNumber(String innerText, SongElement parentElement) {
        super(innerText, parentElement);
    }



    @Override
    public String getName() {
        return "hymn_number";
    }



}