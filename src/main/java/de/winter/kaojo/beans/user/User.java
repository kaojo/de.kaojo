/*
 * Diese Software ist Eigentum von Julian Winter
 * Alle Rechte sind vorbehalten.
 * Copyright 2015.
 */
package de.winter.kaojo.beans.user;

/**
 *
 * @author julian.winter
 */
public interface User {

    String getUserId();

    void setUserId(String userId);

    String getFirtName();

    void setFirtName(String firstName);

    String getLastName();

    void setLastName(String lastName);

    String getDisplayName();

    void setDisplayName(String displayName);

}
