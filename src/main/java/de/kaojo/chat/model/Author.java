/*
 * Diese Software ist Eigentum von Julian Winter
 * Alle Rechte sind vorbehalten.
 * Copyright 2015.
 */
package de.kaojo.chat.model;

/**
 *
 * @author julian
 */
public class Author {

    private final String userName;
    private final String displayName;

    public String getUserName() {
        return userName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Author(String userName, String displayName) {
        this.displayName = displayName;
        this.userName = userName;
    }

}
