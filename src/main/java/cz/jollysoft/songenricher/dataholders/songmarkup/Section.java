package cz.jollysoft.songenricher.dataholders.songmarkup;



/**
 * Represents a section of the song lyrics. There are several types of song
 * sections:
 * <ul>
 * <li>Verse</li>
 * <li>Chorus</li>
 * <li>Bridge</li>
 * <li>Pre-Chorus</li>
 * <li>Tag</li>
 * </ul>
 * 
 * @author Pavel Foltyn
 */
public class Section {



    /** Name of this section (e.g. "V1", "V2", "C", "V1A", "B") */
    private String name;

    /** Type code of this section (V, C, B, P, or T). Mandatory. */
    private String sectionCode;

    /** Number of this section. Optional. */
    private Integer sectionNumber;

    /** Part letter of a long section (a long verse, for example). */
    private String sectionPart;

    /** Part of the lyrics belonging to this section. */
    private String lyricsText;



    /**
     * Constructor.
     * 
     * @param name Name of this section (e.g. V1, C, V2A, B, etc.).
     * @param lyricsText Part of the lyrics for this section.
     */
    public Section(String name, String lyricsText) {
        this.name = name;
        parseName();
        this.lyricsText = lyricsText;
    }



    public String getName() {
        return name;
    }

    //public void setName(String name) {
    //    this.name = name;
    //}

    public String getLyricsText() {
        return lyricsText;
    }

    public void setLyricsText(String lyricsText) {
        this.lyricsText = lyricsText;
    }



    /**
     * Gets a part of this section's name which defines a type of the section.
     * V means a verse.
     * C means a chorus.
     * B means a bridge.
     * P means a pre-chorus.
     * T means a tag.
     * 
     * @return Returns the type code of this section.
     */
    public String getSectionCode() {
        //return name.substring(0, 1);
        return sectionCode;
    }



    /**
     * Gets a part of this section's name telling the ordinal number of the section within sections of the same type.
     * This is typically used for verses. We've got V1, V2, etc.
     * 1 means V1 (verse 1), 2 means V2 (verse 2), etc.
     * 
     * @return Returns the ordinal number of this section (within the sections of the same type). Returns null if this section has NO ordinal number.
     */
    public Integer getSectionNumber() {
        // if ( ! (name.length() > 1) ) {
        //     return null;
        // }
        // String sectionNumberAsString = name.substring(1, 1);
        // int sectionNumber;
        // try {
        //     sectionNumber = Integer.parseInt(sectionNumberAsString);
        // } catch (NumberFormatException e) {
        //     throw new RuntimeException(String.format("The name of this section has an improper format: '%s'", name));
        // }
        // return sectionNumber;
        return sectionNumber;
    }



    /**
     * Certain sections may have letters in their name (e.g. V1A, V1B)
     * which denotes that the section (e.g. verse 1) is to be divided into several parts (part A and part B of the same verse 1)
     * (several slides during the show).
     * 
     * @return Returns the section's part letter (if any). Returns null if there's no section part letter within this section.
     */
    public String getSectionPart() {
        // if ( ! (name.length() > 2) ) {
        //     return null;
        // }
        // String sectionPart = name.substring(2, 1);
        // return sectionPart;
        return sectionPart;
    }



    /**
     * Parses the name of this section and stores its components in their respective fields (sectionCode, sectionNumber, sectionPart).
     */
    private void parseName() {

        // The section name must be at least one character long.
        // The section may be four characters long at the most (V16B).
        if ( ! ((name.length() >= 1) && (name.length() <= 4)) ) {
            throw new RuntimeException(String.format("This section name is not allowed here: %s", name));
        }

        // TODO: Finish this.
        // The first character of the name must be one of the following letters: V, C, B, P, or T.


    }



    /**
     * Creates a lyrics section based on the given line (which should be the section's beginning markup).
     * 
     * @param sectionBeginningLine Line describing the section.
     * @return Returns a new section based on the given section beginning line.
     */
    public static Section fromBeginningLine(String sectionBeginningLine) {

        // TODO: Finish this.
        return null;

    }



}
