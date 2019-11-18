package cz.jollysoft.songenricher.exceptions;



/**
 * Represents an exception that might be thrown during stream operations on a file, its contents or a song.
 * 
 * @author Pavel Foltyn
 */
public class PipelineException extends RuntimeException {



    /** Version id. This is used in classes that implement Serializable. */
    private static final long serialVersionUID = 1819743212857617696L;



    /**
     * Constructor.
     */
    public PipelineException() {
        super();
    }



    /**
     * Constructor.
     * 
     * @param message A message explaining the exception.
     */
    public PipelineException(String message) {
        super(message);
    }



    /**
     * Constructor.
     * 
     * @param message A message explaing the exception.
     * @param cause Original cause of the exception.
     */
    public PipelineException(String message, Throwable cause) {
        super(message, cause);
    }



    /**
     * Constructor.
     * 
     * @param cause Original cause of the exception.
     */
    public PipelineException(Throwable cause) {
        super(cause);
    }



}