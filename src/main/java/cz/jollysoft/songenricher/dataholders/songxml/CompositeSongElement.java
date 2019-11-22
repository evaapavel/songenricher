package cz.jollysoft.songenricher.dataholders.songxml;



import java.util.ArrayList;
import java.util.List;



/**
 * Represents a song element that can have subelements.
 * Such an element (a composite element) cannot have inner text.
 * 
 * @author Pavel Foltyn
 */
public abstract class CompositeSongElement extends SongElement {



    /** Subelements of this element. */
    private List<SongElement> subelements;



    /**
     * Constructor.
     * 
     * @param parentElement Parent element of this element.
     */
    public CompositeSongElement(SongElement parentElement) {
        super(parentElement);
        subelements = new ArrayList<>();
    }



    public List<SongElement> getSubelements() {
        return subelements;
    }

    //public void setSubelements(List<SongElement> subelements) {
    //    this.subelements = subelements;
    //}



}
