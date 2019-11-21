package cz.jollysoft.songenricher.xmlpieces;



import cz.jollysoft.songenricher.transformers.xml.TextToken;



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

    /** The parent element of this text XML piece. */
    private Element parentElement;



    /**
     * Constructor.
     */
    public Text() {
        //plaintext = EMPTY;
        this(EMPTY);
    }



    /**
     * Constructor.
     * 
     * @param plaintext Text contents of this Text XML piece.
     */
    public Text(String plaintext) {
        //this.plaintext = plaintext;
        this(plaintext, null);
    }



    /**
     * Constructor.
     * 
     * @param plaintext Text contents of this Text XML piece.
     * @param parentElement Parent element of this text piece.
     */
    public Text(String plaintext, Element parentElement) {
        this.plaintext = plaintext;
        this.parentElement = parentElement;
    }



    /**
     * Constructor.
     * 
     * @param textToken Text token to construct this text piece out of.
     */
    public Text(TextToken textToken) {
        //this.plaintext = textToken.getValue();
        //this(textToken.getValue());
        this(textToken, null);
    }



    /**
     * Constructor.
     * 
     * @param textToken Text token to construct this text piece out of.
     * @param parentElement Parent element of this text piece.
     */
    public Text(TextToken textToken, Element parentElement) {
        //this.plaintext = textToken.getValue();
        //this.parentElement = parentElement;
        this(textToken.getValue(), parentElement);
    }



    public String getPlaintext() {
        return plaintext;
    }

    public void setPlaintext(String plaintext) {
        this.plaintext = plaintext;
    }

    public Element getParentElement() {
        return parentElement;
    }

    public void setParentElement(Element parentElement) {
        this.parentElement = parentElement;
    }



    @Override
    public String toString() {
        return "Text: " + plaintext;
    }



}
