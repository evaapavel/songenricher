package cz.jollysoft.songenricher.dataholders.songxml;



/**
 * Represents the key of a song.
 * 
 * @author Pavel Foltyn
 */
public class Key extends SimpleSongElement {



    /**
     * Constructor.
     * 
     * @param parentElement Parent element of this element.
     */
    public Key(SongElement parentElement) {
        this((String) null, parentElement);
    }



    /**
     * Constructor.
     * 
     * @param innerText Inner text of this element.
     * @param parentElement Parent element of this element.
     */
    public Key(String innerText, SongElement parentElement) {
        super(innerText, parentElement);
    }



    /**
     * Copy constructor.
     * 
     * @param elementToClone Element to copy.
     * @param newParentElement A song element to be used as the parent of the cloned object.
     */
    public Key(Key elementToClone, SongElement newParentElement) {
        super(elementToClone, newParentElement);
    }



    @Override
    public String getName() {
        return "key";
    }



    @Override
    public SongElement clone(SongElement newParentElement) {
        Key songElement = new Key(this, newParentElement);
        return songElement;
    }



}