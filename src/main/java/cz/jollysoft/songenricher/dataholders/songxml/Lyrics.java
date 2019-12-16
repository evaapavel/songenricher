package cz.jollysoft.songenricher.dataholders.songxml;



import java.util.List;
import java.util.ArrayList;

import cz.jollysoft.songenricher.dataholders.songmarkup.Section;



/**
 * Represents the lyrics of a song.
 * 
 * @author Pavel Foltyn
 */
public class Lyrics extends SimpleSongElement {



    /** Sections of the song lyrics. */
    private List<Section> sections;



    /**
     * Constructor.
     * 
     * @param parentElement Parent element of this element.
     */
    public Lyrics(SongElement parentElement) {
        this((String) null, parentElement);
    }



    /**
     * Constructor.
     * 
     * @param innerText     Inner text of this element.
     * @param parentElement Parent element of this element.
     */
    public Lyrics(String innerText, SongElement parentElement) {
        super(innerText, parentElement);
        sections = new ArrayList<>();
    }



    /**
     * Copy constructor.
     * 
     * @param elementToClone Element to copy.
     * @param newParentElement A song element to be used as the parent of the cloned object.
     */
    @SuppressWarnings("unchecked")
    public Lyrics(Lyrics elementToClone, SongElement newParentElement) {
        super(elementToClone, newParentElement);
        if (elementToClone.sections != null) {
            this.sections = (List<Section>) ((ArrayList<Section>) elementToClone.sections).clone();
        } else {
            this.sections = null;
        }
    }



    public List<Section> getSections() {
        return sections;
    }

    //public void setSections(List<Section> sections) {
    //    this.sections = sections;
    //}



    @Override
    public String getName() {
        return "lyrics";
    }



    @Override
    public SongElement clone(SongElement newParentElement) {
        Lyrics songElement = new Lyrics(this, newParentElement);
        return songElement;
    }



}
