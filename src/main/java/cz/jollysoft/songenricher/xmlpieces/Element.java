package cz.jollysoft.songenricher.xmlpieces;



import java.util.ArrayList;
import java.util.List;



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



}