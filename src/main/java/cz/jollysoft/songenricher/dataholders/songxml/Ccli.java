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
        this((String) null, parentElement);
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



    /**
     * Copy constructor.
     * 
     * @param elementToClone Element to copy.
     * @param newParentElement A song element to be used as the parent of the cloned object.
     */
    public Ccli(Ccli elementToClone, SongElement newParentElement) {
        super(elementToClone, newParentElement);
    }



    @Override
    public String getName() {
        return "ccli";
    }



    @Override
    public SongElement clone(SongElement newParentElement) {
        Ccli songElement = new Ccli(this, newParentElement);
        return songElement;
    }



}