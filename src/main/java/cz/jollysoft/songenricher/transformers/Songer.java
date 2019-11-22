package cz.jollysoft.songenricher.transformers;



import cz.jollysoft.songenricher.dataholders.Song;
import cz.jollysoft.songenricher.xmlpieces.Document;



/**
 * Helps to transform generic XML to a song and vice versa.
 * 
 * @author Pavel Foltyn
 */
public class Songer {



    /** Generic XML document structure. */
    private Document xmlDocument;

    /** Open song structure. */
    private Song song;



    /**
     * Constructor.
     * 
     * @param xmlDocument XML document to convert to an open song.
     */
    public Songer(Document xmlDocument) {
        this.xmlDocument = xmlDocument;
    }



    /**
     * Constructor.
     * 
     * @param song Open song to convert to generic XML.
     */
    public Songer(Song song) {
        this.song = song;
    }



    public Document getXmlDocument() {
        return xmlDocument;
    }



    public Song getSong() {
        return song;
    }



    /**
     * Converts the given XML document to open song structure.
     */
    public void convertXmlToSong() {

        // Convert the given XML to a song.
        //song = new Song();
        song = Song.fromXml(xmlDocument);

    }



    /**
     * Converts the given open song to generic XML structure.
     */
    public void convertSongToXml() {
    }



}
