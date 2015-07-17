package de.kaojo.ejb.exceptions;

/**
 *
 * @author jwinter
 */
public class ChatManagerException extends Exception {

    public ChatManagerException() {
    }

    public ChatManagerException(String message) {
        super(message);
    }

    public ChatManagerException(Throwable cause) {
        super(cause);
    }

    public ChatManagerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ChatManagerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
