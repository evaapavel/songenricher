package cz.jollysoft.songenricher.processors;



import java.util.function.BiFunction;
import java.nio.file.Path;

import cz.jollysoft.songenricher.dataholders.Ensemble;
import cz.jollysoft.songenricher.dataholders.Song;



/**
 * Converts file a Song object to something that can be later saved to the file system.
 * 
 * @author Pavel Foltyn
 */
public class SongSerializer implements BiFunction<Song, Path, Ensemble> {




    @Override
    public Ensemble apply(Song song, Path path) {
        return new Ensemble(path);
    }



}
