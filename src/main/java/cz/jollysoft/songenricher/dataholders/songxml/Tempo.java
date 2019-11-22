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
        this(null, parentElement);
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



    @Override
    public String getName() {
        return "tempo";
    }



}