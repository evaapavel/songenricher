package cz.jollysoft.songenricher.dataholders;



import cz.jollysoft.songenricher.dataholders.songxml.SongRoot;
import cz.jollysoft.songenricher.xmlpieces.Document;
import cz.jollysoft.songenricher.xmlpieces.Element;



/**
 * Represents a song to be adjusted.
 * 
 * @author Pavel Foltyn
 */
public class Song {



    /** File contents of this song. */
    private Ensemble ensemble;

    /** XML structure of this song. */
    private SongRoot songRoot;



    /**
     * Constructor.
     */
    public Song() {
    }



    // /**
    //  * Constructor.
    //  * 
    //  * @param ensemble File contents of this song.
    //  */
    // public Song(Ensemble ensemble) {
    //     this.ensemble = ensemble;
    // }



    public Ensemble getEnsemble() {
        return ensemble;
    }

    public void setEnsemble(Ensemble ensemble) {
        this.ensemble = ensemble;
    }

    public SongRoot getSongRoot() {
        return songRoot;
    }

    public void setSongRoot(SongRoot songRoot) {
        this.songRoot = songRoot;
    }



    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }



    /**
     * Converts this song (which is represented by dedicated XML) to general-purpose XML.
     * 
     * @return Returns a Document object which encapsulates XML elements, attributes, etc.
     */
    public Document toXmlDocument() {
        Document xmlDocument = new Document("1.0", "UTF-8", "song");
        Element documentElement = songRoot.toXmlElement();
        xmlDocument.setDocumentElement(documentElement);
        return xmlDocument;
    }



    /**
     * Creates an instance of this class out of given XML.
     * 
     * @param xmlDocument XML to use when initializing the new instance.
     * @return Returns the new instance of this class with data from the given XML.
     */
    public static Song fromXml(Document xmlDocument) {
        Song song = new Song();
        //song.songRoot = SongRoot.fromXml(xmlDocument.getDocumentElement());
        song.songRoot = new SongRoot();
        song.songRoot.buildFromXml(xmlDocument.getDocumentElement());
        return song;
    }



}
