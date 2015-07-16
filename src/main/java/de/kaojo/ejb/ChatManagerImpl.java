/*
 * Diese Software ist Eigentum von Julian Winter
 * Alle Rechte sind vorbehalten.
 * Copyright 2015.
 */
package de.kaojo.ejb;

import de.kaojo.chat.model.ChatRoom;
import de.kaojo.chat.model.ChatRoomImpl;
import de.kaojo.chat.model.ChatUser;
import de.kaojo.chat.model.ChatUserImpl;
import de.kaojo.chat.model.Message;
import de.kaojo.ejb.dto.interfaces.ChatRoomChatRequest;
import de.kaojo.ejb.dto.interfaces.ChatRoomNameChatRequest;
import de.kaojo.ejb.dto.interfaces.MessageChatRequest;
import de.kaojo.ejb.dto.interfaces.UserIdChatRequest;
import de.kaojo.ejb.dto.interfaces.UserTokenChatRequest;
import de.kaojo.persistence.entities.AccountEntity;
import de.kaojo.persistence.entities.ChatRoomEntity;
import de.kaojo.persistence.entities.MessageEntity;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author julian.winter
 */
@Stateless
public class ChatManagerImpl implements ChatManager {

    private static final String DEFAULT_CHAT_ROOM = "kaojo";

    @PersistenceContext(unitName = "postgres")
    EntityManager em;

    @Override
    public List<ChatRoom> getChatRooms(UserIdChatRequest chatRequest) {
        List<ChatRoomEntity> memberedChatRooms = getMemberedChatRooms(chatRequest);
        return mapChatRooms(memberedChatRooms);
    }

    @Override
    public List<ChatRoom> getAccessibleChatRooms(UserIdChatRequest chatRequest) {
        List<ChatRoomEntity> invitedChatRooms = getInvitedChatRooms(chatRequest);
        List<ChatRoomEntity> publicChatRooms = getPublicChatRooms(chatRequest);
        if (!publicChatRooms.isEmpty()) {
            invitedChatRooms.addAll(publicChatRooms);
        }
        return mapChatRooms(invitedChatRooms);
    }

    @Override
    public boolean addUserToChatRoom(ChatRoomChatRequest chatRequest) {
        Long chatRoomId = chatRequest.getChatRoomId();
        Long userId = chatRequest.getUserId();
        AccountEntity accountEntity = em.find(AccountEntity.class, userId);
        ChatRoomEntity chatRoomEntity = em.find(ChatRoomEntity.class, chatRoomId);
        chatRoomEntity.getMembers().add(accountEntity);
        return true;
    }

    private List<ChatRoomEntity> getMemberedChatRooms(UserIdChatRequest chatRequest) {
        Long userId = chatRequest.getUserId();
        Query query = em.createQuery("SELECT DISTINCT cr FROM ChatRoomEntity cr JOIN cr.members m WHERE m.id = :userId ");
        query.setParameter("userId", userId);
        return new ArrayList<>(query.getResultList());
    }

    private List<ChatRoomEntity> getInvitedChatRooms(UserIdChatRequest chatRequest) {
        Long userId = chatRequest.getUserId();
        Query query = em.createQuery("SELECT DISTINCT cr FROM ChatRoomEntity cr JOIN cr.invites i WHERE i.id = :userId AND :userId <> ALL (SELECT m.id FROM cr.members m)");
        query.setParameter("userId", userId);
        return new ArrayList<>(query.getResultList());
    }

    private List<ChatRoomEntity> getPublicChatRooms(UserIdChatRequest chatRequest) {
        Long userId = chatRequest.getUserId();
        Query query = em.createQuery("SELECT DISTINCT cr FROM ChatRoomEntity cr WHERE cr.unrestricted = true AND :userId <> ALL (SELECT m.id FROM cr.members m)");
        query.setParameter("userId", userId);
//        query.setParameter("stringUserId", userId.toString());
        List<ChatRoomEntity> result = new ArrayList<>(query.getResultList());
        if (result.isEmpty()) {
            query = em.createQuery("SELECT DISTINCT cr from ChatRoomEntity cr WHERE cr.unrestricted = true");
            List resultList = query.getResultList();
            if (resultList.isEmpty()) {
                result.addAll(createDefaultChatRooms());
            }
        }
        return result;
    }

    private List<ChatRoom> mapChatRooms(List<ChatRoomEntity> chatRoomsentities) {
        List<ChatRoom> result = new ArrayList<>();
        for (ChatRoomEntity chatRoomEntity : chatRoomsentities) {
            ChatRoom chatRoom = mapChatRoomEntityToChatRoom(chatRoomEntity);
            result.add(chatRoom);
        }
        return result;
    }

    private ChatRoom mapChatRoomEntityToChatRoom(ChatRoomEntity chatRoomEntity) {
        Long id = chatRoomEntity.getId();
        String roomName = chatRoomEntity.getRoomName();
        Set<AccountEntity> members = chatRoomEntity.getMembers();
        List<ChatUser> chatUsers = mapMembers(members);
        ChatUser owner = mapMember(chatRoomEntity.getOwner());
        Set<MessageEntity> messages = chatRoomEntity.getMessages();
        List<Message> messageHistory = mapMessages(messages);
        ChatRoomImpl chatRoomImpl = new ChatRoomImpl(id, roomName, chatUsers, messageHistory, owner, chatRoomEntity.isUnrestricted());
        return chatRoomImpl;
    }

    private List<Message> mapMessages(Set<MessageEntity> messages) {
        List<Message> result = new ArrayList<>();
        for (MessageEntity accountEntity : messages) {
            result.add(mapMessage(accountEntity));
        }
        return result;
    }

    private List<ChatUser> mapMembers(Set<AccountEntity> members) {
        List<ChatUser> result = new ArrayList<>();
        for (AccountEntity accountEntity : members) {
            result.add(mapMember(accountEntity));
        }
        return result;
    }

    private ChatUser mapMember(AccountEntity accountEntity) {
        if (accountEntity == null) {
            return null;
        }
        ChatUserImpl chatUserImpl = new ChatUserImpl();
        chatUserImpl.setDisplayName(getDisplayName(accountEntity));
        chatUserImpl.setId(accountEntity.getId());

        return chatUserImpl;
    }

    private Message mapMessage(MessageEntity messageEntity) {
        Message message = new Message(getDisplayName(messageEntity.getAuthor()), messageEntity.getContent(), messageEntity.getCreationDate());
        return message;
    }

    private String getDisplayName(AccountEntity accountEntity) {
        return accountEntity.getDisplayName() != null ? accountEntity.getDisplayName() : accountEntity.getUserName();
    }

    private Set<ChatRoomEntity> createDefaultChatRooms() {
        Set<ChatRoomEntity> chatRoomEntitys = new HashSet<>();
        ChatRoomEntity chatRoomEntity = new ChatRoomEntity();
        chatRoomEntity.setRoomName(DEFAULT_CHAT_ROOM);
        chatRoomEntity.setUnrestricted(true);
        try {
            em.persist(chatRoomEntity);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        //create maybe more default ChatRooms

        chatRoomEntitys.add(chatRoomEntity);
        return chatRoomEntitys;
    }

    @Override
    public boolean removeUserFromChatRoom(ChatRoomChatRequest chatRequest) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean inviteUserToChatRoom(ChatRoomChatRequest chatRequest) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addAdminToChatRoom(ChatRoomChatRequest chatRequest) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteChatRoom(ChatRoomChatRequest chatRequest) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean receiveMessage(MessageChatRequest chatRequest) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean createUserToken(UserIdChatRequest chatRequest) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Long getAccountIdFromToken(UserTokenChatRequest chatRequest) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Long getChatRoomIdFromChatRoomName(ChatRoomNameChatRequest chatRequest) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getDisplayNameFromToken(UserTokenChatRequest chatRequest) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
