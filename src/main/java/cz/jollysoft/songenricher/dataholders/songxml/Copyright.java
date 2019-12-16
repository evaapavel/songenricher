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
        this((String) null, parentElement);
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



    /**
     * Copy constructor.
     * 
     * @param elementToClone Element to copy.
     * @param newParentElement A song element to be used as the parent of the cloned object.
     */
    public Copyright(Copyright elementToClone, SongElement newParentElement) {
        super(elementToClone, newParentElement);
    }



    @Override
    public String getName() {
        return "copyright";
    }



    @Override
    public SongElement clone(SongElement newParentElement) {
        Copyright songElement = new Copyright(this, newParentElement);
        return songElement;
    }



}