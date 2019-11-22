package cz.jollysoft.songenricher.dataholders.songxml;



/**
 * Represents the author of a song.
 * 
 * @author Pavel Foltyn
 */
public class Author extends SimpleSongElement {



    /**
     * Constructor.
     * 
     * @param parentElement Parent element of this element.
     */
    public Author(SongElement parentElement) {
        this(null, parentElement);
    }



    /**
     * Constructor.
     * 
     * @param innerText Inner text of this element.
     * @param parentElement Parent element of this element.
     */
    public Author(String innerText, SongElement parentElement) {
        super(innerText, parentElement);
    }



    @Override
    public String getName() {
        return "author";
    }



}