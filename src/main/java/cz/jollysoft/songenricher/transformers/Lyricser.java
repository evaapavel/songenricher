package cz.jollysoft.songenricher.transformers;



import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import cz.jollysoft.songenricher.dataholders.Song;
import cz.jollysoft.songenricher.dataholders.songmarkup.Section;

import static cz.jollysoft.songenricher.constants.AppConstants.NEW_LINE_SEQUENCE;
import static cz.jollysoft.songenricher.util.AppUtils.matchesPattern;



/**
 * Helps to parse the lyrics of a song.
 * 
 * @author Pavel Foltyn
 */
public class Lyricser {



    /** Regular expression describing a line that represents the beginning of a lyrics section. */
    //private static final String SECTION_BEGINNING_REGEX = "\\[((V(([1-9][0-9]*(([abcdABCD])|()))|()))|(C(([1-4](([abcdABCD])|()))|()))|(B)|(P)|(T))\\]";
    private static final String SECTION_BEGINNING_REGEX = "\\[((V(([1-9][0-9]*(([abcdABCD])|()))|()))|(C(((([1-4])|())(([abcdABCD])|()))|()))|(B)|(P)|(T))\\]";



    /** Song to parse the lyrics of. */
    private Song song;



    /**
     * Constructor.
     * 
     * @param song Song containing the lyrics that should be parsed.
     */
    public Lyricser(Song song) {
        this.song = song;
    }



    public Song getSong() {
        return song;
    }



    /**
     * Parses the lyrics in the given song.
     */
    public void parseLyrics() {
        List<Section> sections = parse(song.getSongRoot().getLyrics().getInnerText());
        song.getSongRoot().getLyrics().getSections().addAll(sections);
    }



    /**
     * Parses given lyrics into a list of lyrics sections.
     * 
     * @param allLyrics Lyrics to parse.
     * @return Returns a list of lyrics sections.
     */
    private List<Section> parse(String allLyrics) {

        // Convert the lyrics string into a list of lines.
        List<String> lines = convertToLines(allLyrics);

        // Convert the lines into a list of lyrics sections.
        List<Section> sections = convertToSections(lines);

        // Return the result.
        return sections;

    }



    /**
     * Converts given lyrics to a list of lines by splitting the text according to newline characters.
     * 
     * @param allLyrics Lyrics to convert to lines.
     * @return Returns a list of lines with the given lyrics.
     */
    private List<String> convertToLines(String allLyrics) {

        // Convert the lyrics string into a list of lines.
        //List<String> lines = allLyrics.split("\r\n")

        // Step 1: Replace all CRLF with LF and all CR with LF. Now there should be just LF's.
        String lyricsWithNewlinesNormalized = allLyrics.replace("\r\n", "\n").replace('\r', '\n');
        
        // Step 2: Get an array of lines of the lyrics.
        String[] arrayOfLines = lyricsWithNewlinesNormalized.split("\n");

        // Step 3: Convert the array into a list.
        List<String> lines = Arrays.asList(arrayOfLines);
        //List<String> lines = new ArrayList<>();
        //lines.addAll(arrayOfLines);
        // This syntax is only available in Java 9 and above:
        //List<String> lines = List.of("a", "b", "c");

        // Return the result.
        return lines;

    }



    /**
     * Converts a given list of lyrics lines to a list of lyrics sections.
     * 
     * @param lines Text lines with the lyrics.
     * @return Returns a list of sections with the given lyrics.
     */
    private List<Section> convertToSections(List<String> lines) {

        // This conversion is very simple.
        // At the beginning, assume that you're reading the "section beginning" line. It looks something like this, for example: [V1]
        // (There are no indenting spaces before the section mark.)
        // Subsequent lines are part of that section (text of the lyrics) until you encounter another "section beginning" line.
        // (Lyrics should be indented with a space or more.)

        // Prepare a resulting list of lyrics sections.
        List<Section> sections = new ArrayList<>();

        // Build sections.
        Section currentSection = null;
        List<String> linesWithinSection = new ArrayList<>();
        for (String line : lines) {

            // New section may be started via two different ways.
            boolean newSectionToStart = false;
            if (currentSection == null) {
                // Either this is the very first line of the entire lyrics text.
                // Do verify the current line is really a section beginning.
                if ( ! (matchesPattern(line, SECTION_BEGINNING_REGEX)) ) {
                    throw new RuntimeException(String.format("Expection a section beginning sequence. But the following has been encountered: %s", line));
                }
                newSectionToStart = true;
            } else {
                // Or the current line looks like a section beginning.
                if (matchesPattern(line, SECTION_BEGINNING_REGEX)) {
                    newSectionToStart = true;
                }
            }

            if (newSectionToStart) {
                // Handle the previous section first.
                if (currentSection != null) {
                    String sectionLyricsText = linesWithinSection.stream()
                        .reduce((acc, s) -> acc.concat(NEW_LINE_SEQUENCE).concat(s))
                        .orElse("")
                    ;
                    currentSection.setLyricsText(sectionLyricsText);
                    sections.add(currentSection);
                }
                // Prepare a new section.
                currentSection = Section.fromBeginningLine(line);
                linesWithinSection.clear();
            } else {
                // This is not a section start sequence.
                // Just an ordinary line within the lyrics (belonging to the current section, though).
                // Handle the line. Add it to the sections' lines.
                linesWithinSection.add(line);
            }

        }

        // Add the last section to the list.
        if (currentSection != null) {
            String sectionLyricsText = linesWithinSection.stream()
                .reduce((acc, s) -> acc.concat(NEW_LINE_SEQUENCE).concat(s))
                .orElse("")
            ;
            currentSection.setLyricsText(sectionLyricsText);
            sections.add(currentSection);
        }

        // Return the result.
        return sections;

    }



}
