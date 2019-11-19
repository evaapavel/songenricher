package cz.jollysoft.songenricher.xmlpieces;

/**
 * Represents the whole of an XML document.
 * 
 * @author Pavel Foltyn
 */
public class Document {



    /** XML version that this XML document is written according to. */
    private String version;

    /** Encoding used to encode this XML document when serialized to a disk file. */
    private String encoding;

    /** Main (topmost) element of this XML document. */
    private Element documentElement;



    /**
     * Constructor.
     */
    public Document() {
        this("1.0", "UTF-8", "doc");
    }



    /**
     * Constructor.
     * 
     * @param version             XML version to be used for this XML document.
     * @param encoding            Encoding to use when saving data to disk or
     *                            loading data from disk.
     * @param documentElementName Name of the main document element.
     */
    public Document(String version, String encoding, String documentElementName) {
        this.version = version;
        this.encoding = encoding;
        setDocumentElement(new Element(documentElementName));
    }



    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public Element getDocumentElement() {
        return documentElement;
    }

    public void setDocumentElement(Element documentElement) {
        this.documentElement = documentElement;
    }



}
