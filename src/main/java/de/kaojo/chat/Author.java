/*
 * Diese Software ist Eigentum von Julian Winter
 * Alle Rechte sind vorbehalten.
 * Copyright 2015.
 */
package de.kaojo.chat;

/**
 *
 * @author julian
 */
public class Author {

    private final String userId;
    private final String displayName;

    public String getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Author(String userId, String displayName) {
        this.displayName = displayName;
        this.userId = userId;
    }

}
