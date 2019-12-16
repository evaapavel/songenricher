package cz.jollysoft.songenricher.dataholders.songxml;



import java.util.Optional;

import cz.jollysoft.songenricher.xmlpieces.Element;



/**
 * Represents the capo of a song.
 * 
 * @author Pavel Foltyn
 */
public class Capo extends SimpleSongElement {



    /** True :-: to be printed, false :-: to not be printed. */
    private boolean print;



    /**
     * Constructor.
     * 
     * @param parentElement Parent element of this element.
     */
    public Capo(SongElement parentElement) {
        this((String) null, parentElement);
    }



    /**
     * Constructor.
     * 
     * @param innerText     Inner text of this element.
     * @param parentElement Parent element of this element.
     */
    public Capo(String innerText, SongElement parentElement) {
        super(innerText, parentElement);
    }



    /**
     * Copy constructor.
     * 
     * @param elementToClone Element to copy.
     * @param newParentElement A song element to be used as the parent of the cloned object.
     */
    public Capo(Capo elementToClone, SongElement newParentElement) {
        super(elementToClone, newParentElement);
        this.print = elementToClone.print;
    }



    public boolean isPrint() {
        return print;
    }

    public void setPrint(boolean print) {
        this.print = print;
    }



    @Override
    public String getName() {
        return "capo";
    }



    @Override
    public SongElement clone(SongElement newParentElement) {
        Capo songElement = new Capo(this, newParentElement);
        return songElement;
    }



    @Override
    public void buildFromXml(Element element) {

        // Call the super implementation first.
        super.buildFromXml(element);

        // Make a local action.

        // Integrity check first.
        //if ( ! ((element.getAttributes().size() == 1) && (element.getAttributes().get(0).getName() == "print")) ) {
        if ( ! ((element.getAttributes().size() == 1) && ("print".equals(element.getAttributes().get(0).getName()))) ) {
            throw new RuntimeException(String.format("The given element is not suitable for a 'capo' song element.", element.toString()));
        }

        // Extract the "print" attribute.
        boolean print = Boolean.parseBoolean(element.getAttributes().get(0).getValue());
        this.print = print;

    }



    @Override
    public String toString() {
        //return getName() + Optional.<String>ofNullable(innerText).map(x -> ": " + x).orElse("");
        //return getName() + Optional.<String>ofNullable(getInnerText()).map(x -> ": " + x).orElse("");
        return
            getName()
            + " (print=" + print + ")"
            + Optional.<String>ofNullable(getInnerText()).map(x -> ": " + x).orElse("")
        ;
    }



}
