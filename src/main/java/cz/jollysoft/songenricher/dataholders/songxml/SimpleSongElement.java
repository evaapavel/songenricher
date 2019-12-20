package cz.jollysoft.songenricher.dataholders.songxml;



import java.util.Optional;

import cz.jollysoft.songenricher.xmlpieces.Element;
import cz.jollysoft.songenricher.xmlpieces.Text;



/**
 * Represents a song element that has NO CHILDREN. On the other hand, this song
 * element MAY HAVE some inner TEXT.
 * 
 * @author Pavel Foltyn
 */
public abstract class SimpleSongElement extends SongElement {



    /** Inner text of this element. */
    private String innerText;



    /**
     * Constructor.
     * 
     * @param innerText Inner text of this element.
     * @param parentElement Parent element of this element.
     */
    public SimpleSongElement(String innerText, SongElement parentElement) {
        super(parentElement);
        this.innerText = innerText;
    }



    /**
     * Copy constructor.
     * 
     * @param elementToClone Element to copy.
     * @param newParentElement A song element to be used as the parent of the cloned object.
     */
    public SimpleSongElement(SimpleSongElement elementToClone, SongElement newParentElement) {
        super(elementToClone, newParentElement);
        this.innerText = elementToClone.innerText;
    }



    public String getInnerText() {
        return innerText;
    }

    public void setInnerText(String innerText) {
        this.innerText = innerText;
    }



    @Override
    public void buildFromXml(Element element) {

        // This kind of a song element should contain just a single text piece or nothing. No more text pieces, no elements.

        // Integrity check.
        if ( ! ((element.getSubelements().size() == 0) && (element.getSubpieces().size() <= 1)) ) {
            throw new RuntimeException(String.format("This simple song element cannot be built using the given XML: %s", element.toString()));
        }

        // Build the element.
        if (element.getSubpieces().size() > 0) {
            Text text = (Text) element.getSubpieces().get(0);
            innerText = text.getPlaintext();
        }

    }



    @Override
    public Element toXmlElement(Element parentXmlElement) {
        Element xmlElement = new Element(getName(), parentXmlElement);
        if (innerText != null) {
            Text xmlText = new Text(innerText, xmlElement);
            xmlElement.addText(xmlText);
        }
        return xmlElement;
    }



    @Override
    public String toString() {
        //return getName() + new Optional<String>(innerText);
        //return getName() + new<String> Optional(innerText);
        //return getName() + <String>new Optional(innerText);
        //return getName() + Optional<String>.ofNullable(innerText);
        //return getName() + Optional.ofNullable<String>(innerText);
        //return getName() + Optional<String>.ofNullable(innerText).orElse("");
        //return getName() + Optional.<String>ofNullable(innerText).orElse("");
        //return getName() + <String>Optional.ofNullable(innerText);
        //return getName() + Optional.ofNullable(innerText).map(x -> ": " + x).orElse("");
        //return getName() + Optional.ofNullable(innerText).map(x -> ": " + x).toString();
        //return getName() + Optional.ofNullable(innerText).map(x -> ": " + x).orElse("");
        return getName() + Optional.<String>ofNullable(innerText).map(x -> ": " + x).orElse("");
    }



}