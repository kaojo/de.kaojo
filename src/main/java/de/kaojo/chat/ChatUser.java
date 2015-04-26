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
public class ChatUser {

    private String displayName;

    private String userId;

    public ChatUser() {
    }

    public ChatUser(String iUserId, String iDisplayName) {
        this.displayName = iDisplayName;
        this.userId = iUserId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

}
