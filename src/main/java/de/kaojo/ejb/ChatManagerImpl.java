/*
 * Diese Software ist Eigentum von Julian Winter
 * Alle Rechte sind vorbehalten.
 * Copyright 2015.
 */
package de.kaojo.ejb;

import de.kaojo.chat.model.ChatRoom;
import de.kaojo.chat.model.ChatRoomImpl;
import de.kaojo.ejb.exceptions.ChatManagerException;
import de.kaojo.ejb.dto.interfaces.ChatRoomChatRequest;
import de.kaojo.ejb.dto.interfaces.ChatRoomNameChatRequest;
import de.kaojo.ejb.dto.interfaces.MessageChatRequest;
import de.kaojo.ejb.dto.interfaces.AccountIdChatRequest;
import de.kaojo.ejb.dto.interfaces.UserNameChatRequest;
import de.kaojo.persistence.entities.AccountEntity;
import de.kaojo.persistence.entities.ChatRoomEntity;
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
    public List<ChatRoom> getChatRooms(AccountIdChatRequest chatRequest) {
        List<ChatRoomEntity> memberedChatRooms = getMemberedChatRooms(chatRequest);
        return mapChatRooms(memberedChatRooms);
    }

    @Override
    public List<ChatRoom> getAccessibleChatRooms(AccountIdChatRequest chatRequest) {
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
    public Long getAccountIdFromUserName(UserNameChatRequest chatRequest) throws ChatManagerException {
        return getAccountByUserName(chatRequest.getUserName()).getId();
    }

    @Override
    public Long getChatRoomIdFromChatRoomName(ChatRoomNameChatRequest chatRequest) throws ChatManagerException {
        return getChatRoomByName(chatRequest.getChatRoomName()).getId();
    }

    @Override
    public String getDisplayNameFromAccountId(AccountIdChatRequest chatRequest) throws ChatManagerException {
        AccountEntity account = em.find(AccountEntity.class, chatRequest.getAccountId());
        if (account == null) {
            throw new ChatManagerException("Invalid UserId supplied, UserId: " + chatRequest.getAccountId());
        }
        return getDisplayName(account);
    }

    private List<ChatRoomEntity> getMemberedChatRooms(AccountIdChatRequest chatRequest) {
        Long userId = chatRequest.getAccountId();
        Query query = em.createQuery("SELECT DISTINCT cr FROM ChatRoomEntity cr JOIN cr.members m WHERE m.id = :userId ");
        query.setParameter("userId", userId);
        return new ArrayList<>(query.getResultList());
    }

    private List<ChatRoomEntity> getInvitedChatRooms(AccountIdChatRequest chatRequest) {
        Long userId = chatRequest.getAccountId();
        Query query = em.createQuery("SELECT DISTINCT cr FROM ChatRoomEntity cr JOIN cr.invites i WHERE i.id = :userId AND :userId <> ALL (SELECT m.id FROM cr.members m)");
        query.setParameter("userId", userId);
        return new ArrayList<>(query.getResultList());
    }

    private List<ChatRoomEntity> getPublicChatRooms(AccountIdChatRequest chatRequest) {
        Long userId = chatRequest.getAccountId();
        Query query = em.createQuery("SELECT DISTINCT cr FROM ChatRoomEntity cr WHERE cr.unrestricted = true AND :userId <> ALL (SELECT m.id FROM cr.members m)");
        query.setParameter("userId", userId);
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

    private AccountEntity getAccountByUserName(String userName) throws ChatManagerException {
        Query query = em.createQuery("SELECT ac FROM AccountEntity ac WHERE ac.userName = :userName");
        query.setParameter("userName", userName);
        List resultList = query.getResultList();
        if (resultList.isEmpty() | resultList.size() != 1) {
            throw new ChatManagerException("Invalid userName supplied, userName: " + userName);
        }
        return (AccountEntity) resultList.get(0);
    }

    private ChatRoomEntity getChatRoomByName(String chatRoomName) throws ChatManagerException {
        Query query = em.createQuery("SELECT cr FROM ChatRoomEntity cr WHERE cr.roomName = :chatRoomName");
        query.setParameter("chatRoomName", chatRoomName);
        List resultList = query.getResultList();
        if (resultList.isEmpty() | resultList.size() != 1) {
            throw new ChatManagerException("Invalid chatRoomName supplied, chatRoomName: " + chatRoomName);
        }
        return (ChatRoomEntity) resultList.get(0);
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
        return new ChatRoomImpl(chatRoomEntity);
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
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        //create maybe more default ChatRooms

        chatRoomEntitys.add(chatRoomEntity);
        return chatRoomEntitys;
    }
}
