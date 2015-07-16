/*
 * Diese Software ist Eigentum von Julian Winter
 * Alle Rechte sind vorbehalten.
 * Copyright 2015.
 */
package de.kaojo.ejb;

import de.kaojo.chat.ChatRoom;
import de.kaojo.ejb.dto.interfaces.ChatRoomChatRequest;
import de.kaojo.ejb.dto.interfaces.ChatRoomNameChatRequest;
import de.kaojo.ejb.dto.interfaces.MessageChatRequest;
import de.kaojo.ejb.dto.interfaces.UserIdChatRequest;
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

    public boolean addUserToChatRoom(ChatRoomChatRequest chatRequest);

    public boolean removeUserFromChatRoom(ChatRoomChatRequest chatRequest);

    public boolean inviteUserToChatRoom(ChatRoomChatRequest chatRequest);

    public boolean addAdminToChatRoom(ChatRoomChatRequest chatRequest);

    public boolean deleteChatRoom(ChatRoomChatRequest chatRequest);

    public boolean receiveMessage(MessageChatRequest chatRequest);

    public boolean createUserToken(UserIdChatRequest chatRequest);

    public String getDisplayNameFromToken(UserTokenChatRequest chatRequest);

    public Long getAccountIdFromToken(UserTokenChatRequest chatRequest);

    public Long getChatRoomIdFromChatRoomName(ChatRoomNameChatRequest chatRequest);

}
