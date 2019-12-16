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
        this((String) null, parentElement);
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



    /**
     * Copy constructor.
     * 
     * @param elementToClone Element to copy.
     * @param newParentElement A song element to be used as the parent of the cloned object.
     */
    public HymnNumber(HymnNumber elementToClone, SongElement newParentElement) {
        super(elementToClone, newParentElement);
    }



    @Override
    public String getName() {
        return "hymn_number";
    }



    @Override
    public SongElement clone(SongElement newParentElement) {
        HymnNumber songElement = new HymnNumber(this, newParentElement);
        return songElement;
    }



}