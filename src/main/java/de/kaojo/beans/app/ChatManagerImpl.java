/*
 * Diese Software ist Eigentum von Julian Winter
 * Alle Rechte sind vorbehalten.
 * Copyright 2015.
 */
package de.kaojo.beans.app;

import de.kaojo.context.user.User;
import de.kaojo.chat.ChatRoom;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;

/**
 *
 * @author julian.winter
 */
@ApplicationScoped
public class ChatManagerImpl implements ChatManager {

    @Override
    public List<ChatRoom> getChatRooms(User user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addChatRoom(ChatRoom chatRoom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
