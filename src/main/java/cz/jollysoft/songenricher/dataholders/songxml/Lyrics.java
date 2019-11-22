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
        this(null, parentElement);
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



}
