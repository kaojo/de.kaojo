/*
 * Diese Software ist Eigentum von Julian Winter
 * Alle Rechte sind vorbehalten.
 * Copyright 2015.
 */
package de.kaojo.context.user;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;

/**
 *
 * @author julian
 */
@DefaultUser
@SessionScoped
public class UserImpl implements User, Serializable {

    String userId;
    String firtName;
    String lastName;
    String displayName;

    public UserImpl() {

    }

    @Override
    public String getUserId() {
        return userId;
    }

    /**
     *
     * @return
     */
    @Override
    public String getFirtName() {
        return firtName;
    }

    /**
     *
     * @param firtName
     */
    @Override
    public void setFirtName(String firtName) {
        this.firtName = firtName;
    }

    /**
     *
     * @return
     */
    @Override
    public String getLastName() {
        return lastName;
    }

    /**
     *
     * @param lastName
     */
    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     *
     * @return
     */
    @Override
    public String getDisplayName() {
        return displayName;
    }

    /**
     *
     * @param displayName
     */
    @Override
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public User build(String firstname, String lastName, String displayName) {
        this.displayName = displayName;
        this.firtName = firstname;
        this.lastName = lastName;

        return this;
    }

}
