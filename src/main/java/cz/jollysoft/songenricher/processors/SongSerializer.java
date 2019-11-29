package cz.jollysoft.songenricher.processors;



//import java.util.function.BiFunction;
import java.util.function.Function;
//import java.nio.file.Path;
import java.util.List;

import cz.jollysoft.songenricher.dataholders.Ensemble;
import cz.jollysoft.songenricher.dataholders.Song;
import cz.jollysoft.songenricher.transformers.Songer;
import cz.jollysoft.songenricher.transformers.Xmler;
import cz.jollysoft.songenricher.xmlpieces.Document;



//public class SongSerializer implements BiFunction<Song, Path, Ensemble> {
/**
 * Converts file a Song object to something that can be later saved to the file system.
 * 
 * @author Pavel Foltyn
 */
public class SongSerializer implements Function<Song, Ensemble> {




    @Override
    public Ensemble apply(Song song) {
        //return new Ensemble(path);
        //return song.getEnsemble();
        Ensemble ensemble = songToEnsemble(song);
        return ensemble;
    }



    /**
     * Transforms a given song into file contents.
     * The method assumes that the song already contains an Ensemble object with a Path correctly set.
     * The Ensemble object just lacks contents (lines of the file). And this is what this method should help with.
     * 
     * @param song Song to transform.
     * @return Returns an object that represents file contents with song data.
     */
    private Ensemble songToEnsemble(Song song) {

        // Transform the song into generic XML.
        Songer songer = new Songer(song);
        songer.convertSongToXml();
        Document xmlDocument = songer.getXmlDocument();

        // Transform the generated XML into lines of the file.
        Xmler xmler = new Xmler(xmlDocument);
        xmler.synthetize();
        List<String> lines = xmler.getLines();

        // Access the file contents object stored in the song.
        Ensemble ensemble = song.getEnsemble();

        // Store the lines into the file.
        ensemble.setLines(lines);

        // Return the file contents.
        return ensemble;

    }



}
