package com.epam.alex.exception;

/**
 * Created on 2/22/2016.
 *
 * @author Bocharnikov Alexander
 */
public class RequestException extends RuntimeException {

    public RequestException() {
        super();
    }

    public RequestException(String message) {
        super(message);
    }

    public RequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequestException(Throwable cause) {
        super(cause);
    }
}
