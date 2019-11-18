package cz.jollysoft.songenricher.processors;



import java.util.function.UnaryOperator;

import cz.jollysoft.songenricher.dataholders.Song;



/**
 * Adds useful information to the song.
 * 
 * @author Pavel Foltyn
 */
public class SongEnricher implements UnaryOperator<Song> {



    public Song apply(Song song) {
        return new Song();
    }



}
