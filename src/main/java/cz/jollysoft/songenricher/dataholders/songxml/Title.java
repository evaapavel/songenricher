package cz.jollysoft.songenricher.dataholders.songxml;



/**
 * Represents the title of a song.
 * 
 * @author Pavel Foltyn
 */
public class Title extends SimpleSongElement {



    /**
     * Constructor.
     * 
     * @param parentElement Parent element of this element.
     */
    public Title(SongElement parentElement) {
        this((String) null, parentElement);
    }



    /**
     * Constructor.
     * 
     * @param innerText Inner text of this element.
     * @param parentElement Parent element of this element.
     */
    public Title(String innerText, SongElement parentElement) {
        super(innerText, parentElement);
    }



    /**
     * Copy constructor.
     * 
     * @param elementToClone Element to copy.
     * @param newParentElement A song element to be used as the parent of the cloned object.
     */
    public Title(Title elementToClone, SongElement newParentElement) {
        super(elementToClone, newParentElement);
    }



    @Override
    public String getName() {
        return "title";
    }



    @Override
    public SongElement clone(SongElement newParentElement) {
        Title songElement = new Title(this, newParentElement);
        return songElement;
    }



}