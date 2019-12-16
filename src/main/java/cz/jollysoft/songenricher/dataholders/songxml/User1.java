package cz.jollysoft.songenricher.dataholders.songxml;



/**
 * Represents the user 1 of a song.
 * 
 * @author Pavel Foltyn
 */
public class User1 extends SimpleSongElement {



    /**
     * Constructor.
     * 
     * @param parentElement Parent element of this element.
     */
    public User1(SongElement parentElement) {
        this((String) null, parentElement);
    }



    /**
     * Constructor.
     * 
     * @param innerText Inner text of this element.
     * @param parentElement Parent element of this element.
     */
    public User1(String innerText, SongElement parentElement) {
        super(innerText, parentElement);
    }



    /**
     * Copy constructor.
     * 
     * @param elementToClone Element to copy.
     * @param newParentElement A song element to be used as the parent of the cloned object.
     */
    public User1(User1 elementToClone, SongElement newParentElement) {
        super(elementToClone, newParentElement);
    }



    @Override
    public String getName() {
        return "user1";
    }



    @Override
    public SongElement clone(SongElement newParentElement) {
        User1 songElement = new User1(this, newParentElement);
        return songElement;
    }



}