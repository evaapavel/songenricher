package cz.jollysoft.songenricher.dataholders.songxml;



/**
 * Represents the user 3 of a song.
 * 
 * @author Pavel Foltyn
 */
public class User3 extends SimpleSongElement {



    /**
     * Constructor.
     * 
     * @param parentElement Parent element of this element.
     */
    public User3(SongElement parentElement) {
        this((String) null, parentElement);
    }



    /**
     * Constructor.
     * 
     * @param innerText Inner text of this element.
     * @param parentElement Parent element of this element.
     */
    public User3(String innerText, SongElement parentElement) {
        super(innerText, parentElement);
    }



    /**
     * Copy constructor.
     * 
     * @param elementToClone Element to copy.
     * @param newParentElement A song element to be used as the parent of the cloned object.
     */
    public User3(User3 elementToClone, SongElement newParentElement) {
        super(elementToClone, newParentElement);
    }



    @Override
    public String getName() {
        return "user3";
    }



    @Override
    public SongElement clone(SongElement newParentElement) {
        User3 songElement = new User3(this, newParentElement);
        return songElement;
    }



}