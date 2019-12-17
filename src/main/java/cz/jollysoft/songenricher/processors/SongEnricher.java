package cz.jollysoft.songenricher.processors;



import java.nio.file.Path;
import java.util.Optional;
import java.util.function.UnaryOperator;

import cz.jollysoft.songenricher.dataholders.Ensemble;
//import cz.jollysoft.songenricher.dataholders.Ensemble;
import cz.jollysoft.songenricher.dataholders.Song;
import cz.jollysoft.songenricher.dataholders.songmarkup.Chorus;
import cz.jollysoft.songenricher.dataholders.songmarkup.Verse;
import cz.jollysoft.songenricher.exceptions.PipelineException;
import cz.jollysoft.songenricher.transformers.Lyricser;



/**
 * Adds useful information to the song.
 * 
 * @author Pavel Foltyn
 */
public class SongEnricher implements UnaryOperator<Song> {



    /** Path to the root of input files. */
    private Path inputPathRoot;

    /** Path to the root of output files. */
    private Path outputPathRoot;



    /**
     * Constructor.
     * 
     * @param inputPathRoot  Path to the root of input files.
     * @param outputPathRoot Path to the root of output files.
     */
    public SongEnricher(Path inputPathRoot, Path outputPathRoot) {
        this.inputPathRoot = inputPathRoot;
        this.outputPathRoot = outputPathRoot;
    }



    public Path getInputPathRoot() {
        return inputPathRoot;
    }

    //public void setInputPathRoot(Path inputPathRoot) {
    //    this.inputPathRoot = inputPathRoot;
    //}

    public Path getOutputPathRoot() {
        return outputPathRoot;
    }

    //public void setOutputPathRoot(Path outputPathRoot) {
    //    this.outputPathRoot = outputPathRoot;
    //}



    @Override
    public Song apply(Song song) {

        //return new Song();
        //return new Song(song.getEnsemble());
        //return new Song(new Ensemble(song.getEnsemble().getPath()));
        //return new Song();

        // Prepare a new song via cloning the given one.
        Song enrichedSong;
        try {
            enrichedSong = (Song) song.clone();
        } catch (CloneNotSupportedException e) {
            //throw new RuntimeException(String.format("The given song (%s) cannot be cloned: %s", song.toString(), e.toString()), e);
            throw new PipelineException(String.format("The given song (%s) cannot be cloned: %s", song.toString(), e.toString()), e);
        }

        // Set up a new path for the output ensemble.
        setOutputPath(enrichedSong);

        // Count the number of song's verses and add this information to the song's name (withing the XML song element).
        countVerses(enrichedSong);

        // Add numbering to verses and choruses.
        addNumbering(enrichedSong);

        // Serialize the song sections back to the lyrics song element.
        Lyricser lyricser = new Lyricser(enrichedSong);
        lyricser.synthesizeLyrics();
        enrichedSong = lyricser.getSong();

        // Done!

        // Return the result.
        return enrichedSong;

    }



    /**
     * Sets the output path to a given song being enriched.
     * 
     * @param enrichedSong Song to set the output path for.
     */
    private void setOutputPath(Song enrichedSong) {

        // Get the original input path from the song.
        Path inputPath = enrichedSong.getEnsemble().getPath();

        // Get the relative part of the song's input path (relative according to the root of input paths).
        Path relativePath = inputPathRoot.relativize(inputPath);

        // Prepare the song's output path.
        Path outputPath = outputPathRoot.resolve(relativePath);

        // Store the resulting path (the output one) back to the song.
        //enrichedSong.getEnsemble().setPath(outputPath);
        // Actually, we have to create a new Ensemble object for the song to be able to assign it a new path.
        // Which is good and correct because once the song data gets serialized, it will be necessary to store it
        // to a NEW FILE, of course.
        Ensemble outputEnsemble = new Ensemble(outputPath);
        enrichedSong.setEnsemble(outputEnsemble);

    }



    /**
     * Counts verses in a given song being enriched and adds the number of verses to the song's title.
     * 
     * @param enrichedSong Song to set the output path for.
     */
    private void countVerses(Song enrichedSong) {

        // Count the verses of the given song.
        // Counting the verses actually means finding a verse with the greatest section number.
        Optional<Integer> greatestSectionNumber = enrichedSong.getSongRoot().getLyrics().getSections().stream()
            .filter(section -> section instanceof Verse)
            .map(section -> (Verse) section)
            .map(verse -> verse.getSectionNumber())
            .max(Integer::compare)
        ;

        // Store info about the verses in the song title.
        if (greatestSectionNumber.isPresent()) {
            int verseCount = greatestSectionNumber.get().intValue();
            String originalTitle = enrichedSong.getSongRoot().getTitle().getInnerText();
            String titleWithVerseCount = String.format("%s (%d %s)", originalTitle, verseCount, ((verseCount == 1) ? ("sloka") : (((verseCount >= 2) && (verseCount <= 4)) ? ("sloky") : ("slok"))));
            enrichedSong.getSongRoot().getTitle().setInnerText(titleWithVerseCount);
        }

    }



    /**
     * Adds numbering to the verses and choruses of a given song being enriched.
     * 
     * @param enrichedSong Song to set the output path for.
     */
    private void addNumbering(Song enrichedSong) {

        // Select verses and choruses.
        // Make sure a verse number (chorus number) is present (verses only).
        // Make sure that if a given song section has a section part (section part letter), it is only the section part A.
        // Add the section number to the lyrics.

        // Handle verses separately.
        // Examples:
        // [: Nejkrásnější chvíle vždycky jsou na kolenou :] ---> 1. [: Nejkrásnější chvíle vždycky jsou na kolenou :]
        // [: Sílu opustit svůj hřích jsem dostal na kolenou :] ---> 2. [: Sílu opustit svůj hřích jsem dostal na kolenou :]
        enrichedSong.getSongRoot().getLyrics().getSections().stream()
            .filter(section -> section instanceof Verse)
            .map(section -> (Verse) section)
            //.map(verse -> {
            .forEach(verse -> {
                    if ((verse.getSectionPart() == null) || ("A".equals(verse.getSectionPart()))) {
                    int verseNumber = verse.getSectionNumber().intValue();
                    String lyricsOriginal = verse.getLyricsText();
                    String lyricsEnriched = lyricsOriginal;
                    for (int i = 0; i < lyricsOriginal.length(); i++) {
                        char c = lyricsOriginal.charAt(i);
                        if ( ! ((c == ' ') || (c == '\t') || (c == '\r') || (c == '\n')) ) {
                            lyricsEnriched = lyricsOriginal.substring(0, i) + String.format("%d. ", verseNumber) + lyricsOriginal.substring(i);
                            break;
                        }
                    }
                    verse.setLyricsText(lyricsEnriched);
                }
                //return verse;
            })
        ;

        // Handle choruses separately.
        // Examples:
        // V modlitbách když budeš stát, Duch svatý začne vát, ---> Ref.: V modlitbách když budeš stát, Duch svatý začne vát,
        // Já mám přečkat bouře čas ---> Ref. 1: Já mám přečkat bouře čas
        // Já mám přečkat bouře čas ---> Ref. 2: Já mám přečkat bouře čas
        enrichedSong.getSongRoot().getLyrics().getSections().stream()
            .filter(section -> section instanceof Chorus)
            .map(section -> (Chorus) section)
            //.map(chorus -> {
            .forEach(chorus -> {
                    if ((chorus.getSectionPart() == null) || ("A".equals(chorus.getSectionPart()))) {
                    String lyricsOriginal = chorus.getLyricsText();
                    String lyricsEnriched = lyricsOriginal;
                    if (chorus.getSectionNumber() != null) {
                        // This is a less usual case where even the chorus part of a song has numbering.
                        int chorusNumber = chorus.getSectionNumber().intValue();
                        for (int i = 0; i < lyricsOriginal.length(); i++) {
                            char c = lyricsOriginal.charAt(i);
                            if ( ! ((c == ' ') || (c == '\t') || (c == '\r') || (c == '\n')) ) {
                                lyricsEnriched = lyricsOriginal.substring(0, i) + String.format("Ref. %d: ", chorusNumber) + lyricsOriginal.substring(i);
                                break;
                            }
                        }
                    } else {
                        // This is a typical case where there is just one chorus in the song (with no numbering, even if it can have parts identified by letters such as CA, CB, ...).
                        for (int i = 0; i < lyricsOriginal.length(); i++) {
                            char c = lyricsOriginal.charAt(i);
                            if ( ! ((c == ' ') || (c == '\t') || (c == '\r') || (c == '\n')) ) {
                                lyricsEnriched = lyricsOriginal.substring(0, i) + "Ref.: " + lyricsOriginal.substring(i);
                                break;
                            }
                        }
                    }
                    chorus.setLyricsText(lyricsEnriched);
                }
                //return chorus;
            })
        ;

    }



}
