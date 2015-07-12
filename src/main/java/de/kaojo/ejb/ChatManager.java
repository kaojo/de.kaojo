/*
 * Diese Software ist Eigentum von Julian Winter
 * Alle Rechte sind vorbehalten.
 * Copyright 2015.
 */
package de.kaojo.ejb;

import de.kaojo.chat.ChatRoom;
import de.kaojo.ejb.dto.interfaces.ChatRoomChatRequest;
import de.kaojo.ejb.dto.interfaces.MessageChatRequest;
import de.kaojo.ejb.dto.interfaces.UserIdChatRequest;
import de.kaojo.ejb.dto.interfaces.UserNameChatRequest;
import de.kaojo.ejb.dto.interfaces.UserTokenChatRequest;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author julian.winter
 */
@Local
public interface ChatManager {

    public List<ChatRoom> getChatRooms(UserIdChatRequest chatRequest);

    public List<ChatRoom> getAccessibleChatRooms(UserIdChatRequest chatRequest);

    public void userjoined(ChatRoomChatRequest chatRequest);

    public void userleft(ChatRoomChatRequest chatRequest);
    
    public void receiveMessage(MessageChatRequest chatRequest);
    
    public void createUserToken(UserNameChatRequest chatRequest);
    
    public String getUserFromToken(UserTokenChatRequest chatRequest);

}
