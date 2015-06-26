/*
 * Diese Software ist Eigentum von Julian Winter
 * Alle Rechte sind vorbehalten.
 * Copyright 2015.
 */
package de.kaojo.chat;

import javax.websocket.Session;

/**
 *
 * @author julian
 */
public interface ChatUser {

    public String getName();

    public Session getSession();

}
