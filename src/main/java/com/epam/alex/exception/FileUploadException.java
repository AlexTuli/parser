package com.epam.alex.exception;

/**
 * Created on 2/22/2016.
 *
 * @author Bocharnikov Alexander
 */
public class FileUploadException extends Exception {

    public FileUploadException() {
        super();
    }

    public FileUploadException(String message) {
        super(message);
    }

    public FileUploadException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileUploadException(Throwable cause) {
        super(cause);
    }
}
