/*
 * Diese Software ist Eigentum von Julian Winter
 * Alle Rechte sind vorbehalten.
 * Copyright 2015.
 */
package de.kaojo.ejb;

import de.kaojo.chat.model.ChatRoom;
import de.kaojo.chat.model.Message;
import de.kaojo.ejb.dto.interfaces.ChatRoomChatRequest;
import de.kaojo.ejb.dto.interfaces.ChatRoomNameChatRequest;
import de.kaojo.ejb.dto.interfaces.MessageChatRequest;
import de.kaojo.ejb.dto.interfaces.AccountIdChatRequest;
import de.kaojo.ejb.dto.interfaces.NewChatRoomChatRequest;
import de.kaojo.ejb.dto.interfaces.UserNameChatRequest;
import de.kaojo.ejb.exceptions.ChatManagerException;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author julian.winter
 */
@Local
public interface ChatManager {

    public List<ChatRoom> getAllChatRooms(AccountIdChatRequest chatRequest) throws ChatManagerException;

    public List<ChatRoom> getChatRooms(AccountIdChatRequest chatRequest) throws ChatManagerException;

    public List<ChatRoom> getAccessibleChatRooms(AccountIdChatRequest chatRequest) throws ChatManagerException;

    public boolean addUserToChatRoom(ChatRoomChatRequest chatRequest) throws ChatManagerException;

    public boolean removeUserFromChatRoom(ChatRoomChatRequest chatRequest) throws ChatManagerException;

    public boolean inviteUserToChatRoom(ChatRoomChatRequest chatRequest) throws ChatManagerException;

    public boolean addAdminToChatRoom(ChatRoomChatRequest chatRequest) throws ChatManagerException;

    public boolean deleteChatRoom(ChatRoomChatRequest chatRequest) throws ChatManagerException;

    public boolean createNewChatRoom(NewChatRoomChatRequest chatRequest) throws ChatManagerException;

    public boolean receiveMessage(MessageChatRequest chatRequest) throws ChatManagerException;

    public Long getChatRoomIdFromChatRoomName(ChatRoomNameChatRequest chatRequest) throws ChatManagerException;

    public List<Message> getOldMessages(ChatRoomNameChatRequest chatRequest) throws ChatManagerException;

    public ChatRoom getChatRoomByChatRoomId(ChatRoomChatRequest chatRequest) throws ChatManagerException;

    public ChatRoom getChatRoomByChatRoomName(ChatRoomNameChatRequest chatRequest) throws ChatManagerException;
}
