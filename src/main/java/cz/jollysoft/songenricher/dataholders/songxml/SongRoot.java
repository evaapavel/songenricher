package cz.jollysoft.songenricher.dataholders.songxml;



import static cz.jollysoft.songenricher.constants.AppConstants.NEW_LINE_SEQUENCE;

import java.util.Map;
import java.util.HashMap;

import cz.jollysoft.songenricher.xmlpieces.Element;



/**
 * Represents the root element of a song that contains all song data.
 * 
 * @author Pavel Foltyn
 */
public class SongRoot extends CompositeSongElement {



    /** Access to the subelements by their names. */
    private Map<String, SongElement> subelementsByName;



    /**
     * Constructor.
     */
    public SongRoot() {
        // The root element has no parent.
        super(null);
        subelementsByName = new HashMap<>();
    }



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

            // Add the child element:
            // Both to the list of subelements.
            // And to the map of subelements by their names.
            getSubelements().add(child);
            subelementsByName.put(child.getName(), child);

        }

    }



    @Override
    public String toString() {
        return
            getName()
            + NEW_LINE_SEQUENCE
            + getSubelements().stream()
                //.map(Object::toString)
                .map(SongElement::toString)
                .reduce("", (acc, s) -> acc.concat(s).concat(NEW_LINE_SEQUENCE))
        ;
    }



}