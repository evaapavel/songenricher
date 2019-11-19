package cz.jollysoft.songenricher.transformers;



import java.util.List;
import java.util.ArrayList;

import cz.jollysoft.songenricher.transformers.xml.Token;
import cz.jollysoft.songenricher.xmlpieces.Document;



/**
 * Helps to transform a list of text lines into an XML document.
 * 
 * @author Pavel Foltyn
 */
public class Xmler {



    /** Lines of the text to transform into an XML document. */
    private List<String> lines;

    /** Text lines concatenated into a single huge string. */
    private String entireText;

    /** Tokens prepared for XML parsing. */
    private List<Token> xmlTokens;

    /** XML document made out of the given text. */
    private Document xmlDocument;



    /**
     * Constructor.
     * 
     * @param lines Lines of text to transform into XML.
     */
    public Xmler(List<String> lines) {
        this.lines = lines;
    }



    /**
     * Gets the resulting XML document.
     * 
     * @return Returns the XML document produced out of the given lines of text.
     */
    public Document getXmlDocument() {
        return xmlDocument;
    }



    public List<Token> getXmlTokens() {
        return xmlTokens;
    }



    /**
     * Parses the given input text and tries to convert it into an XML document.
     */
    public void parse() {

        // Make the given lines of text a "plain text".
        flatten();

        // Convert the text into XML.
        produce();

    }



    /**
     * Convert text lines into a single string.
     */
    private void flatten() {
        String flatText = lines.stream()
            .reduce("", (acc, s) -> acc.concat(" ").concat(s))
            //.reduce("", String::concat)
        ;
        entireText = flatText;
    }



    /**
     * Convert a single string into an XML document.
     */
    private void produce() {
        List<Token> tokens = tokenize(entireText);
        xmlTokens = tokens;
    }



    /**
     * Tokenizes a given text.
     * 
     * @param text Text to tokenize.
     * @return Returns a list of tokens obtained from the input text.
     */
    private List<Token> tokenize(String text) {

        // Prepare a resulting list of tokens.
        List<Token> tokens = new ArrayList<>();
        //Token token;

        // Scan characters within the string, one by one.
        int currentPos = 0;
        int endPos = text.length();
        while (currentPos < endPos) {

            // Access the current character.
            char c = text.charAt(currentPos);

            // The first character in a token to process tell whether it is a "markup" token ("<...>") or a "text" token (e.g. "Praha").
            boolean isMarkup = (c == '<');

            // Process the current token.
            if (isMarkup) {

                // Markup token.
                // Search for the first '>'. This shall be the end of this markup token (inclusive).
                // Skip any sequences quoted with single quotes or double quotes.
                //int pos = currentPos;
                // Skip the character that the current token begins with.
                int pos = currentPos + 1;
                boolean endOfMarkupFound = false;
                boolean insideQuotedSequence = false;
                char quotingMark = '"';
                while (pos < endPos) {
                    char cc = text.charAt(pos);
                    if ( (cc == '>') && ( ! insideQuotedSequence ) ) {
                        // End of this markup token has been encountered.
                        //token = new Token(text.substring(currentPos, pos + 1));
                        //tokens.add(token);
                        addToken(tokens, text, currentPos, pos + 1);
                        currentPos = pos + 1;
                        endOfMarkupFound = true;
                        break;
                    }
                    if ( ((cc == '"') || (cc == '\'')) && ( ! insideQuotedSequence ) ) {
                        // Beginning of a quoted sequence.
                        insideQuotedSequence = true;
                        quotingMark = cc;
                    } else if ( (cc == '"') && (insideQuotedSequence) && (quotingMark == '"') ) {
                        // End of a sequence quoted with double quotes.
                        insideQuotedSequence = false;
                    } else if ( (cc == '\'') && (insideQuotedSequence) && (quotingMark == '\'') ) {
                        // End of a sequence quoted with single quotes.
                        insideQuotedSequence = false;
                    } else {
                        // All other cases:
                        // Either an ordinary character.
                        // Or a single quote inside a double-quoted sequence.
                        // Or a double quote inside a single-quoted sequnece.
                        // Or a "greater than" sign inside a quoted sequence (no matter whether single- or double-quoted).
                        // For all these cases, just keep going.
                    }
                    pos++;
                }
                if ( ! endOfMarkupFound ) {
                    throw new RuntimeException(String.format("End of a markup token not found past the position %d.", currentPos));
                }

            } else {

                // Text token.
                // Search for the first '<'. This shall be the end of this text token (exclusive).
                //int lessThanSignPos = text.indexOf('<', currentPos);
                // Skip the character that the current token begins with.
                // The indexOf(int ch, int fromIndex) method works fine even for situations where the fromIndex parameter points outside the string.
                int lessThanSignPos = text.indexOf('<', currentPos + 1);

                // Handle both cases (found / not found).
                if (lessThanSignPos >= 0) {
                    // Integrity check.
                    if ( ! (lessThanSignPos > currentPos) ) {
                        throw new RuntimeException(String.format("The current position in the tokenizer is %d. But the '<' has been found at %d.", currentPos, lessThanSignPos));
                    }
                    // Start of another token found.
                    //token = new Token(text.substring(currentPos, lessThanSignPos));
                    //tokens.add(token);
                    addToken(tokens, text, currentPos, lessThanSignPos);
                    currentPos = lessThanSignPos;
                } else {
                    // No more tokens.
                    //token = new Token(text.substring(currentPos, endPos));
                    //tokens.add(token);
                    addToken(tokens, text, currentPos, endPos);
                    currentPos = endPos;
                }

            }

        }

        // Return the result.
        return tokens;

    }



    /**
     * Extracts a token from a given text and adds it to a given list of tokens.
     * The method leaves out leading and trailing whitespace characters. This is called "trimming".
     * If the result of the trim operation (from both the beginning and the end) is an empty string,
     * then the "empty" token is ignored and thus not added to the resulting token list.
     * 
     * @param tokens List of tokens to add the new token to.
     * @param text Text to extract the token out of.
     * @param startPos Starting position of the token (inclusive).
     * @param endPos Ending position of the token (exclusive).
     */
    private void addToken(List<Token> tokens, String text, int startPos, int endPos) {

        // Integrity check:
        // Starting position must fall within the given text string.
        // Ending position must fall within the given text string, or it can be equal to the text's length (but must not go beyond it).
        // Starting position must be less than the ending position (the token must have one character at least).
        if ( ! ((startPos >= 0) && (startPos < text.length())) ) {
            throw new RuntimeException(String.format("The starting position of %d is outside the valid indices for the given string <%d, %d).", startPos, 0, text.length()));
        }
        if ( ! ((endPos >= 0) && (endPos <= text.length())) ) {
            throw new RuntimeException(String.format("The ending position of %d is outside the valid indices for the given string <%d, %d>.", startPos, 0, text.length()));
        }
        if ( ! (startPos < endPos) ) {
            throw new RuntimeException(String.format("The starting position is %d, the ending position is %d. Valid positions for the given string are <%d, %d). The starting position should be LESS THAN the ending position.", startPos, endPos, 0, text.length()));
        }

        // Extract the token.
        String tokenAsString = text.substring(startPos, endPos);

        // Trim the token.
        String tokenAsStringTrimmed = tokenAsString.trim();

        // If there is something left after trimming, create a Token object out of the remainder and add it to the list.
        if (tokenAsStringTrimmed.length() > 0) {
            Token token = new Token(tokenAsStringTrimmed);
            tokens.add(token);
        }

    }



}