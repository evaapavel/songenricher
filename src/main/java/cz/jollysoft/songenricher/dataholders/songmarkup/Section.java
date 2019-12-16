package cz.jollysoft.songenricher.dataholders.songmarkup;


import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

import static cz.jollysoft.songenricher.util.AppUtils.matchesPattern;
import static cz.jollysoft.songenricher.constants.AppConstants.SECTION_BEGINNING_REGEX;
import static cz.jollysoft.songenricher.constants.AppConstants.NEWLINE_SEQUENCE;;



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
public abstract class Section implements Cloneable {



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
        //this.name = name;
        // Convert the section name to uppercase.
        this.name = name.toUpperCase();
        parseName();
        this.lyricsText = lyricsText;
    }



    /**
     * Copy constructor.
     * 
     * @param section Section to copy.
     */
    public Section(Section section) {
        this.name = section.name;
        this.sectionCode = section.sectionCode;
        this.sectionNumber = section.sectionNumber;
        this.sectionPart = section.sectionPart;
        this.lyricsText = section.lyricsText;
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



    @Override
    public abstract Object clone() throws CloneNotSupportedException;



    /**
     * Parses the name of this section and stores its components in their respective fields (sectionCode, sectionNumber, sectionPart).
     */
    private void parseName() {

        // Declare variables for section name components.
        char c1;
        char c2;
        char c3;
        char c4;

        //// For the sake of simplicity, let's convert the song section name to upper case.
        // For the sake of simplicity, assume the song section name has been converted to upper case.

        // The section name must be at least one character long.
        // The section may be four characters long at the most (V16B).
        if ( ! ((name.length() >= 1) && (name.length() <= 4)) ) {
            throw new RuntimeException(String.format("This section name is not allowed here: %s", name));
        }

        // The first character of the name must be one of the following letters: V, C, B, P, or T.
        c1 = name.charAt(0);
        if ( ! ((c1 == 'V') || (c1 == 'C') || (c1 == 'B') || (c1 == 'P') || (c1 == 'T')) ) {
            throw new RuntimeException(String.format("The section code is supposed to be one of the following: V for verse, C for chorus, B for bridge, P for pre-chorus, or T for tag. This section type code is not supported here: '%c'", c1));
        }

        // Store the section type code.
        //sectionCode = Character.valueOf(c1).toString();
        sectionCode = Character.toString(c1);

        // Depending on the number of characters in the section name, proceed with the syntax analysis.
        switch (name.length()) {

            case 1:
                // The song section name has just 1 character.

                // Just the section type code and nothing else.
                // This is fine!
                break;

            case 2:
                // The song section name has 2 characters.

                // The second character may be either a section number (e.g. V3) or a section part (e.g. CA).
                c2 = name.charAt(1);
                if ( (c2 >= '1') && (c2 <= '9') ) {

                    // We don't allow verse #0 (V0).

                    // Section numbers are allowed for verses and choruses only.
                    if ( ! ((c1 == 'V') || (c1 == 'C')) ) {
                        throw new RuntimeException(String.format("This section name is not supported here: %s", name));
                    }

                    // Store the section number.
                    //sectionNumber = Integer.valueOf(Character.toString(c2));
                    sectionNumber = ((int) c2) - ((int) '0');

                } else if ( (c2 >= 'A') && (c2 <= 'D') ) {

                    // We allow 4 section parts at the most (A, B, C, or D).

                    // Section parts without section numbers are allowed for choruses only.
                    if ( ! (c1 == 'C') ) {
                        throw new RuntimeException(String.format("This section name is not supported here: %s", name));
                    }

                    // Store the section part.
                    sectionPart = Character.toString(c2);

                } else {

                    // This is not an option.
                    throw new RuntimeException(String.format("This section name is not supported here: %s", name));

                }

                break;

            case 3:
                // The song section name has 3 characters.

                // The second and third characters may be either a section number (e.g. V12) or a section number and a section part (e.g. V1B).
                c2 = name.charAt(1);
                c3 = name.charAt(2);
                if ( (c2 >= '1') && (c2 <= '9') && (c3 >= '0') && (c3 <= '9') ) {

                    // Section numbers are something like this: 1, 2, 3, ..., 9, 10, 11, ..., 19, 20, ...
                    // But not like this: 01, 02, ...
                    // Here, a section number must be a two-digits' number: 10, 11, 12, 13, ...

                    // Two-digits' section numbers are allowed for verses only.
                    if ( ! (c1 == 'V') ) {
                        throw new RuntimeException(String.format("This section name is not supported here: %s", name));
                    }

                    // Store the section number.
                    //sectionNumber = (((int) c2) - ((int) '0')) * 10 + ((int) c3) - ((int) '0');
                    //sectionNumber = Integer.valueOf("" + c2 + c3);
                    sectionNumber = Integer.valueOf(Character.toString(c2) + Character.toString(c3));

                } else if ( (c2 >= '1') && (c2 <= '9') && (c3 >= 'A') && (c3 <= 'D') ) {

                    // We don't allow verse #0 (V0).
                    // We allow 4 section parts at the most (A, B, C, or D).

                    // Section numbers with section parts are allowed for verses and choruses only.
                    if ( ! ((c1 == 'V') || (c1 == 'C')) ) {
                        throw new RuntimeException(String.format("This section name is not supported here: %s", name));
                    }

                    // Store the section number and the section part.
                    sectionNumber = c2 - '0';
                    sectionPart = Character.toString(c3);

                } else {

                    // This is not an option.
                    throw new RuntimeException(String.format("This section name is not supported here: %s", name));

                }

                break;

            case 4:
                // The song section name has 4 characters.

                // In this case, there's ONE option ONLY.
                // The second and third characters MUST be a section number and the fourth character MUST be a section part.
                // Example: V12A
                c2 = name.charAt(1);
                c3 = name.charAt(2);
                c4 = name.charAt(3);

                // Integrity check.
                // Here, a section number must be a two-digits' number: 10, 11, 12, 13, ...
                // We allow 4 section parts at the most (A, B, C, or D).
                if ( ! ((c2 >= '1') && (c2 <= '9') && (c3 >= '0') && (c3 <= '9') && (c4 >= 'A') && (c4 <= 'D')) ) {
                    throw new RuntimeException(String.format("This section name is not supported here: %s", name));
                }

                // Two-digits' section numbers are allowed for verses only.
                if ( ! (c1 == 'V') ) {
                    throw new RuntimeException(String.format("This section name is not supported here: %s", name));
                }

                // Store the section number and the section part.
                sectionNumber = (c2 - '0') * 10 + (c3 - '0');
                sectionPart = Character.toString(c4);

                break;

            default:
                throw new RuntimeException(String.format("This song section name is too long and is not supported here: '%s'", name));

        }

    }



    /**
     * Converts this section back to a part of the lyrics string.
     * 
     * @return Returns this section converted into a partial lyrics string.
     */
    public String toLyrics() {

        //return toString();

        // Prepare lyrics text. Add indentation.
        String[] lyricsTextLines = lyricsText.split("((\n)|(\r\n)|(\r))");
        List<String> linesIndented = Stream.of(lyricsTextLines)
            .map(line -> " " + line.trim())
            .collect(Collectors.toList())
        ;

        // Prepare the section name line.
        String nameLine = String.format("[%s]", name);

        // Prepare all lines together.
        List<String> allLines = new ArrayList<>();
        allLines.add(nameLine);
        allLines.addAll(linesIndented);

        // Put all together.
        String lyricsComplete = allLines.stream()
            .reduce("", (acc, s) -> acc.concat(NEWLINE_SEQUENCE).concat(s))
        ;

        // Return the result.
        return lyricsComplete;

    }



    @Override
    public String toString() {
        return String.format("[%s]%s%s", name, NEWLINE_SEQUENCE, lyricsText);
    }



    /**
     * Creates a lyrics section based on the given line (which should be the section's beginning markup).
     * 
     * @param sectionBeginningLine Line describing the section.
     * @return Returns a new section based on the given section beginning line.
     */
    public static Section fromBeginningLine(String sectionBeginningLine) {

        // Convert the given string to upper case first.
        String sectionBeginningLineUpperCase = sectionBeginningLine.toUpperCase();

        // Integrity check.
        //if ( ! (matchesPattern(sectionBeginningLine, SECTION_BEGINNING_REGEX)) ) {
        if ( ! (matchesPattern(sectionBeginningLineUpperCase, SECTION_BEGINNING_REGEX)) ) {
            throw new RuntimeException(String.format("The given string does not parse as a beginning of a song section: %s", sectionBeginningLine));
        }

        // Examples of a song section beginning:
        // [V1]
        // [C]
        // [V2A]
        // This means the second character (after the left square bracket) is a section type code.
        char typeCodeCharacter = sectionBeginningLine.charAt(1);
        // We also need a section name.
        // This means we have to strip off the leading and trailing square brackets.
        String sectionName = sectionBeginningLineUpperCase.substring(1, sectionBeginningLineUpperCase.length() - 1);

        // Create a new song section based on the section type code.
        Section section;
        switch (typeCodeCharacter) {
            case 'V':
                section = new Verse(sectionName, null);
                break;
            case 'C':
                section = new Chorus(sectionName, null);
                break;
            case 'B':
                section = new Bridge(sectionName, null);
                break;
            case 'P':
                section = new PreChorus(sectionName, null);
                break;
            case 'T':
                section = new Tag(sectionName, null);
                break;
            default:
                throw new RuntimeException(String.format("This type of a song section is not supported here: '%c'", typeCodeCharacter));
        }

        // Return the result.
        return section;

    }



}
