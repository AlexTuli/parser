package com.epam.alex.exception;

/**
 * Created on 2/21/2016.
 *
 * @author Alexander Bocharnikov
 */
public class ParserException extends Exception {

    public ParserException() {
        super();
    }

    public ParserException(String message) {
        super(message);
    }

    public ParserException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParserException(Throwable cause) {
        super(cause);
    }
}
