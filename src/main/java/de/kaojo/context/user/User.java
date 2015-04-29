/*
 * Diese Software ist Eigentum von Julian Winter
 * Alle Rechte sind vorbehalten.
 * Copyright 2015.
 */
package de.kaojo.context.user;

/**
 *
 * @author julian.winter
 */
public interface User {

    public String getUserId();

    public String getFirtName();

    public void setFirtName(String firstName);

    public String getLastName();

    public void setLastName(String lastName);

    public String getDisplayName();

    public void setDisplayName(String displayName);

    public User build(String firstname, String lastName, String displayName);

}
