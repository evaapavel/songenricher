package cz.jollysoft.songenricher.dataholders.songxml;



/**
 * Represents the user 2 of a song.
 * 
 * @author Pavel Foltyn
 */
public class User2 extends SimpleSongElement {



    /**
     * Constructor.
     * 
     * @param parentElement Parent element of this element.
     */
    public User2(SongElement parentElement) {
        this((String) null, parentElement);
    }



    /**
     * Constructor.
     * 
     * @param innerText Inner text of this element.
     * @param parentElement Parent element of this element.
     */
    public User2(String innerText, SongElement parentElement) {
        super(innerText, parentElement);
    }



    /**
     * Copy constructor.
     * 
     * @param elementToClone Element to copy.
     * @param newParentElement A song element to be used as the parent of the cloned object.
     */
    public User2(User2 elementToClone, SongElement newParentElement) {
        super(elementToClone, newParentElement);
    }



    @Override
    public String getName() {
        return "user2";
    }



    @Override
    public SongElement clone(SongElement newParentElement) {
        User2 songElement = new User2(this, newParentElement);
        return songElement;
    }



}