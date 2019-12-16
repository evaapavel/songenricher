package cz.jollysoft.songenricher.dataholders.songxml;



/**
 * Represents the presentation of a song.
 * 
 * @author Pavel Foltyn
 */
public class Presentation extends SimpleSongElement {



    /**
     * Constructor.
     * 
     * @param parentElement Parent element of this element.
     */
    public Presentation(SongElement parentElement) {
        this((String) null, parentElement);
    }



    /**
     * Constructor.
     * 
     * @param innerText Inner text of this element.
     * @param parentElement Parent element of this element.
     */
    public Presentation(String innerText, SongElement parentElement) {
        super(innerText, parentElement);
    }



    /**
     * Copy constructor.
     * 
     * @param elementToClone Element to copy.
     * @param newParentElement A song element to be used as the parent of the cloned object.
     */
    public Presentation(Presentation elementToClone, SongElement newParentElement) {
        super(elementToClone, newParentElement);
    }



    @Override
    public String getName() {
        return "presentation";
    }



    @Override
    public SongElement clone(SongElement newParentElement) {
        Presentation songElement = new Presentation(this, newParentElement);
        return songElement;
    }



}