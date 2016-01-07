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
public class UserManagerException extends KaojoException {

    public UserManagerException() {
    }

    public UserManagerException(String message) {
        super(message);
    }

    public UserManagerException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserManagerException(Throwable cause) {
        super(cause);
    }

    public UserManagerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
