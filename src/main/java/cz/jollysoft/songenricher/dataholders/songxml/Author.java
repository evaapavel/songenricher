package cz.jollysoft.songenricher.dataholders.songxml;



/**
 * Represents the author of a song.
 * 
 * @author Pavel Foltyn
 */
public class Author extends SimpleSongElement {



    /**
     * Constructor.
     * 
     * @param parentElement Parent element of this element.
     */
    public Author(SongElement parentElement) {
        this((String) null, parentElement);
    }



    /**
     * Constructor.
     * 
     * @param innerText Inner text of this element.
     * @param parentElement Parent element of this element.
     */
    public Author(String innerText, SongElement parentElement) {
        super(innerText, parentElement);
    }



    /**
     * Copy constructor.
     * 
     * @param elementToClone Element to copy.
     * @param newParentElement A song element to be used as the parent of the cloned object.
     */
    public Author(Author elementToClone, SongElement newParentElement) {
        super(elementToClone, newParentElement);
    }



    @Override
    public String getName() {
        return "author";
    }



    @Override
    public SongElement clone(SongElement newParentElement) {
        Author songElement = new Author(this, newParentElement);
        return songElement;
    }



}