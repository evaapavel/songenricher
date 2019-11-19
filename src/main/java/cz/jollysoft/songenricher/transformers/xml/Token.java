package cz.jollysoft.songenricher.transformers.xml;



/**
 * Represents a token in a text to be parsed for XML.
 * 
 * @author Pavel Foltyn
 */
public class Token {



    /** Text value of this token. */
    private String value;



    /**
     * Constructor.
     * 
     * @param value Text value of this token.
     */
    public Token(String value) {
        this.value = value;
    }



    public String getValue() {
        return value;
    }



    @Override
    public String toString() {
        return value;
    }



}