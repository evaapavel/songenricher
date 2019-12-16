package cz.jollysoft.songenricher.dataholders.songxml;



/**
 * Represents the key line of a song.
 * 
 * @author Pavel Foltyn
 */
public class KeyLine extends SimpleSongElement {



    /**
     * Constructor.
     * 
     * @param parentElement Parent element of this element.
     */
    public KeyLine(SongElement parentElement) {
        this((String) null, parentElement);
    }



    /**
     * Constructor.
     * 
     * @param innerText Inner text of this element.
     * @param parentElement Parent element of this element.
     */
    public KeyLine(String innerText, SongElement parentElement) {
        super(innerText, parentElement);
    }



    /**
     * Copy constructor.
     * 
     * @param elementToClone Element to copy.
     * @param newParentElement A song element to be used as the parent of the cloned object.
     */
    public KeyLine(KeyLine elementToClone, SongElement newParentElement) {
        super(elementToClone, newParentElement);
    }



    @Override
    public String getName() {
        return "key_line";
    }



    @Override
    public SongElement clone(SongElement newParentElement) {
        KeyLine songElement = new KeyLine(this, newParentElement);
        return songElement;
    }



}