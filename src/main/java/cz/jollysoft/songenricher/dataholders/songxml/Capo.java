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
        this(null, parentElement);
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
