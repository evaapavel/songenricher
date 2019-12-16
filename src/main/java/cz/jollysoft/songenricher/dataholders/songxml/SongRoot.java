package cz.jollysoft.songenricher.dataholders.songxml;



import static cz.jollysoft.songenricher.constants.AppConstants.NEWLINE_SEQUENCE;

import java.util.Map;
import java.util.HashMap;

import cz.jollysoft.songenricher.xmlpieces.Element;



/**
 * Represents the root element of a song that contains all song data.
 * 
 * @author Pavel Foltyn
 */
public class SongRoot extends CompositeSongElement implements Cloneable {



    /** Access to the subelements by their names. */
    private Map<String, SongElement> subelementsByName;



    /**
     * Constructor.
     */
    public SongRoot() {
        // The root element has no parent.
        super((SongElement) null);
        subelementsByName = new HashMap<>();
    }



    /**
     * Copy constructor.
     * 
     * @param elementToClone Element to copy.
     * @param newParentElement A song element to be used as the parent of the cloned object.
     */
    public SongRoot(SongRoot elementToClone, SongElement newParentElement) throws CloneNotSupportedException {
        super(elementToClone, newParentElement);
        if (elementToClone.subelementsByName != null) {
            this.subelementsByName = new HashMap<>();
            if (this.getSubelements() != null) {
                for (SongElement subelement : this.getSubelements()) {
                    this.subelementsByName.put(subelement.getName(), subelement);
                }
            }
        } else {
            this.subelementsByName = null;
        }
    }



    /**
     * Copy constructor.
     * 
     * @param songRoot SongRoot to copy.
     */
    public SongRoot(SongRoot songRoot) throws CloneNotSupportedException {
        // No parent.
        this(songRoot, (SongElement) null);
    }



    // //@SuppressWarnings("unchecked")
    // /**
    //  * Copy constructor.
    //  * 
    //  * @param songRoot SongRoot to copy.
    //  */
    // public SongRoot(SongRoot songRoot) throws CloneNotSupportedException {
    //     //super(songRoot);
    //     super(songRoot, (SongElement) null);
    //     //if (songRoot.subelementsByName != null) {
    //     //    this.subelementsByName = (Map<String, SongElement>) ((HashMap<String, SongElement>) songRoot.subelementsByName).clone();
    //     //} else {
    //     //    this.subelementsByName = null;
    //     //}
    //     if (songRoot.subelementsByName != null) {
    //         this.subelementsByName = new HashMap<>();
    //         //for (String subelementName : songRoot.subelementsByName.keySet()) {
    //         //    SongElement subelement = songRoot.subelementsByName.get(subelementName);
    //         //    //SongElement subelementCloned = subelement.clone(this);
    //         //    SongElement subelementCloned = null;
    //         //    this.subelementsByName.put(subelementName, subelementCloned);
    //         //}
    //         for (SongElement subelement : this.getSubelements()) {
    //             this.subelementsByName.put(subelement.getName(), subelement);
    //         }
    //     } else {
    //         this.subelementsByName = null;
    //     }
    // }



    public Title getTitle() { return (Title) subelementsByName.get("title"); }
    public Author getAuthor() { return (Author) subelementsByName.get("author"); }
    public Copyright getCopyright() { return (Copyright) subelementsByName.get("copyright"); }
    public HymnNumber getHymnNumber() { return (HymnNumber) subelementsByName.get("hymn_number"); }
    public Presentation getPresentation() { return (Presentation) subelementsByName.get("presentation"); }
    public Ccli getCcli() { return (Ccli) subelementsByName.get("ccli"); }
    public Capo getCapo() { return (Capo) subelementsByName.get("capo"); }
    public Key getKey() { return (Key) subelementsByName.get("key"); }
    public Aka getAka() { return (Aka) subelementsByName.get("aka"); }
    public KeyLine getKeyLine() { return (KeyLine) subelementsByName.get("key_line"); }
    public User1 getUser1() { return (User1) subelementsByName.get("user1"); }
    public User2 getUser2() { return (User2) subelementsByName.get("user2"); }
    public User3 getUser3() { return (User3) subelementsByName.get("user3"); }
    public Theme getTheme() { return (Theme) subelementsByName.get("theme"); }
    public Tempo getTempo() { return (Tempo) subelementsByName.get("tempo"); }
    public TimeSig getTimeSig() { return (TimeSig) subelementsByName.get("time_sig"); }
    public Lyrics getLyrics() { return (Lyrics) subelementsByName.get("lyrics"); }



    @Override
    public String getName() {
        return "song";
    }



    @Override
    public SongElement clone(SongElement newParentElement) throws CloneNotSupportedException {
        // No parent element.
        SongRoot songRoot = new SongRoot(this, (SongElement) null);
        return songRoot;
    }



    @Override
    public Object clone() throws CloneNotSupportedException {
        SongRoot songRoot = new SongRoot(this);
        return songRoot;
    }



    @Override
    public void buildFromXml(Element element) {

        // This kind of a song element should contain other song elements (and nothing else, no text pieces, for example).

        // Integrity check (just subelements, no text subpieces).
        if ( ! (element.getSubpieces().size() == element.getSubelements().size()) ) {
            throw new RuntimeException(String.format("This song root cannot be built out of the given element: %s", element.toString()));
        }

        // Create song subelements.
        for (Element childElement : element.getSubelements()) {

            // Depending on the name of the subelement, create a corresponding song element.
            SimpleSongElement child;
            switch (childElement.getName()) {
                case "title":
                    child = new Title(this);
                    break;
                case "author":
                    child = new Author(this);
                    break;
                case "copyright":
                    child = new Copyright(this);
                    break;
                case "hymn_number":
                    child = new HymnNumber(this);
                    break;
                case "presentation":
                    child = new Presentation(this);
                    break;
                case "ccli":
                    child = new Ccli(this);
                    break;
                case "capo":
                    child = new Capo(this);
                    break;
                case "key":
                    child = new Key(this);
                    break;
                case "aka":
                    child = new Aka(this);
                    break;
                case "key_line":
                    child = new KeyLine(this);
                    break;
                case "user1":
                    child = new User1(this);
                    break;
                case "user2":
                    child = new User2(this);
                    break;
                case "user3":
                    child = new User3(this);
                    break;
                case "theme":
                    child = new Theme(this);
                    break;
                case "tempo":
                    child = new Tempo(this);
                    break;
                case "time_sig":
                    child = new TimeSig(this);
                    break;
                case "lyrics":
                    child = new Lyrics(this);
                    break;
                default:
                    throw new RuntimeException(String.format("This element is not supported here: %s", childElement.toString()));
            }

            // Build the child song element.
            child.buildFromXml(childElement);

            // Integrity check:
            // There should not be a child with the same name yet.
            if ( ! ( ! subelementsByName.containsKey(child.getName()) ) ) {
                throw new RuntimeException(String.format("An element with the same name (%s) has already been processed. Here is the list of processed elements: %s", child.getName(), subelementsByName.toString()));
            }

            // Add the child element:
            // Both to the list of subelements.
            // And to the map of subelements by their names.
            getSubelements().add(child);
            subelementsByName.put(child.getName(), child);

        }

        // Once we've processed all the song elements, let's make sure there are at least these:
        // title
        if ( ! (subelementsByName.containsKey("title")) ) {
            throw new RuntimeException(String.format("A song is supposed to have a title. The list of song elements does not contain a 'title' element: %s", subelementsByName.toString()));
        }
        // lyrics
        if ( ! (subelementsByName.containsKey("lyrics")) ) {
            throw new RuntimeException(String.format("A song is supposed to have lyrics. The list of song elements does not contain a 'lyrics' element: %s", subelementsByName.toString()));
        }

    }



    @Override
    public String toString() {
        return
            getName()
            + NEWLINE_SEQUENCE
            + getSubelements().stream()
                //.map(Object::toString)
                .map(SongElement::toString)
                .reduce("", (acc, s) -> acc.concat(s).concat(NEWLINE_SEQUENCE))
        ;
    }



    /**
     * Converts the song data to general purpose XML.
     * 
     * @return Returns an XML element with this song's data.
     */
    public Element toXmlElement() {
        return toXmlElement(null);
    }



}