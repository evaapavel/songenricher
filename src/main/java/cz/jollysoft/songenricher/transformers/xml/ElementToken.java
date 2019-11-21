package cz.jollysoft.songenricher.transformers.xml;



import java.util.Map;
import java.util.HashMap;



/**
 * Represents an element token in an XML file.
 * 
 * @author Pavel Foltyn
 */
public class ElementToken extends Token {



    /** Name of this processing token. */
    private String name;

    /** Map of attributes and their values. */
    private Map<String, String> attributesAndValues;

    /** True :-: this is an opening tag of an XML element, false :-: this is a closing tag of an XML element. */
    private boolean openingTag;



    /**
     * Constructor.
     * 
     * @param value Value of this processing token.
     */
    public ElementToken(String value) {
        super(value);
        attributesAndValues = new HashMap<>();
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getAttributesAndValues() {
        return attributesAndValues;
    }

    public void setAttributesAndValues(Map<String, String> attributesAndValues) {
        this.attributesAndValues = attributesAndValues;
    }

    public boolean isOpeningTag() {
        return openingTag;
    }

    public void setOpeningTag(boolean openingTag) {
        this.openingTag = openingTag;
    }

    public boolean isClosingTag() {
        return ( ! openingTag );
    }

    public void setClosingTag(boolean closingTag) {
        this.openingTag = ( ! closingTag );
    }



}
