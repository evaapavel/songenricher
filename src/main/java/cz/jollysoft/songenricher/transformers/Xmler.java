package cz.jollysoft.songenricher.transformers;



import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
//import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
//import java.util.Map;
//import java.util.Deque;

import cz.jollysoft.songenricher.transformers.xml.ElementToken;
import cz.jollysoft.songenricher.transformers.xml.ProcessingToken;
import cz.jollysoft.songenricher.transformers.xml.TextToken;
import cz.jollysoft.songenricher.transformers.xml.Token;
import cz.jollysoft.songenricher.xmlpieces.Attribute;
import cz.jollysoft.songenricher.xmlpieces.Document;
import cz.jollysoft.songenricher.xmlpieces.Element;
import cz.jollysoft.songenricher.xmlpieces.Piece;
import cz.jollysoft.songenricher.xmlpieces.Text;

import static cz.jollysoft.songenricher.constants.AppConstants.NEWLINE_SEQUENCE;
import static cz.jollysoft.songenricher.util.AppUtils.matchesPattern;



/**
 * Helps to transform a list of text lines into an XML document.
 * Can be used for the reverse operation as well when a need be to transform an XML document into text lines.
 * 
 * @author Pavel Foltyn
 */
public class Xmler {



    /** Defines a regular expression that is to be used when checking for XML identifiers. */
    //private static final String XML_IDENTIFIER_REGEX = "[a-zA-Z\\_\\-][a-zA-Z\\_\\-0-9]*";
    private static final String XML_IDENTIFIER_REGEX = "[a-zA-Z\\_][a-zA-Z\\_\\-0-9]*";

    // /** Platform dependent newline character(s). CRLF for Windows, LF for Linux, CR for Mac. */
    //private static final String NEWLINE_SEQUENCE = ( ((System.getProperty("line.separator") != null) && (System.getProperty("line.separator").length() > 0)) ? (System.getProperty("line.separator")) : ("\n") );



    /** Lines of the text to transform into an XML document. */
    private List<String> lines;

    /** Text lines concatenated into a single huge string. */
    private String entireText;

    /** Tokens prepared for XML parsing. */
    private List<Token> xmlTokens;

    /** XML document made out of the given text. */
    private Document xmlDocument;



    /** Number of spaces to add at the beginning of a line when converting an XML document to text lines and a new recursion level is to start. */
    private int indentationStep = 2;

    /** Print writer to use when converting XML to text. */
    private PrintWriter printWriter;



    /**
     * Constructor.
     * 
     * @param xmlDocument XML document to transform into text lines.
     */
    public Xmler(Document xmlDocument, int indentationStep) {
        this.xmlDocument = xmlDocument;
        this.indentationStep = indentationStep;
    }



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



    public List<String> getLines() {
        return lines;
    }



    public int getIndentationStep() {
        return indentationStep;
    }

    public void setIndentationStep(int indentationStep) {
        this.indentationStep = indentationStep;
    }



    /**
     * Gets the given XML document and tries to produce lines of text out of it.
     */
    public void synthetize() {
        printXmlToWriterAndConvertToLines();
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
        //String newlineSequence = String.format("%n");
        String flatText = lines.stream()
            //.reduce("", String::concat)
            //.reduce("", (acc, s) -> acc.concat(" ").concat(s))
            // Try to keep newline characters between the lines.
            //.reduce("", (acc, s) -> acc.concat("\n").concat(s))
            //.reduce((acc, s) -> acc.concat("\n").concat(s))
            .reduce((acc, s) -> acc.concat(NEWLINE_SEQUENCE).concat(s))
            // Here we've got an Optional<String>.
            .orElse("")
        ;
        entireText = flatText;
    }



    /**
     * Convert a single string into an XML document.
     */
    private void produce() {

        // Tokenize.
        List<Token> tokens = tokenize(entireText);
        xmlTokens = tokens;

        // Parse the tokens.
        List<Token> parsedTokens = parseTokens(xmlTokens);
        xmlTokens = parsedTokens;

        // Analyze the list of tokens and build the structure of the resulting XML document.
        analyzeAndBuild();

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



    /**
     * Parses each token from a given list and returns a list of the same tokens parsed.
     * 
     * @param tokens Tokens to parse.
     * @return Returns a list of the same tokens that are parsed.
     */
    private List<Token> parseTokens(List<Token> tokens) {

        // Prepare a resulting list.
        //List<Token> parsedTokens = new ArrayList<>();
        List<Token> parsedTokens = null;

        // Parse each token.
        parsedTokens = tokens.stream()
            .map(this::parseToken)
            .collect(Collectors.toList())
        ;

        // Return the result.
        return parsedTokens;

    }



    /**
     * Parses a given token.
     * 
     * @param token Token to parse.
     * @return Returns a parsed token.
     */
    private Token parseToken(Token token) {

        // Determine the type of the token.
        Token parsedToken;
        if (token.getValue().startsWith("<?")) {
            // This should be a processing instruction token.
            parsedToken = new ProcessingToken(token.getValue());
        } else if (token.getValue().startsWith("<")) {
            // This should be an element token.
            parsedToken = new ElementToken(token.getValue());
        //} else if () {
        //} else {
        //    throw new RuntimeException(String.format("Cannot determine the type of the given token: %s", token.toString()));
        //}
        } else {
            // For all other cases, this should be a text token.
            parsedToken = new TextToken(token.getValue());
        }

        // For each type:
        // Perform an integrity check.
        // Create an appropriate specialized token.
        // Set up the new token's properties.
        if (parsedToken instanceof ProcessingToken) {

            // Processing instruction token.

            // Access the token.
            ProcessingToken processingToken = (ProcessingToken) parsedToken;

            // Mandatory parts of a processing instruction token:
            // Opening and closing sequence.
            // Processing instruction name.
            // This means: A processing instruction token must be at least 5 characters long (<?a?>)
            String tokenText = processingToken.getValue();
            if ( ! (tokenText.length() >= 5) ) {
                throw new RuntimeException(String.format("The processing instruction token is too short: '%s'", tokenText));
            }
            if ( ! (tokenText.startsWith("<?")) ) {
                throw new RuntimeException(String.format("A processing instruction token must begin with '<?'. But this one is: '%s'", tokenText));
            }
            if ( ! (tokenText.endsWith("?>")) ) {
                throw new RuntimeException(String.format("A processing instruction token must end with '?>'. But this one is: '%s'", tokenText));
            }

            // OK. Here we're sure we can dispose of the beginning and ending sequence and there'll be something left.
            // Example: <?xml version="1.0" encoding="UTF-8"?>
            //String tokenTextWithoutBeginAndEnd = tokenText.substring(2, tokenText.length() - 4);
            // The String.substring method signature is as follows:
            // String substring(int beginIndex, int endIndex)
            // The second parameter means an INDEX (exclusive, i.e. the index of the first character to not include in the result),
            // not a number of characters!!!
            String tokenTextWithoutBeginAndEnd = tokenText.substring(2, tokenText.length() - 2);
            //String[] parts = tokenTextWithoutBeginAndEnd.split("[ \t]+");
            String[] parts = splitTokenText(tokenTextWithoutBeginAndEnd);
            if ( ! (parts.length >= 1) ) {
                throw new RuntimeException(String.format("A processing instruction token must have a name. But this one is: '%s'", tokenText));
            }

            // The first part is expected to be the name of the processing instruction token.
            //if ( ! (matchesPattern(parts[0], "[a-zA-Z\\_\\-][a-zA-Z\\_\\-0-9]*")) ) {
            if ( ! (matchesPattern(parts[0], XML_IDENTIFIER_REGEX)) ) {
                throw new RuntimeException(String.format("A processing instruction token must have a name (which is more or less an identifier). But this one is: '%s'", tokenText));
            }
            processingToken.setName(parts[0]);

            // The rest must be attributes (name/value pairs).
            for (int i = 1; i < parts.length; i++) {
                String part = parts[i];
                //Map.Entry<String, String> attributeAndValue = parseAttribute(part);
                //processingToken.getAttributesAndValues().put(attributeAndValue.getKey(), attributeAndValue.getValue());
                String[] attributeAndValue = parseAttribute(part);
                processingToken.getAttributesAndValues().put(attributeAndValue[0], attributeAndValue[1]);
            }

        } else if (parsedToken instanceof ElementToken) {

            // Element token.

            // Access the token.
            ElementToken elementToken = (ElementToken) parsedToken;

            // Mandatory parts of an element token:
            // Opening and closing character (or sequence, i.e. an opening sequence in the closing version of an element token a.k.a. "closing tag").
            // Element name.
            // This means:
            // An element token must be at least 3 characters long (for opening tags such as <a>)
            // or at least 4 characters long (for closing tags such as </a>)
            String tokenText = elementToken.getValue();
            if ( ! ( ((tokenText.startsWith("</")) && (tokenText.length() >= 4)) || (tokenText.length() >= 3) ) ) {
                throw new RuntimeException(String.format("The element token is too short: '%s'", tokenText));
            }
            if ( ! (tokenText.startsWith("<")) ) {
                throw new RuntimeException(String.format("An element token must begin with '<' or '</'. But this one is: '%s'", tokenText));
            }
            if ( ! (tokenText.endsWith(">")) ) {
                throw new RuntimeException(String.format("An element token must end with '>'. But this one is: '%s'", tokenText));
            }

            // OK. Here we're sure we can dispose of the beginning and ending sequence and there'll be something left.
            // We have to distinguish between an opening tag and a closing tag.
            String tokenTextWithoutBeginAndEnd;
            if (tokenText.startsWith("</")) {
                // A closing tag.
                // Caution: Closing tags are supposed to NOT have attributes.
                // Example: </capo>
                //tokenTextWithoutBeginAndEnd = tokenText.substring(2, tokenText.length() - 3);
                tokenTextWithoutBeginAndEnd = tokenText.substring(2, tokenText.length() - 1);
                elementToken.setClosingTag(true);
            } else {
                // An opening tag.
                // Example: <capo print="false">
                //tokenTextWithoutBeginAndEnd = tokenText.substring(1, tokenText.length() - 2);
                tokenTextWithoutBeginAndEnd = tokenText.substring(1, tokenText.length() - 1);
                elementToken.setOpeningTag(true);
            }
            //String[] parts = tokenTextWithoutBeginAndEnd.split("[ \t]+");
            String[] parts = splitTokenText(tokenTextWithoutBeginAndEnd);
            if ( ! (parts.length >= 1) ) {
                throw new RuntimeException(String.format("An element token must have a name. But this one is: '%s'", tokenText));
            }

            // The first part is expected to be the name of the processing instruction token.
            //if ( ! (matchesPattern(parts[0], "[a-zA-Z\\_\\-][a-zA-Z\\_\\-0-9]*")) ) {
            if ( ! (matchesPattern(parts[0], XML_IDENTIFIER_REGEX)) ) {
                throw new RuntimeException(String.format("An element token must have a name (which is more or less an identifier). But this one is: '%s'", tokenText));
            }
            elementToken.setName(parts[0]);

            // Closing tags are NOT supposed to have attributes.
            if ( (elementToken.isClosingTag()) && (parts.length > 1) ) {
                throw new RuntimeException(String.format("The closing version of an element token must NOT have any attributes: '%s'", tokenText));
            }

            // The rest must be attributes (name/value pairs).
            for (int i = 1; i < parts.length; i++) {
                String part = parts[i];
                //Map.Entry<String, String> attributeAndValue = parseAttribute(part);
                //processingToken.getAttributesAndValues().put(attributeAndValue.getKey(), attributeAndValue.getValue());
                String[] attributeAndValue = parseAttribute(part);
                elementToken.getAttributesAndValues().put(attributeAndValue[0], attributeAndValue[1]);
            }

        } else if (parsedToken instanceof TextToken) {

            // There's nothing to do with text tokens.
            // Done!

        } else {

            // Unknown.
            throw new RuntimeException(String.format("This type of token is not supported here: '%s'", parsedToken.getClass().getName()));

        }

        // For the time being:
        return parsedToken;

    }



    // /**
    //  * Checks whether a given text part matches a given pattern.
    //  * 
    //  * @param textPart Text part to check.
    //  * @param regexPattern Pattern to check the given text part against.
    //  * @return Returns true :-: the given text part matches the given pattern, false :-: the given text part does NOT match the given pattern.
    //  */
    // private boolean matchesPattern(String textPart, String regexPattern) {
    //     return textPart.matches(regexPattern);
    // }



    //private Map.Entry<String, String> parseAttribute(String textPart) {
    //* @return Returns a map entry with the parsed name and value.
    /**
     * Parses a given text part as if it be a name/value pair (e.g. print="false").
     * 
     * @param textPart
     * @return Returns a two items' array with the parsed name and value.
     */
    private String[] parseAttribute(String textPart) {
        String[] attributeParts = textPart.split("=");
        if ( ! (attributeParts.length == 2) ) {
            throw new RuntimeException(String.format("Not a valid attribute (name/value pair): %s", textPart));
        }
        //if ( ! (matchesPattern(attributeParts[0], "[a-zA-Z\\_\\-][a-zA-Z\\_\\-0-9]*")) ) {
        if ( ! (matchesPattern(attributeParts[0], XML_IDENTIFIER_REGEX)) ) {
            throw new RuntimeException(String.format("The name of an attribute is supposed to be a sort of an identifier (allowing dashes). But this one: %s", textPart));
        }
        if ( ! ((matchesPattern(attributeParts[1], "'.*'")) || (matchesPattern(attributeParts[1], "\".*\""))) ) {
            throw new RuntimeException(String.format("The value of an attribute is supposed to be enclosed in quotes (single or double). But this one: %s", textPart));
        }
        //Map.Entry<String, String> attributeAndValue = new Map.Entry<>("", "");
        String[] attributeAndValue = new String[2];
        // For the name, take the whole string.
        attributeAndValue[0] = attributeParts[0];
        // For the value, strip the leading and trailing quotes.
        //attributeAndValue[1] = attributeParts[1].substring(1, attributeParts[1].length() - 2);
        attributeAndValue[1] = attributeParts[1].substring(1, attributeParts[1].length() - 1);
        return attributeAndValue;
    }



    //private List<String> splitTokenText(String tokenTextWithoutBeginAndEnd) {
    //* @return Returns a list of parts of the given token text.
    /**
     * Splits a text from a token (the opening and closing sequences must be stripped out already).
     * 
     * @param tokenTextWithoutBeginAndEnd Token text without the opening and trailing sequences.
     * @return Returns an array of parts of the given token text.
     */
    private String[] splitTokenText(String tokenTextWithoutBeginAndEnd) {

        // *******************************************************
        // A few examples how this works:
        // *******************************************************
        // (1)
        // ORIGINAL TOKEN: <?xml version="1.0" encoding="UTF-8"?>
        // INPUT: xml version="1.0" encoding="UTF-8"
        // OUTPUT: xml | version="1.0" | encoding="UTF-8"
        // *******************************************************
        // (2)
        // ORIGINAL TOKEN: </song>
        // INPUT: song
        // OUTPUT: song
        // *******************************************************
        // (3)
        // ORIGINAL TOKEN: <capo print="false">
        // INPUT: capo print="false"
        // OUTPUT: capo | print="false"
        // *******************************************************
        // (4)
        // ORIGINAL TOKEN: <person name="Elvis Presley" yearOfBirth="1935">
        // INPUT: person name="Elvis Presley" yearOfBirth="1935"
        // OUTPUT: person | name="Elvis Presley" | yearOfBirth="1935"
        // *******************************************************

        // Prepare a resulting list.
        List<String> tokenParts = new ArrayList<>();

        // Process the given token text.
        // If there's a quoted sequence, consider the inner text of the sequence to belong to the same token part even if there be whitespace (e.g. spaces).
        int currentPos = 0;
        int endPos = tokenTextWithoutBeginAndEnd.length();
        //boolean insideTokenPart = false;
        while (currentPos < endPos) {

            // Access the current character.
            char c = tokenTextWithoutBeginAndEnd.charAt(currentPos);

            // Try to find the beginning of a token part.
            if ( ! ((c == ' ') || (c == '\t') || (c == '\r') || (c == '\n')) ) {

                // The current character is not whitespace.
                // Therefore, this is the beginning of a token part.

                // Process further the given token text trying to find the end of the token part.
                int pos = currentPos + 1;
                boolean insideQuotedSequence = false;
                char quotingMark = '"';
                while (pos < endPos) {

                    // Access the current character.
                    char cc = tokenTextWithoutBeginAndEnd.charAt(pos);

                    // Try to find the end of the current token part.
                    if ( ((cc == ' ') || (cc == '\t') || (cc == '\r') || (cc == '\n')) && ( ! insideQuotedSequence ) ) {
                        // We're not inside a quoted sequence and a whitespace character has been encountered.
                        // This means we have just proceeded past the last character of the current token part.
                        break;
                    } else if ( ((cc == '"') || (cc == '\'')) && ( ! insideQuotedSequence ) ) {
                        // A beginning of a quoted sequence (single- or double-quoted) is here.
                        insideQuotedSequence = true;
                        quotingMark = cc;
                    } else if ( (cc == '"') && (insideQuotedSequence) && (quotingMark == '"') ) {
                        // An end of a double-quoted sequence is here.
                        insideQuotedSequence = false;
                    } else if ( (cc == '\'') && (insideQuotedSequence) && (quotingMark == '\'') ) {
                        // An end of a single-quoted sequence is here.
                        insideQuotedSequence = false;
                    } else {
                        // All other characters are OK.
                        // Just go forth.
                    }

                    // Iterate.
                    pos++;

                }

                // Integrity check.
                // Avoid unterminated quoted sequences.
                if (insideQuotedSequence) {
                    throw new RuntimeException(String.format("This token contains an unterminated quoted sequence: '%s'", tokenTextWithoutBeginAndEnd));
                }

                // Add the current token part to the resulting list.
                String tokenPart = tokenTextWithoutBeginAndEnd.substring(currentPos, pos);
                tokenParts.add(tokenPart);

                // Iterate the outer loop.
                currentPos = pos;
                
            } else {

                // The current character IS whitespace.

                // Proceed to the next character.
                currentPos++;

            }

        }

        // Return the result.
        String[] tokenPartsAsArray = new String[tokenParts.size()];
        tokenParts.toArray(tokenPartsAsArray);
        return tokenPartsAsArray;
        // Or you can transform the above lines into a single one:
        //return tokenParts.toArray(new String[0]);

    }



    /**
     * Analyzes the list of tokens and builds up an XML document.
     */
    private void analyzeAndBuild() {

        // Declare local variables.
        //Token currentToken;
        //ProcessingToken processingToken;
        //ElementToken elementToken;
        //TextToken textToken;

        // Integrity check.
        // For a valid XML document, we expect 3 tokens at least.
        // Example:
        // <?xml version="1.0" encoding="UTF-8"?>
        // <song>
        // </song>
        if ( ! (xmlTokens.size() >= 3) ) {
            throw new RuntimeException(String.format("To build an XML document, there should be at least 3 tokens. But only %d tokens have been found.", xmlTokens.size()));
        }

        // Iterate through all the XML tokens you've just parsed.
        Iterator<Token> tokenIterator = xmlTokens.iterator();

        // Create a document element and set up its main attributes.
        //xmlDocument = getDocument(tokenIterator);
        xmlDocument = extractDocument(tokenIterator);

        // Build the XML document.
        // Process the remaining tokens.
        //Deque<Piece> stackOfXmlPieces = new ArrayDeque<>();

        //while (tokenIterator.hasNext()) {
        //    // Create an XML piece out of the current token.
        //    Piece currentPiece = getPiece(tokenIterator, stackOfXmlPieces);
        //}
        // Extract the root XML piece (which should be an Element, of course).
        //Piece rootPiece = extractPiece(tokenIterator, stackOfXmlPieces);
        //Piece rootPiece = loadPiece(tokenIterator, stackOfXmlPieces);
        //if ( ! (rootPiece instanceof Element) ) {
        //    throw new RuntimeException(String.format("Expected an Element, but this has come now: %s", rootPiece.toString()));
        //}
        Element rootElement = extractElement(tokenIterator, null);
        xmlDocument.setDocumentElement(rootElement);

        //// Integrity check.
        //// The XML pieces' stack should be empty.
        //if ( ! (stackOfXmlPieces.isEmpty()) ) {
        //    throw new RuntimeException(String.format("There are some unprocessed XML pieces on the stack: %s", stackOfXmlPieces.toString()));
        //}

        // Integrity check.
        // There should be no more tokens to process via the iterator.
        if ( ! ( ! tokenIterator.hasNext() ) ) {
            throw new RuntimeException(String.format("An XML document is supposed to have exactly ONE DOCUMENT ELEMENT."));
        }

        // Done!

    }



    //private Document getDocument(Iterator<Token> tokenIterator) {
    /**
     * Extracts a Document object out of the current token.
     * 
     * @param tokenIterator Token iterator to process tokens.
     * @return Returns a Document object with data from the current token.
     */
    private Document extractDocument(Iterator<Token> tokenIterator) {

        // Create a document element and set up its main attributes.
        // This shall be this method's result.
        Document document = new Document();

        // A processing instruction token is expected here.
        if ( ! (tokenIterator.hasNext()) ) {
            throw new RuntimeException(String.format("A (processing instruction) token is expected to be currently available. But there's no token left."));
        }

        // Access the current token.
        Token currentToken = tokenIterator.next();

        // A processing instruction token is expected here.
        if ( ! (currentToken instanceof ProcessingToken) ) {
            throw new RuntimeException(String.format("A processing instruction token is expected here, but the current token is '%s'.", currentToken.toString()));
        }
        ProcessingToken processingToken = (ProcessingToken) currentToken;

        // The "xml" processing instruction is expected.
        if ( ! ("xml".equals(processingToken.getName())) ) {
            throw new RuntimeException(String.format("The \"xml\" processing instruction token is expected here, but the current token is '%s'.", currentToken.toString()));
        }

        // Check the number of attributes.
        // Load attribute values.
        int attrCount = 0;
        if (processingToken.getAttributesAndValues().containsKey("version")) {
            attrCount++;
            document.setVersion(processingToken.getAttributesAndValues().get("version"));
        }
        if (processingToken.getAttributesAndValues().containsKey("encoding")) {
            attrCount++;
            document.setEncoding(processingToken.getAttributesAndValues().get("encoding"));
        }
        if ( ! (attrCount == processingToken.getAttributesAndValues().size()) ) {
            throw new RuntimeException(String.format("The \"xml\" processing instruction is expected to have two attributes: 'version' and 'encoding'. Whereas this processing instruction token contains these: %s", processingToken.getAttributesAndValues()));
        }

        // Return the result.
        return document;

    }



    // //private Piece extractPiece(Iterator<Token> tokenIterator, Deque<Piece> stackOfXmlPieces) {
    // /**
    //  * Extracts an XML piece from the list of Token objects.
    //  * Uses recursion.
    //  * 
    //  * @param tokenIterator Iterates through tokens.
    //  * @param stackOfXmlPieces Stack of XML pieces that have not been fully processed.
    //  * @return Returns the extracted piece.
    //  */
    // private Piece loadPiece(Iterator<Token> tokenIterator, Deque<Piece> stackOfXmlPieces) {

    //     // Prepare a result.
    //     Piece extractedPiece = null;

    //     // Encapsulate the entire method into an infinite loop.
    //     for (;;) {

    //         // Integrity check.
    //         // Here, we expect a piece.
    //         // Therefore, the list of unprocessed tokens must NOT be empty.
    //         if ( ! (tokenIterator.hasNext()) ) {
    //             throw new RuntimeException(String.format("No more tokens left, but an XML piece is expected!"));
    //         }

    //         // Get the current XML token.
    //         Token currentToken = tokenIterator.next();

    //         // Depending on the type of the token, do the following:
    //         if (currentToken instanceof ProcessingToken) {

    //             // Processing instruction.

    //             // Nonsense. This should not happen in the course of syntax analysis.
    //             throw new RuntimeException(String.format("Processing instructions not expected in this phase: %s", currentToken.toString()));

    //         } else if (currentToken instanceof ElementToken) {

    //             // Element token.

    //             // Access the element token.
    //             ElementToken elementToken = (ElementToken) currentToken;

    //             // Now, it depends on whether this is an opening tag or a closing tag.
    //             if (elementToken.isOpeningTag()) {

    //                 // Opening tag.

    //                 // Create an element XML piece and put it on the stack.
    //                 //Element element = new Element(elementToken.getName());
    //                 Element element = new Element(elementToken);
    //                 stackOfXmlPieces.push(element);

    //                 // Start a method to extract subelements of this element.
    //                 //extractPiece(tokenIterator, stackOfXmlPieces);
    //                 loadPiece(tokenIterator, stackOfXmlPieces);

    //             } else {

    //                 // Closing tag.

    //                 // Access the top element.
    //                 Element elementOnTop = getTopElement(stackOfXmlPieces, false);

    //                 // Integrity check.
    //                 // The element on the stack has the same name as the current closing tag.
    //                 if ( ! (elementOnTop.getName().equals(elementToken.getName())) ) {
    //                     throw new RuntimeException(String.format("The current closing tag is: '%s'. However, we've been expecting: %s", elementToken.toString(), elementOnTop.getName()));
    //                 }

    //                 // OK. We've successfully processed an XML element.
    //                 // Now, just pop it out of the stack.
    //                 stackOfXmlPieces.pop();

    //                 // If there are more elements on the stack, add the element we've just closed to that element's parent.
    //                 Element parentElement = getTopElement(stackOfXmlPieces, true);
    //                 if (parentElement != null) {
    //                     parentElement.addElement(elementOnTop);
    //                 }

    //                 // And return it as a result of this method.
    //                 //return pieceOnTop;
    //                 return elementOnTop;

    //             }

    //         } else if (currentToken instanceof TextToken) {

    //             // Text token.

    //             // Add the text contents to the parent Element token.
    //             TextToken textToken = (TextToken) currentToken;

    //             // Access the top element.
    //             Element elementOnTop = getTopElement(stackOfXmlPieces, false);

    //             // Add the current text token to the parent element.
    //             //elementOnTop.addText(textToken);
    //             Text text = new Text(textToken);
    //             elementOnTop.addText(text);

    //         } else {

    //             // Uknown token.

    //             // This is an integrity issue (syntax error).
    //             throw new RuntimeException(String.format("This token is not supported here: '%s'", currentToken.toString()));

    //         } // END OF the token type selection.

    //     } // END OF the main loop.

    //     //// Return the result.
    //     //return extractedPiece;
    //     //throw new RuntimeException(String.format("Unexpected situation occurred while analyzing the text of an XML document."));

    // }



    // /**
    //  * Gets an Element on the top of the stack of XML pieces.
    //  * Keeps the element on the stack (using peek() not pop()).
    //  * 
    //  * @param stackOfXmlPieces XML pieces' stack.
    //  * @param ignoreIfMissing True :-: ignore the situation where there are no more elements on the stack (and return null), false :-: if the stack is empty, then throw an exception.
    //  * @return Returns the top element from the given stack, or null if the stack is empty (see parameters).
    //  */
    // private Element getTopElement(Deque<Piece> stackOfXmlPieces, boolean ignoreIfMissing) {

    //     // Integrity check.
    //     // When the given stack is empty.
    //     if (stackOfXmlPieces.isEmpty()) {
    //         if (ignoreIfMissing) {
    //             // Just return null, do not throw an exception.
    //             return null;
    //         } else {
    //             // We expected a particular element on the stack. Throw an exception.
    //             throw new RuntimeException(String.format("The stack of XML pieces is empty!"));
    //         }
    //     }

    //     // There should be an element on the top of the XML pieces' stack.
    //     Piece pieceOnTop = stackOfXmlPieces.peek();

    //     // Integrity check.
    //     // The top piece should be an element.
    //     if ( ! (pieceOnTop instanceof Element) ) {
    //         throw new RuntimeException(String.format("This type (%s) of XML piece is not expected here: %s", pieceOnTop.getClass().getName(), pieceOnTop.toString()));
    //     }

    //     // Access the element.
    //     Element elementOnTop = (Element) pieceOnTop;

    //     // return the result.
    //     return elementOnTop;

    // }



    //private Element extractElement(Iterator<Token> tokenIterator, ElementToken openingTag, Element parentElement) {
    //* @param parentElement If the element to be extracted shall have a parent, then this is a reference to that parent, otherwise this shall be null.
    /**
     * Extracts an element from the list of tokens iterated through by a given iterator.
     * 
     * @param tokenIterator Iterator to access items on the list. Forward direction only.
     * @param openingTag If already loaded, this should be the opening tag of the element to extract. If not loaded yet, this should be null.
     * @return Returns an element extracted from the token list.
     */
    private Element extractElement(Iterator<Token> tokenIterator, ElementToken openingTag) {

        // Integrity check.
        // The opening tag token (if provided) should be an OPENING tag indeed.
        if ( openingTag != null ) {
            if ( ! (openingTag.isOpeningTag()) ) {
                throw new RuntimeException(String.format("This token is supposed to be an opening tag: '%s'", openingTag.toString()));
            }
        }

        // Integrity check.
        // If no opening tag has been passed into the method, it must be loaded using the iterator. That is, there must be unprocessed tokens.
        if ( openingTag == null ) {
            if ( ! (tokenIterator.hasNext()) ) {
                throw new RuntimeException(String.format("An opening version of an element token is expected here. But there are no more tokens left."));
            }
        }

        // Access the current token.
        Token currentToken;
        if (openingTag != null) {
            currentToken = openingTag;
        } else {
            currentToken = tokenIterator.next();
        }

        // Integrity check.
        // Handle the situation where the current token is NOT an OPENING ELEMENT token.
        if ( ! ((currentToken instanceof ElementToken) && (((ElementToken) currentToken).isOpeningTag())) ) {
            throw new RuntimeException(String.format("The current token is supposed to be an opening tag: '%s'", currentToken.toString()));
        }

        // Load the opening tag of the element to extract.
        ElementToken openingTagLoaded = (ElementToken) currentToken;

        // Create an element out of the opening version of the element token.
        Element currentElement = new Element(openingTagLoaded);

        // Try to load a closing tag of the element to extract.
        ElementToken closingTagLoaded = findClosingTagAndAddChildren(currentElement, tokenIterator);

        // Integrity check.
        // We should really have a closing tag.
        if ( ! (closingTagLoaded.isClosingTag()) ) {
            throw new RuntimeException(String.format("This token is supposed to be a closing tag: '%s'", closingTagLoaded.toString()));
        }

        // Integrity check.
        // Check the names of the opening tag and the closing tag. They should match.
        // Just in case, check the name of the extracted element as well.
        if ( ! (
            (closingTagLoaded.getName().equals(openingTagLoaded.getName()))
            &&
            (currentElement.getName().equals(openingTagLoaded.getName()))
        ) ) {
            throw new RuntimeException(String.format("Names in the element, opening tag and closing tag must match. Element name: '%s', Opening tag name: '%s', Closing tag name: '%s'", currentElement.getName(), openingTagLoaded.getName(), closingTagLoaded.getName()));
        }

        // Return the result.
        return currentElement;

    }



    /**
     * Tries to find a closing tag for a given element, adding child pieces to the element on the fly.
     * 
     * @param currentElement Element currently being extracted.
     * @param tokenIterator Iterator to process tokens.
     * @return Returns the first closing tag encountered. It is the responsibility of the caller to check the closing tag against the opening one.
     */
    private ElementToken findClosingTagAndAddChildren(Element currentElement, Iterator<Token> tokenIterator) {

        // Loop.
        for (;;) {

            // Integrity check.
            // A token is expected.
            if ( ! (tokenIterator.hasNext()) ) {
                throw new RuntimeException(String.format("No more tokens available to parse the XML document. The syntax analysis forced to stop."));
            }

            // Load a token.
            Token currentToken = tokenIterator.next();

            // If the token is a CLOSING TAG version of an ELEMENT TOKEN, then just return the token back to the caller.
            // Do not check whether the closing tag matches the current element (its opening tag). This is to be done by the caller.
            if (
                (currentToken instanceof ElementToken)
                &&
                (((ElementToken) currentToken).isClosingTag())
            ) {
                ElementToken closingTag = (ElementToken) currentToken;
                return closingTag;
            }

            // If the token is a TEXT TOKEN, then just add the corresponding text as a child to the given element.
            if (currentToken instanceof TextToken) {
                TextToken textToken = (TextToken) currentToken;
                currentElement.addTextFromTextToken(textToken);
                // Iterate.
                continue;
            }

            // If the token is an OPENING TAG version of an ELEMENT TOKEN, then try to extract the element using the extractElement() method.
            // Add the extracted element as a child to the given element.
            if (
                (currentToken instanceof ElementToken)
                &&
                (((ElementToken) currentToken).isOpeningTag())
            ) {
                ElementToken openingTag = (ElementToken) currentToken;
                //Element childElement = extractElement(tokenIterator, openingTag, currentElement);
                Element childElement = extractElement(tokenIterator, openingTag);
                currentElement.addElement(childElement);
                // Iterate.
                continue;
            }

            // Integrity check.
            // Other cases mean an error!
            throw new RuntimeException(String.format("This token is not expected here: '%s'", currentToken.toString()));

        }

    }



    // /**
    //  * Extracts an element from the list of tokens iterated through by a given iterator.
    //  * 
    //  * @param tokenIterator Iterator to access items on the list. Forward direction only.
    //  * @param parentElement If the element to be extracted shall have a parent, then this is a reference to that parent, otherwise this shall be null.
    //  * @return Returns an element extracted from the token list.
    //  */
    // private Element extractElement(Iterator<Token> tokenIterator, ElementToken openingTag, Element parentElement) {

    //     // TODOM: Integrity check.
    //     // The opening tag token should be an OPENING tag indeed.

    //     // Access the current token.
    //     Token currentToken;
    //     if (openingTag != null) {
    //         currentToken = openingTag;
    //     } else {
    //         currentToken = tokenIterator.next();
    //     }

    //     // TODOM: Handle the situation where the current token is NOT an OPENING ELEMENT token.

    //     // TODOM: Handle the situation where there are no more tokens accessible through the iterator.
    //     // Here we have two legal situtaions (any other situation means an ERROR):
    //     // (i) Either there is no opening tag and no parent element passed into the method and there IS a token available through the iterator.
    //     // (ii) Or there IS an opening tag and there IS a parent element.
    //     //      In that case we do not need a token from the iterator as an opening tag of the element to extract,
    //     //      but we'll need another token soon, though, of course, to CLOSE the element.

    //     // Load the opening tag of the element to extract.
    //     ElementToken openingTagLoaded = (ElementToken) currentToken;

    //     // Try to find the closing tag of the element. Use a loop.
    //     for (;;) {

    //         // TODOM: Handle the situation where there are no more tokens accessible through the iterator.

    //         // Load the next token.
    //         currentToken = tokenIterator.next();

    //         // If the token is a CLOSING ELEMENT token of the ELEMENT being EXTRACTED, then this is the desirable case.
    //         // Add the complete element to the parent if any.
    //         // We're done.
    //         // Return the result.
    //         if (
    //             (currentToken instanceof ElementToken)
    //             &&
    //             (((ElementToken) currentToken).isClosingTag())
    //             &&
    //             (((ElementToken) currentToken).getName().equals(openingTagLoaded.getName()))
    //         ) {
    //             ElementToken closingTagLoaded = (ElementToken) currentToken;
    //             Element extractedElement = new Element(openingTagLoaded);
    //             if (parentElement != null) {
    //                 parentElement.addElement(extractedElement);
    //             }
    //             return extractedElement;
    //         }

    //         // If the token is a CLOSING ELEMENT token of ANOTHER ELEMENT, then this is an error!
    //         // TODOM: Report the error.

    //         // If the token is an OPENING ELEMENT token, then it must be a subelement of the element being extracted.
    //         // Use recursion. It is necessary to pass the token just read into the recursive call,
    //         // otherwise the processing at the lower level would not have the opening token available.
    //         if (
    //             (currentToken instanceof ElementToken)
    //             &&
    //             (((ElementToken) currentToken).isOpeningTag())
    //         ) {
    //             ElementToken openingTagForSubelementLoaded = (ElementToken) currentToken;
    //             return extractedElement;
    //         }

    //         // If the token is a TEXT token and there is NO PARENT element, then this is an error!
    //         // TODOM: Report the error.

    //         // If the token is a TEXT token and there IS a parent element, then add this text to the parent.
    //         // Then iterate.
    //         // (Continue looking for a closing tag of the element currently being extracted.)
    //         if (currentToken instanceof TextToken) {
    //             TextToken textTokenLoaded = (TextToken) currentToken;
    //             parentElement.addTextFromTextToken(textTokenLoaded);
    //         }

    //         // If the token is a PROCESSING instruction token, then this is an error!
    //         // TODOM: Report the error.

    //         // TODOM: The processing is SUPPOSED to NOT reach up here.

    //     }

    //     // TODOM: Get rid of this "fake" return once implementation of this method is finished.
    //     return null;

    // }



    /**
     * Prints the given XML document to a PrintWriter
     * and once the "printing" is done, the method takes the "printed" text
     * and converts it to text lines.
     */
    private void printXmlToWriterAndConvertToLines() {

        // Print XML to a TextWriter.

        // Open a TextWriter.
        StringWriter stringWriter = new StringWriter();
        printWriter = new PrintWriter(stringWriter);

        // Print the initial processing instruction to the writer.
        printInitialProcessingInstruction();

        // Print the rest of XML to the writer.
        printXmlPiece(xmlDocument.getDocumentElement(), 0, false, false);

        // Extract printed data.
        String printedText = printWriter.toString();
        lines = convertToLines(printedText);

        // Close the writer.
        printWriter.close();

    }



    /**
     * Converts a given string to a list of lines.
     * 
     * @param allLinesInOne String to convert to lines.
     * @return Returns a list of lines taken from the given string.
     */
    private List<String> convertToLines(String allLinesInOne) {

        // Prepare a list of strings for the result of this method.
        List<String> linesRead = new ArrayList<String>();

        // Open a StringReader.
        StringReader stringReader = new StringReader(allLinesInOne);
        Scanner scanner = new Scanner(stringReader);

        // Read lines one by one from the reader. Store the lines to the list.
        while (scanner.hasNextLine()) {

            // Read a line and store it in the resulting list.
            String lineRead = scanner.nextLine();
            linesRead.add(lineRead);

        }

        // Close the reader.
        //stringReader.close();
        scanner.close();

        // Return the result.
        return linesRead;

    }



    /**
     * Prints the initial processing instruction to the open PrintWriter.
     */
    private void printInitialProcessingInstruction() {
        //printWriter.println(String.format("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"));
        printWriter.println(String.format("<?xml version=\"%s\" encoding=\"%s\"?>", xmlDocument.getVersion(), xmlDocument.getEncoding()));
    }



    /**
     * Prints a given XML piece to the open PrintWriter.
     * 
     * @param piece XML piece to print.
     * @param indentationLevel Number of spaces to make on each line before printing useful data.
     * @param noNewlines True :-: do NOT print any newlines, false :-: do perform println (with newline characters at the end).
     * @param noIndents True :-: do NOT apply indentation at the beginning of printing, false :-: do APPLY the indentation.
     */
    private void printXmlPiece(Piece piece, int indentationLevel, boolean noNewlines, boolean noIndents) {

        // Prepare indenting.
        String indenting = repeatString(" ", indentationLevel);

        // Print out the given XML piece using the given indentation level.
        if (piece instanceof Text) {

            // A Text XML piece.
            Text text = (Text) piece;
            printIndents((! noIndents), indenting);
            printWriter.print(text.getPlaintext());
            printNewline(! noNewlines);

        } else if (piece instanceof Element) {

            // An Element XML piece.
            Element element = (Element) piece;

            // Decide on "compact" printing.
            if ( (element.getSubelements().size() == 0) && (element.getSubpieces().size() <= 1) ) {

                // If the current element has no subelements and does have one subpiece (i.e. a text piece) at the most,
                // then print out the element and its contents in a compact mode.

                // Print out the opening tag.
                printIndents((! noIndents), indenting);
                printWriter.print("<" + element.getName() + xmlAttributesToString(element.getAttributes()) + ">");

                // Print out the subelements.
                for (Piece childPiece : element.getSubpieces()) {
                    //printXmlPiece(childPiece, indentationLevel + 2, true, true);
                    printXmlPiece(childPiece, indentationLevel + indentationStep, true, true);
                }

                // Print out the closing tag.
                printWriter.print("</" + element.getName() + ">");
                printNewline(! noNewlines);

            } else {

                // Standard behaviour.

                // Print out the opening tag.
                printIndents((! noIndents), indenting);
                printWriter.print("<" + element.getName() + xmlAttributesToString(element.getAttributes()) + ">");
                printNewline(! noNewlines);

                // Print out the subelements.
                for (Piece childPiece : element.getSubpieces()) {
                    //printXmlPiece(childPiece, indentationLevel + 2, noNewlines, noIndents);
                    printXmlPiece(childPiece, indentationLevel + indentationStep, noNewlines, noIndents);
                }

                // Print out the closing tag.
                printIndents((! noIndents), indenting);
                printWriter.print("</" + element.getName() + ">");
                printNewline(! noNewlines);

            }

        } else {

            // Error.
            throw new RuntimeException(String.format("This type of XML piece is not supported here: %s", piece.toString()));

        }

    }



    /**
     * Repeats a given string a given number of times.
     * 
     * @param s String to repeat.
     * @param repeatCount Number of repetitions of the given string.
     * @return Returns a string that is a multiple concatenation of the given string. The given string gets repeated the given number of times.
     */
    private static String repeatString(String s, int repeatCount) {
        return new String(new char[repeatCount]).replace("\0", s);
    }



    /**
     * Transforms a given list of XML attributes into a single string suitable for printing the XML contents.
     * 
     * @param attributes XML attributes to convert to a string.
     * @return Returns a string made out of the given list of attributes.
     */
    private String xmlAttributesToString(List<Attribute> attributes) {
        return attributes.stream()
            // Here we've got a Stream<Attribute>.
            .map(attr -> String.format("%s=\"%s\"", attr.getName(), attr.getValue()))
            // Here we've got a Stream<String>.
            .reduce((acc, x) -> acc + " " + x)
            // Here we've got an Optional<String>.
            .map(attrs -> " " + attrs)
            // Here we've got an Optional<String>.
            .orElse("")
        ;
    }



    /**
     * Prints indentation to the open PrintWriter if required.
     * 
     * @param doIndents True :-: DO indentation, false :-: do NOT any indentation.
     * @param indenting Indentation sequence (typically a few spaces).
     */
    private void printIndents(boolean doIndents, String indenting) {
        if (doIndents) {
            printWriter.print(indenting);
        }
    }



    /**
     * Sends a newline to the open PrintWriter if required.
     * 
     * @param doNewline True :-: DO send the newline character to the PrintWriter, false :-: do NOT send any newline characters.
     */
    private void printNewline(boolean doNewline) {
        if (doNewline) {
            printWriter.println();
        }
    }



}
