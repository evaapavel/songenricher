package cz.jollysoft.songenricher.xmlpieces;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cz.jollysoft.songenricher.transformers.xml.ElementToken;
import cz.jollysoft.songenricher.transformers.xml.TextToken;



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

    /** Parent element of this element. */
    private Element parentElement;



    /**
     * Constructor.
     * 
     * @param name Name of this element.
     */
    public Element(String name) {
        //this.name = name;
        //subelements = new ArrayList<>();
        //subpieces = new ArrayList<>();
        //attributes = new ArrayList<>();
        this(name, null);
    }



    /**
     * Constructor.
     * 
     * @param name Name of this element.
     * @param parentElement Parent element of this element.
     */
    public Element(String name, Element parentElement) {
        this.name = name;
        this.parentElement = parentElement;
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
        //this.name = elementToken.getName();
        //subelements = new ArrayList<>();
        //subpieces = new ArrayList<>();
        //attributes = new ArrayList<>();
        //for (Map.Entry<String, String> entry : elementToken.getAttributesAndValues().entrySet()) {
        //    Attribute attribute = new Attribute(entry.getKey(), entry.getValue());
        //    this.attributes.add(attribute);
        //}
        this(elementToken, null);
    }



    /**
     * Constructor.
     * Constructs an XML element from an OPENING version of an element token.
     * 
     * @param elementToken Element token to construct this element out of.
     * @param parentElement Parent element of this element.
     */
    public Element(ElementToken elementToken, Element parentElement) {
        // Integrity check.
        if ( ! (elementToken.isOpeningTag()) ) {
            throw new RuntimeException(String.format("An opening tag is expected to construct an element. But the given token is: ", elementToken.toString()));
        }
        // Initialize the instance.
        this.name = elementToken.getName();
        this.parentElement = parentElement;
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

    public Element getParentElement() {
        return parentElement;
    }

    public void setParentElement(Element parentElement) {
        this.parentElement = parentElement;
    }



    /**
     * Adds a given text piece to this XML element as its child.
     * 
     * @param childText Text piece to add to this.
     */
    public void addText(Text childText) {
        this.subpieces.add(childText);
        childText.setParentElement(this);
    }



    /**
     * Creates a text XML piece from a given text token.
     * Adds the text piece to this XML element as its child.
     * 
     * @param childTextToken Text token to create a text piece out of.
     * @return Returns the text piece just created.
     */
    public Text addTextFromTextToken(TextToken childTextToken) {
        //this.subpieces.add(childText);
        Text childText = new Text(childTextToken);
        addText(childText);
        return childText;
    }



    /**
     * Adds a given element piece to this XML element as its child.
     * 
     * @param childElement Element piece to add to this.
     */
    public void addElement(Element childElement) {
        this.subelements.add(childElement);
        this.subpieces.add(childElement);
        childElement.setParentElement(this);
    }



    /**
     * Creates an Element XML piece out of a given element token.
     * Adds the element piece to this XML element as its child.
     * 
     * @param childElementToken Element token to create an element piece out of.
     * @return Returns the element piece just created.
     */
    public Element addElementFromElementToken(ElementToken childElementToken) {
        //this.subelements.add(childElement);
        //this.subpieces.add(childElement);
        //childElement.setParentElement(this);
        Element childElement = new Element(childElementToken);
        addElement(childElement);
        return childElement;
    }



    @Override
    public String toString() {
        return "Element: " + name + attributes + "(" + subpieces.size() + " subpieces)";
    }



}