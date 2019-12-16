package cz.jollysoft.songenricher.dataholders.songxml;



/**
 * Represents the tempo of a song.
 * 
 * @author Pavel Foltyn
 */
public class Tempo extends SimpleSongElement {



    /**
     * Constructor.
     * 
     * @param parentElement Parent element of this element.
     */
    public Tempo(SongElement parentElement) {
        this((String) null, parentElement);
    }



    /**
     * Constructor.
     * 
     * @param innerText Inner text of this element.
     * @param parentElement Parent element of this element.
     */
    public Tempo(String innerText, SongElement parentElement) {
        super(innerText, parentElement);
    }



    /**
     * Copy constructor.
     * 
     * @param elementToClone Element to copy.
     * @param newParentElement A song element to be used as the parent of the cloned object.
     */
    public Tempo(Tempo elementToClone, SongElement newParentElement) {
        super(elementToClone, newParentElement);
    }



    @Override
    public String getName() {
        return "tempo";
    }



    @Override
    public SongElement clone(SongElement newParentElement) {
        Tempo songElement = new Tempo(this, newParentElement);
        return songElement;
    }



}