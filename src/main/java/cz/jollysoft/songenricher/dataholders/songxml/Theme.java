package cz.jollysoft.songenricher.dataholders.songxml;



/**
 * Represents the theme of a song.
 * 
 * @author Pavel Foltyn
 */
public class Theme extends SimpleSongElement {



    /**
     * Constructor.
     * 
     * @param parentElement Parent element of this element.
     */
    public Theme(SongElement parentElement) {
        this((String) null, parentElement);
    }



    /**
     * Constructor.
     * 
     * @param innerText Inner text of this element.
     * @param parentElement Parent element of this element.
     */
    public Theme(String innerText, SongElement parentElement) {
        super(innerText, parentElement);
    }



    /**
     * Copy constructor.
     * 
     * @param elementToClone Element to copy.
     * @param newParentElement A song element to be used as the parent of the cloned object.
     */
    public Theme(Theme elementToClone, SongElement newParentElement) {
        super(elementToClone, newParentElement);
    }



    @Override
    public String getName() {
        return "theme";
    }



    @Override
    public SongElement clone(SongElement newParentElement) {
        Theme songElement = new Theme(this, newParentElement);
        return songElement;
    }



}