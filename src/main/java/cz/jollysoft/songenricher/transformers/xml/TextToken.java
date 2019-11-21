package cz.jollysoft.songenricher.transformers.xml;



/**
 * Represents a token with plaintext (no markup).
 * 
 * @author Pavel Foltyn
 */
public class TextToken extends Token {



    /**
     * Constructor.
     * 
     * @param value Value of this text token.
     */
    public TextToken(String value) {
        super(value);
    }



}