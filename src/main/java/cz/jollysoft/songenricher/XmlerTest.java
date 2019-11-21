package cz.jollysoft.songenricher;



import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import cz.jollysoft.songenricher.dataholders.Ensemble;
import cz.jollysoft.songenricher.processors.EnsembleLoader;
import cz.jollysoft.songenricher.transformers.Xmler;
import cz.jollysoft.songenricher.transformers.xml.Token;
import cz.jollysoft.songenricher.xmlpieces.Attribute;
import cz.jollysoft.songenricher.xmlpieces.Element;
import cz.jollysoft.songenricher.xmlpieces.Piece;
import cz.jollysoft.songenricher.xmlpieces.Text;



/**
 * Tests loading a file's contents into a memory structure
 * and pre-parsing the file contents, i.e. transforming them into tokens.
 * 
 * @author Pavel Foltyn
 */
public class XmlerTest {



    /**
     * Main application entry point.
     * 
     * @param args Command line parameters.
     */
    public static void main(String[] args) {

        // Give debug info.
        System.out.println("Starting...");

        // Prepare a path to the file.
        //String pathToFile = "src/main/resources/test.txt";
        //String pathToFile = "src/main/resources/testLastLineWithoutEOLN.txt";
        String pathToFile = "src/main/resources/Biblicky_tyden_2001/29. Na kolenou";

        // Prepare a Path object.
        Path path = Paths.get(pathToFile);

        // Load the contents of the file into an Ensemble object.
        EnsembleLoader ensembleLoader = new EnsembleLoader();
        Ensemble ensemble = ensembleLoader.apply(path);

        // Display the file contents.
        for (String line : ensemble.getLines()) {
            System.out.println(line);
        }

        // Test the first phase of the XML parser.
        // Tokenize the lines of the file.
        Xmler xmler = new Xmler(ensemble.getLines());
        xmler.parse();

        // Display tokens produced out of the file.
        for (Token token : xmler.getXmlTokens()) {
            System.out.println(token);
        }

        // Print out the XML object structure hierarchically.
        Element rootElement = xmler.getXmlDocument().getDocumentElement();
        //printXmlPiece(rootElement, 0, false);
        printXmlPiece(rootElement);

        // Give debug info.
        System.out.println("Done!");

    }



    private static void printXmlPiece(Piece piece) {
        printXmlPiece(piece, 0, false, false);
    }



    /**
     * Prints a given XML piece to stdout.
     * 
     * @param piece XML piece to print.
     * @param indentationLevel Number of spaces to make on each line before printing useful data.
     * @param noNewLines True :-: do NOT print any new lines, false :-: do perform println (with new line characters at the end).
     * @param noIndents True :-: do NOT apply indentation at the beginning of printing, false :-: do APPLY the indentation.
     */
    private static void printXmlPiece(Piece piece, int indentationLevel, boolean noNewLines, boolean noIndents) {

        // Prepare indenting.
        String indenting = repeatString(" ", indentationLevel);

        // Print out the given XML piece using the given indentation level.
        if (piece instanceof Text) {

            // A Text XML piece.
            Text text = (Text) piece;
            //System.out.println(indenting + text.getPlaintext());
            //if ( ! noIndents ) {
            //    System.out.print(indenting);
            //}
            //System.out.print(text.getPlaintext());
            //if ( ! noNewLines ) {
            //    System.out.println();
            //}
            printIndents((! noIndents), indenting);
            System.out.print(text.getPlaintext());
            printNewLine(! noNewLines);

        } else if (piece instanceof Element) {

            // An Element XML piece.
            Element element = (Element) piece;

            // Decide on "compact" printing.
            if ( (element.getSubelements().size() == 0) && (element.getSubpieces().size() <= 1) ) {

                // If the current element has no subelements and does have one subpiece (i.e. a text piece) at the most,
                // then print out the element and its contents in a compact mode.

                // Print out the opening tag.
                printIndents((! noIndents), indenting);
                System.out.print("<" + element.getName() + xmlAttributesToString(element.getAttributes()) + ">");

                // Print out the subelements.
                for (Piece childPiece : element.getSubpieces()) {
                    printXmlPiece(childPiece, indentationLevel + 2, true, true);
                }

                // Print out the closing tag.
                System.out.print("</" + element.getName() + ">");
                printNewLine(! noNewLines);

            } else {

                // Standard behaviour.

                // Print out the opening tag.
                //System.out.println(indenting + "<" + element.getName() + xmlAttributesToString(element.getAttributes()) + ">");
                printIndents((! noIndents), indenting);
                System.out.print("<" + element.getName() + xmlAttributesToString(element.getAttributes()) + ">");
                printNewLine(! noNewLines);

                // Print out the subelements.
                for (Piece childPiece : element.getSubpieces()) {
                    printXmlPiece(childPiece, indentationLevel + 2, noNewLines, noIndents);
                }

                // Print out the closing tag.
                //System.out.println(indenting + "</" + element.getName() + ">");
                printIndents((! noIndents), indenting);
                System.out.print("</" + element.getName() + ">");
                printNewLine(! noNewLines);

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
    private static String xmlAttributesToString(List<Attribute> attributes) {
        return attributes.stream()
            // Here we've got a Stream<Attribute>.
            .map(attr -> String.format("%s=\"%s\"", attr.getName(), attr.getValue()))
            // Here we've got a Stream<String>.
            //.reduce("", String::concat)
            //.reduce(String::concat)
            .reduce((acc, x) -> acc + " " + x)
            // Here we've got an Optional<String>.
            //.orElseGet(() -> "")
            //.orElse("")
            //.map(attrs -> " " + attrs + " ")
            .map(attrs -> " " + attrs)
            // Here we've got an Optional<String>.
            .orElse("")
        ;
    }



    /**
     * Prints indentation to stdout if required.
     * 
     * @param doIndents True :-: DO indentation, false :-: do NOT any indentation.
     * @param indenting Indentation sequence (typically a few spaces).
     */
    private static void printIndents(boolean doIndents, String indenting) {
        if (doIndents) {
            System.out.print(indenting);
        }
    }



    /**
     * Sends a new-line to stdout if required.
     * 
     * @param doNewLine True :-: DO send the new-line character to stdout, false :-: do NOT send any new-line characters.
     */
    private static void printNewLine(boolean doNewLine) {
        if (doNewLine) {
            System.out.println();
        }
    }



}
