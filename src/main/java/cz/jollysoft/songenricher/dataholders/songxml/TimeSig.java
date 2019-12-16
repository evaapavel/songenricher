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
        this((String) null, parentElement);
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



    /**
     * Copy constructor.
     * 
     * @param elementToClone Element to copy.
     * @param newParentElement A song element to be used as the parent of the cloned object.
     */
    public TimeSig(TimeSig elementToClone, SongElement newParentElement) {
        super(elementToClone, newParentElement);
    }



    @Override
    public String getName() {
        return "time_sig";
    }



    @Override
    public SongElement clone(SongElement newParentElement) {
        TimeSig songElement = new TimeSig(this, newParentElement);
        return songElement;
    }



}