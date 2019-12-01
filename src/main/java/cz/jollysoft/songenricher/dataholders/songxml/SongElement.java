package cz.jollysoft.songenricher.dataholders.songxml;

import cz.jollysoft.songenricher.xmlpieces.Element;

/**
 * Represents an element of a song data structure.
 * 
 * @author Pavel Foltyn
 */
public abstract class SongElement {



    /** Parent element of this element. */
    private SongElement parentElement;



    /**
     * Constructor.
     * 
     * @param parentElement Parent element of this element.
     */
    public SongElement(SongElement parentElement) {
        this.parentElement = parentElement;
    }



    public SongElement getParentElement() {
        return parentElement;
    }

    public void setParentElement(SongElement parentElement) {
        this.parentElement = parentElement;
    }



    /**
     * Gets the name of this element.
     * 
     * @return Returns the name of this element.
     */
    public abstract String getName();



    /**
     * Builds the song structure using a given XML element.
     * 
     * @param element XML element to take data for this song element from.
     */
    public abstract void buildFromXml(Element element);



    /**
     * Converts this song element back to XML.
     * 
     * @param parentXmlElement Parent XML element of the element to return.
     * @return Returns an XML element with data from this song element.
     */
    public abstract Element toXmlElement(Element parentXmlElement);



    @Override
    public String toString() {
        return getName();
    }



}