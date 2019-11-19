package cz.jollysoft.songenricher.transformers;



import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;



/**
 * Helps to transform an array of bytes into a string representing a line in a
 * text file.
 * 
 * @author Pavel Foltyn
 */
public class Liner {



    /** Initial size of the auxiliary byte array from which lines are produced. */
    private static final int INITIAL_BYTE_ARRAY_SIZE = 64;

    /** The LF (line feed) character. */
    private static final byte LF = (byte) 0x0A;

    /** The CR (carriage return) character. */
    private static final byte CR = (byte) 0x0D;



    /** Byte array to store data before a line gets produced. */
    private byte[] bytes;

    /** Current position in the byte array to store the next byte. */
    private int currentPosition;

    /** Character set to be used when converting bytes from the byte array into a string. */
    private Charset charset;



    /**
     * Constructor.
     */
    public Liner() {
        this(Charset.forName("UTF-8"));
    }



    /**
     * Constructor.
     * 
     * @param charset Character set use for byte-array-to-string conversions.
     */
    public Liner(Charset charset) {
        bytes = new byte[INITIAL_BYTE_ARRAY_SIZE];
        currentPosition = 0;
        this.charset = charset;
    }



    /**
     * Adds bytes from the buffer to the inner byte array.
     * 
     * @param buffer Byte buffer to get data from.
     * @param startPosition Position in the given buffer to start adding bytes from.
     * @param byteCount Number of bytes to add.
     */
    public void addBytes(ByteBuffer buffer, int startPosition, int byteCount) {

        // Make the inner array bigger if necessary.
        if (currentPosition + byteCount > bytes.length) {
            int newSize = bytes.length * 2;
            while (currentPosition + byteCount > newSize) {
                newSize *= 2;
            }
            //byte[] newHolder = new byte[newSize];
            byte[] newHolder = Arrays.copyOf(bytes, newSize);
            bytes = newHolder;
        }

        // Prepare a position within the buffer.
        buffer.position(startPosition);

        // Take bytes from the buffer and store them into the inner array.
        for (int i = 0; i < byteCount; i++) {
            bytes[currentPosition] = buffer.get();
            currentPosition++;
        }

    }



    //public List<String> getLines() {
    //public List<String> getLines(boolean forceProcessCR) {
    //* @param forceProcessCR True :-: force the processing of a CR byte even if at the very end of the range within the byte array where the bytes read are stored, false :-: await a LF byte past a CR byte.
    /**
     * Tries to extract lines from the inner array.
     * Prevent undesirable issues with not processing the last line of the file on Mac machines.
     * 
     * @param forceNonstandardEOLN True :-: force either (a) the processing of a CR byte as the last byte of the file or (b) the processing of the last line of the file where there's no EOLN, false :-: standard behaviour, all other lines but the very last one.
     * @return Returns strings that represent lines taken from the inner byte array.
     */
    public List<String> getLines(boolean forceNonstandardEOLN) {

        // Prepare a resulting list (for the extracted lines).
        List<String> lines = new ArrayList<>();

        // Search for the next EOLN character (either LF or CR+LF or CR).
        // Note that LF is used on Unix machines whereas CR+LF is used by Windows and CR on Mac's.
        int pos = 0;
        while (pos < currentPosition) {

            // Find a next EOLN sequence (if any).
            //int posEOLN = findEOLN(pos, currentPosition);
            //int posEOLN = findEOLN(pos, currentPosition, forceProcessCR);
            int posEOLN = findEOLN(pos, currentPosition, forceNonstandardEOLN);

            // End condition check.
            if (posEOLN == -1) {
                break;
            }

            // OK. We've got a line from pos to posEOLN.
            // Let's extract it and add it to the result.
            //String line = new String(bytes, pos, posEOLN - pos, Charset.defaultCharset());
            String line = new String(bytes, pos, posEOLN - pos, charset);
            lines.add(line);

            // Move past the EOLN sequence.
            //int lengthOfEOLN = checkLengthOfEOLN(posEOLN, pos, currentPosition);
            int lengthOfEOLN = checkLengthOfEOLN(posEOLN, pos, currentPosition, forceNonstandardEOLN);
            //pos += lengthOfEOLN;
            pos = posEOLN + lengthOfEOLN;

        }

        // Once all the lines have been stored in the resulting list,
        // do "squeeze" up the inner byte array by removing bytes that have just been transformed into lines
        // and leaving only bytes after the last EOLN sequence (if any).
        if (pos > 0) {
            if (pos < currentPosition) {
                System.arraycopy(bytes, pos, bytes, 0, currentPosition - pos);
            }
            currentPosition -= pos;
        }

        // Return the result.
        return lines;

    }



    //private int findEOLN(int startPos, int endPos) {
    //private int findEOLN(int startPos, int endPos, boolean forceProcessCR) {
    //* @param forceProcessCR True :-: do NOT EXPECT a LF byte if a CR byte have been found AT THE END of the read-bytes range, false :-: do EXPECT a LF byte after a CR byte.
    /**
     * Tries to find the first EOLN byte (LF or CR) within the inner byte array.
     * 
     * @param startPos Starting position (index of the first byte to check).
     * @param endPos Ending position (index of the first byte NOT TO CHECK).
     * @param forceNonstandardEOLN True :-: force either (a) the processing of a CR byte as the last byte of the file or (b) the processing of the last line of the file where there's no EOLN, false :-: standard behaviour, all other lines but the very last one.
     * @return Returns the position of the first LF or CR byte. Or -1 if no such byte has been encountered within the specified range.
     */
    private int findEOLN(int startPos, int endPos, boolean forceNonstandardEOLN) {

        // Try to find the first byte (in OS's other than Windows, this will be the last one as well) of an EOLN sequence.
        for (int pos = startPos; pos < endPos; pos++) {
            if ( (bytes[pos] == LF) || (bytes[pos] == CR) ) {
                //return pos;
                // A very specific situation might occur if the following conditions are met:
                // (i) EOLN sequences are CR+LF (Windows style).
                // (ii) CR byte is encountered at the last possible position (endPos - 1).
                // If we accepted here an EOLN sequence (CR only, i.e. Mac), then another EOLN sequence would be found (LF only, i.e. Unix)
                // after next reading from the input file channel is achieved. This would result in one empty line to be extracted
                // which is not in the original file. For this situation, we would rather return -1 (and wait for more bytes to be read).
                //if ( (pos == endPos - 1) && (bytes[pos] == CR) ) {
                //if ( (pos == endPos - 1) && (bytes[pos] == CR) && ( ! forceProcessCR ) ) {
                if ( (pos == endPos - 1) && (bytes[pos] == CR) && ( ! forceNonstandardEOLN ) ) {
                    // Await more bytes from the file.
                    // An additional LF could be read immediately after this CR.
                    return -1;
                }
                return pos;
            }
        }

        // No EOLN byte found.
        if ( (forceNonstandardEOLN) && (endPos > startPos) ) {
            // If this is the last line of the file and there is at least one character (byte) to be processed,
            // then pretend an EOLN byte to be at the ending position (where there is actually nothing to check).
            return endPos;
        }
        return -1;

    }



    //private int checkLengthOfEOLN(int posEOLN, int startPos, int endPos) {
    /**
     * Checks for the length of the EOLN sequence. There might be either LF or CR+LF or CR.
     * 
     * @param posEOLN Position of the FIRST byte of the EOLN sequence.
     * @param startPos Starting position of the search.
     * @param endPos Ending position of the search.
     * @param forceNonstandardEOLN True :-: force either (a) the processing of a CR byte as the last byte of the file or (b) the processing of the last line of the file where there's no EOLN, false :-: standard behaviour, all other lines but the very last one.
     * @return Returns the length (in bytes) of the EOLN sequence (1 or 2). The length of 0 (zero) is returned for the last line of the file without any EOLN at all.
     */
    private int checkLengthOfEOLN(int posEOLN, int startPos, int endPos, boolean forceNonstandardEOLN) {

        // Before the integrity check, allow a line (the very last line of the file) without any EOLN.
        // In such a case, the position of an "imaginary" EOLN sequence is set to the ending position.
        if ( (posEOLN == endPos) && (forceNonstandardEOLN) && (endPos > startPos) ) {
            return 0;
        }

        // Integrity check.
        if ( ! ((posEOLN >= startPos) && (posEOLN < endPos)) ) {
            throw new RuntimeException(String.format("The position %d of the EOLN sequence does not fall between the starting position of %d and the ending position of %d.", posEOLN, startPos, endPos));
        }

        // Check for LF first.
        if (bytes[posEOLN] == LF) {
            // LF is always THE LAST byte of a line.
            return 1;
        }

        // Check for CR next.
        if (bytes[posEOLN] == CR) {
            // If the next position is out of the given interval of indices or the next byte is NOT a LF byte, then assume the line is ended with CR only.
            if ( (posEOLN + 1 < endPos) && (bytes[posEOLN + 1] == LF) ) {
                return 2;
            }
            return 1;
        }

        // Error?
        //return 0;
        throw new RuntimeException(String.format("The byte on the given position %d is not LF or CR.", posEOLN));

    }



}