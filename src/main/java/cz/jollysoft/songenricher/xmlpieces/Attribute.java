package cz.jollysoft.songenricher.xmlpieces;



/**
 * Represents an attribute of an XML element.
 * 
 * @author Pavel Foltyn
 */
public class Attribute {



    /** Name of this attribute. */
    private String name;

    /** Value of this attribute. */
    private String value;



    /**
     * Constructor.
     * 
     * @param name Name for the new attribute.
     * @param value The attribute's value.
     */
    public Attribute(String name, String value) {
        this.name = name;
        this.value = value;
    }



    public String getName() {
        return name;
    }

    //public void setName(String name) {
    //    this.name = name;
    //}

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }



}
