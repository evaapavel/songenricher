package cz.jollysoft.songenricher.processors;



import java.util.function.Function;

import cz.jollysoft.songenricher.dataholders.Ensemble;
import cz.jollysoft.songenricher.dataholders.Song;
import cz.jollysoft.songenricher.transformers.Lyricser;
import cz.jollysoft.songenricher.transformers.Songer;
import cz.jollysoft.songenricher.transformers.Xmler;
import cz.jollysoft.songenricher.xmlpieces.Document;



/**
 * Converts file contents to a Song.
 * 
 * @author Pavel Foltyn
 */
public class SongParser implements Function<Ensemble, Song> {




    @Override
    public Song apply(Ensemble ensemble) {
        //return new Song();
        //return new Song(ensemble);
        // ***
        //Song song = new Song(ensemble);
        //parseSong(song);
        //return song;
        // ***
        Song song = ensembleToSong(ensemble);
        return song;
    }



    // /**
    //  * Parses a given song
    //  * 
    //  * @param song
    //  */
    // private void parseSong(Song song) {
    // }



    /**
     * Transforms given file contents into a song.
     * 
     * @param ensemble Contents of the song file.
     * @return Returns a song object with XML structure parsed.
     */
    private Song ensembleToSong(Ensemble ensemble) {

        // Parse the file contents and get its generic XML structure.
        Xmler xmler = new Xmler(ensemble.getLines());
        xmler.parse();
        Document xmlDocument = xmler.getXmlDocument();

        // Transform the generic XML into a song.
        Songer songer = new Songer(xmlDocument);
        songer.convertXmlToSong();
        Song song = songer.getSong();

        // Store the reference to the associated file into the song.
        song.setEnsemble(ensemble);

        // Parse the lyrics into song sections.
        Lyricser lyricser = new Lyricser(song);
        lyricser.parseLyrics();
        song = lyricser.getSong();

        // Return the result.
        return song;

    }



}
