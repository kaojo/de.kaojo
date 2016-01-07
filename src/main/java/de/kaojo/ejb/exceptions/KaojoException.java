/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.kaojo.ejb.exceptions;

/**
 *
 * @author julian
 */
public class KaojoException extends Exception {

    public KaojoException() {
    }

    public KaojoException(String message) {
        super(message);
    }

    public KaojoException(String message, Throwable cause) {
        super(message, cause);
    }

    public KaojoException(Throwable cause) {
        super(cause);
    }

    public KaojoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
