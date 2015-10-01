/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.kaojo.chat.model;

/**
 *
 * @author julian
 */
public class ChatUserMinimalImpl implements ChatUser {

    private String displayName;

    /**
     * Get the value of displayName
     *
     * @return the value of displayName
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Set the value of displayName
     *
     * @param displayName new value of displayName
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public Long getId() {
        return null;
    }

    @Override
    public boolean isJoined() {
        return false;
    }

}
