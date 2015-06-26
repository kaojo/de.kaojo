/*
 * Diese Software ist Eigentum von Julian Winter
 * Alle Rechte sind vorbehalten.
 * Copyright 2015.
 */
package de.kaojo.beans.app;

import de.kaojo.context.user.User;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;

/**
 *
 * @author julian.winter
 */
@ApplicationScoped
@DefaultChatManager
public class ChatManagerImpl implements ChatManager {

    @Override
    public void userjoined(ChatEvent chatEvent) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void userleft(ChatEvent chatEvent) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> getChatRooms(User user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> getAccessibleChatRooms(User user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
