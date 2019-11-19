package cz.jollysoft.songenricher.xmlpieces;

/**
 * Represents a plain text part of an XML document. The text should not contain
 * any XML markup.
 * 
 * @author Pavel Foltyn
 */
public class Text extends Piece {



    /** Empty text contents for text pieces. */
    private static final String EMPTY = "";



    /** Text contents of this Text XML piece. */
    private String plaintext;



    /**
     * Constructor.
     */
    public Text() {
        plaintext = EMPTY;
    }



    /**
     * Constructor.
     * 
     * @param plaintext Text contents of this Text XML piece.
     */
    public Text(String plaintext) {
        this.plaintext = plaintext;
    }



    public String getPlaintext() {
        return plaintext;
    }

    public void setPlaintext(String plaintext) {
        this.plaintext = plaintext;
    }



}