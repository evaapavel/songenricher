package cz.jollysoft.songenricher;



import java.nio.file.Path;
import java.nio.file.Paths;

import cz.jollysoft.songenricher.dataholders.Ensemble;
import cz.jollysoft.songenricher.processors.EnsembleLoader;
import cz.jollysoft.songenricher.transformers.Xmler;
import cz.jollysoft.songenricher.transformers.xml.Token;



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

        // Give debug info.
        System.out.println("Done!");

    }



}