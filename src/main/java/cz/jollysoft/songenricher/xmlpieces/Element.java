package cz.jollysoft.songenricher.xmlpieces;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cz.jollysoft.songenricher.transformers.xml.ElementToken;



/**
 * Represents an element of an XML document.
 * An element may have zero, one, or more "subelements".
 * Also, it may have zero, one, or more "subpieces".
 * The set of subelements is a subset of the set of subpieces.
 * In addition, an element may have zero, one, or more attributes.
 * 
 * @author Pavel Foltyn
 */
public class Element extends Piece {



    /** Name of this element. */
    private String name;

    /** Subelements of this element. */
    private List<Element> subelements;

    /** Subpieces of this element. */
    private List<Piece> subpieces;

    /** Attributes of this element. */
    private List<Attribute> attributes;



    /**
     * Constructor.
     * 
     * @param name Name of this element.
     */
    public Element(String name) {
        this.name = name;
        subelements = new ArrayList<>();
        subpieces = new ArrayList<>();
        attributes = new ArrayList<>();
    }



    /**
     * Constructor.
     * Constructs an XML element from an OPENING version of an element token.
     * 
     * @param elementToken Element token to construct this element out of.
     */
    public Element(ElementToken elementToken) {
        this.name = elementToken.getName();
        subelements = new ArrayList<>();
        subpieces = new ArrayList<>();
        attributes = new ArrayList<>();
        for (Map.Entry<String, String> entry : elementToken.getAttributesAndValues().entrySet()) {
            Attribute attribute = new Attribute(entry.getKey(), entry.getValue());
            this.attributes.add(attribute);
        }
    }



    public String getName() {
        return name;
    }

    public List<Element> getSubelements() {
        return subelements;
    }

    public List<Piece> getSubpieces() {
        return subpieces;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }



    /**
     * Adds a given text piece to this XML element as its child.
     * 
     * @param childText Text piece to add to this.
     */
    public void addText(Text childText) {
        this.subpieces.add(childText);
    }



    /**
     * Adds a given element piece to this XML element as its child.
     * 
     * @param childElement Element piece to add to this.
     */
    public void addElement(Element childElement) {
        this.subelements.add(childElement);
        this.subpieces.add(childElement);
    }



    @Override
    public String toString() {
        return "Element: " + name + attributes + "(" + subpieces.size() + " subpieces)";
    }



}