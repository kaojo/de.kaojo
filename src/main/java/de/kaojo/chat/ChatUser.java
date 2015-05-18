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

    private String userName;

    public ChatUser() {
    }

    public ChatUser(String userName, String iDisplayName) {
        this.displayName = iDisplayName;
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

}
