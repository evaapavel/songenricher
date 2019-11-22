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
        this(null, parentElement);
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



    @Override
    public String getName() {
        return "theme";
    }



}