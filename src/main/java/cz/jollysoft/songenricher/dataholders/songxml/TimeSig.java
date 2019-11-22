package cz.jollysoft.songenricher.dataholders.songxml;



/**
 * Represents the time sig (probably time signal) of a song.
 * 
 * @author Pavel Foltyn
 */
public class TimeSig extends SimpleSongElement {



    /**
     * Constructor.
     * 
     * @param parentElement Parent element of this element.
     */
    public TimeSig(SongElement parentElement) {
        this(null, parentElement);
    }



    /**
     * Constructor.
     * 
     * @param innerText Inner text of this element.
     * @param parentElement Parent element of this element.
     */
    public TimeSig(String innerText, SongElement parentElement) {
        super(innerText, parentElement);
    }



    @Override
    public String getName() {
        return "time_sig";
    }



}