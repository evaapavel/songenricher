package cz.jollysoft.songenricher.constants;



/**
 * Defines application wide constants.
 * 
 * @author Pavel Foltyn
 */
public final class AppConstants {



    /** Platform dependent newline character(s). CRLF for Windows, LF for Linux, CR for Mac. */
    public static final String NEW_LINE_SEQUENCE = ( ((System.getProperty("line.separator") != null) && (System.getProperty("line.separator").length() > 0)) ? (System.getProperty("line.separator")) : ("\n") );

    /** Regular expression describing a line that represents the beginning of a lyrics section. */
    public static final String SECTION_BEGINNING_REGEX = "\\[((V(([1-9][0-9]*(([abcdABCD])|()))|()))|(C(((([1-4])|())(([abcdABCD])|()))|()))|(B)|(P)|(T))\\]";



    /** Disallow instantiation of this class. */
    private AppConstants() {}



}
