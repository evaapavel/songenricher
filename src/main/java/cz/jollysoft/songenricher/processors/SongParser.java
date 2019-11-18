package cz.jollysoft.songenricher.processors;



import java.util.function.Function;

import cz.jollysoft.songenricher.dataholders.Ensemble;
import cz.jollysoft.songenricher.dataholders.Song;



/**
 * Converts file contents to a Song.
 * 
 * @author Pavel Foltyn
 */
public class SongParser implements Function<Ensemble, Song> {




    @Override
    public Song apply(Ensemble ensemble) {
        return new Song();
    }



}
