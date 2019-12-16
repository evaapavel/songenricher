package cz.jollysoft.songenricher.dataholders.songxml;



import java.util.ArrayList;
import java.util.List;

import cz.jollysoft.songenricher.xmlpieces.Element;



/**
 * Represents a song element that can have subelements.
 * Such an element (a composite element) cannot have inner text.
 * 
 * @author Pavel Foltyn
 */
public abstract class CompositeSongElement extends SongElement {



    /** Subelements of this element. */
    private List<SongElement> subelements;



    /**
     * Constructor.
     * 
     * @param parentElement Parent element of this element.
     */
    public CompositeSongElement(SongElement parentElement) {
        super(parentElement);
        subelements = new ArrayList<>();
    }



    /**
     * Copy constructor.
     * 
     * @param elementToClone Element to copy.
     * @param newParentElement A song element to be used as the parent of the cloned object.
     */
    public CompositeSongElement(CompositeSongElement elementToClone, SongElement newParentElement) {
        super(elementToClone, newParentElement);
        if (elementToClone.subelements != null) {
            this.subelements = new ArrayList<>();
            for (SongElement subelement : elementToClone.subelements) {
                this.subelements.add(subelement.clone(this));
            }
        } else {
            this.subelements = null;
        }
    }



    public List<SongElement> getSubelements() {
        return subelements;
    }

    //public void setSubelements(List<SongElement> subelements) {
    //    this.subelements = subelements;
    //}



    @Override
    public Element toXmlElement(Element parentXmlElement) {
        Element xmlElement = new Element(getName(), parentXmlElement);
        subelements.stream()
            .map(se -> se.toXmlElement(xmlElement))
            .forEach(xmlElement::addElement);
        ;
        return xmlElement;
    }



}
