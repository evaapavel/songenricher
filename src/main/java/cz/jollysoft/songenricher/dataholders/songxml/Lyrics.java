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



    //@SuppressWarnings("unchecked")
    /**
     * Copy constructor.
     * 
     * @param elementToClone Element to copy.
     * @param newParentElement A song element to be used as the parent of the cloned object.
     */
    public Lyrics(Lyrics elementToClone, SongElement newParentElement) throws CloneNotSupportedException {
        super(elementToClone, newParentElement);
        if (elementToClone.sections != null) {
            //this.sections = (List<Section>) ((ArrayList<Section>) elementToClone.sections).clone();
            this.sections = new ArrayList<>();
            for (Section section : elementToClone.sections) {
                this.sections.add((Section) section.clone());
            }
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
    public SongElement clone(SongElement newParentElement) throws CloneNotSupportedException {
        Lyrics songElement = new Lyrics(this, newParentElement);
        return songElement;
    }



}
