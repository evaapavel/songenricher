package cz.jollysoft.songenricher.transformers.xml;



import java.util.Map;
import java.util.HashMap;



/**
 * Represents a processing token in an XML file.
 * 
 * @author Pavel Foltyn
 */
public class ProcessingToken extends Token {



    /** Name of this processing token. */
    private String name;

    /** Map of attributes and their values. */
    private Map<String, String> attributesAndValues;



    /**
     * Constructor.
     * 
     * @param value Value of this processing token.
     */
    public ProcessingToken(String value) {
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



}
