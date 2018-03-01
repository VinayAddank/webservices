package org.rta.core.exception;

/**
 * This exception is thrown to validate engine number.
 * 
 * @author rahul.sharma
 *
 */
public class InvalidEngineNumberException extends Exception {

    private static final long serialVersionUID = -3252147583589515049L;

    public InvalidEngineNumberException() {
        super();
    }

    public InvalidEngineNumberException(String message) {
        super(message);
    }

}
