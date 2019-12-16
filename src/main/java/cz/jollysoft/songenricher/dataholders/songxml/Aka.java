package cz.jollysoft.songenricher.dataholders.songxml;



/**
 * Represents the aka of a song.
 * 
 * @author Pavel Foltyn
 */
public class Aka extends SimpleSongElement {



    /**
     * Constructor.
     * 
     * @param parentElement Parent element of this element.
     */
    public Aka(SongElement parentElement) {
        this((String) null, parentElement);
    }



    /**
     * Constructor.
     * 
     * @param innerText Inner text of this element.
     * @param parentElement Parent element of this element.
     */
    public Aka(String innerText, SongElement parentElement) {
        super(innerText, parentElement);
    }



    /**
     * Copy constructor.
     * 
     * @param elementToClone Element to copy.
     * @param newParentElement A song element to be used as the parent of the cloned object.
     */
    public Aka(Aka elementToClone, SongElement newParentElement) {
        super(elementToClone, newParentElement);
    }



    @Override
    public String getName() {
        return "aka";
    }



    @Override
    public SongElement clone(SongElement newParentElement) {
        Aka songElement = new Aka(this, newParentElement);
        return songElement;
    }



}