/*
 * Diese Software ist Eigentum von Julian Winter
 * Alle Rechte sind vorbehalten.
 * Copyright 2015.
 */
package de.winter.kaojo.beans.app;

import de.winter.kaojo.beans.user.User;
import de.winter.kaojo.chat.ChatRoom;
import java.util.List;

/**
 *
 * @author julian.winter
 */
public interface ChatManager {
    
    public List<ChatRoom> getChatRooms(User user);
    
    public void addChatRoom(ChatRoom chatRoom);
    
}
