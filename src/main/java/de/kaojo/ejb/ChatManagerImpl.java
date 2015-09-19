/*
 * Diese Software ist Eigentum von Julian Winter
 * Alle Rechte sind vorbehalten.
 * Copyright 2015.
 */
package de.kaojo.ejb;

import de.kaojo.chat.model.ChatRoom;
import de.kaojo.chat.model.ChatRoomImpl;
import de.kaojo.chat.model.Message;
import de.kaojo.ejb.exceptions.ChatManagerException;
import de.kaojo.ejb.dto.interfaces.ChatRoomChatRequest;
import de.kaojo.ejb.dto.interfaces.ChatRoomNameChatRequest;
import de.kaojo.ejb.dto.interfaces.MessageChatRequest;
import de.kaojo.ejb.dto.interfaces.AccountIdChatRequest;
import de.kaojo.ejb.dto.interfaces.NewChatRoomChatRequest;
import de.kaojo.ejb.dto.interfaces.UserNameChatRequest;
import de.kaojo.persistence.entities.AccountEntity;
import de.kaojo.persistence.entities.ChatRoomEntity;
import de.kaojo.persistence.entities.MessageEntity;
import de.kaojo.persistence.entities.TextMessageEntity;
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
    public boolean removeUserFromChatRoom(ChatRoomChatRequest chatRequest) throws ChatManagerException {
        ChatRoomEntity chatRoomE = em.find(ChatRoomEntity.class, chatRequest.getChatRoomId());
        AccountEntity accountE = em.find(AccountEntity.class, chatRequest.getUserId());
        if (chatRoomE == null | accountE == null) {
            throw new ChatManagerException("Could not remove User from chatroom.");
        }
        return chatRoomE.getMembers().remove(accountE);
    }

    @Override
    public boolean inviteUserToChatRoom(ChatRoomChatRequest chatRequest) throws ChatManagerException {
        ChatRoomEntity chatRoomE = em.find(ChatRoomEntity.class, chatRequest.getChatRoomId());
        AccountEntity accountE = em.find(AccountEntity.class, chatRequest.getUserId());
        if (chatRoomE == null | accountE == null) {
            throw new ChatManagerException("Could not invite User to chatroom.");
        }
        return chatRoomE.getInvites().add(accountE);
    }

    @Override
    public boolean addAdminToChatRoom(ChatRoomChatRequest chatRequest) throws ChatManagerException {
        ChatRoomEntity chatRoomE = em.find(ChatRoomEntity.class, chatRequest.getChatRoomId());
        AccountEntity accountE = em.find(AccountEntity.class, chatRequest.getUserId());
        if (chatRoomE == null | accountE == null) {
            throw new ChatManagerException("Could not add User as admin to chatroom.");
        }
        return chatRoomE.getAdmins().add(accountE);
    }

    @Override
    public boolean deleteChatRoom(ChatRoomChatRequest chatRequest) {
        ChatRoomEntity chatRoomE = em.find(ChatRoomEntity.class, chatRequest.getChatRoomId());
        em.remove(chatRoomE);
        return true;
    }

    @Override
    public boolean createNewChatRoom(NewChatRoomChatRequest chatRequest) throws ChatManagerException {
        List<ChatRoomEntity> chatRoomEntities = getChatRoomsByName(chatRequest.getRoomName());
        AccountEntity accountEntity = em.find(AccountEntity.class, chatRequest.getAccountId());
        if (!chatRoomEntities.isEmpty() || accountEntity == null) {
            return false;
        }
        ChatRoomEntity chatRoomE = new ChatRoomEntity();
        chatRoomE.setRoomName(chatRequest.getRoomName());
        chatRoomE.setUnrestricted(chatRequest.isPublicChatRoom());
        try {
            em.persist(chatRoomE);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        chatRoomE.setOwner(accountEntity);
        chatRoomE.getMembers().add(accountEntity);
        chatRoomE.getAdmins().add(accountEntity);
        return true;
    }

    @Override
    public boolean receiveMessage(MessageChatRequest chatRequest) throws ChatManagerException {
        ChatRoomEntity chatRoomE = em.find(ChatRoomEntity.class, chatRequest.getChatRoomId());
        AccountEntity accountE = em.find(AccountEntity.class, chatRequest.getUserId());
        if (chatRoomE == null | accountE == null) {
            throw new ChatManagerException("Could not receive Message.");
        }
        Message message = chatRequest.getMessage();
        MessageEntity messageE = new TextMessageEntity();
        messageE.setCreationDate(message.getTimestamp());
        messageE.setContent(message.getContent());
        try {
            em.persist(messageE);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        messageE.setAuthor(accountE);
        messageE.setChatRoom(chatRoomE);
        return true;
    }

    @Override
    public Long getAccountIdFromUserName(UserNameChatRequest chatRequest) throws ChatManagerException {
        return getAccountByUserName(chatRequest.getUserName()).getId();
    }

    @Override
    public Long getChatRoomIdFromChatRoomName(ChatRoomNameChatRequest chatRequest) throws ChatManagerException {
        List<ChatRoomEntity> resultList = getChatRoomsByName(chatRequest.getChatRoomName());
        if (resultList.isEmpty() | resultList.size() != 1) {
            throw new ChatManagerException("Invalid chatRoomName supplied, chatRoomName: " + chatRequest.getChatRoomName());
        }
        return resultList.get(0).getId();
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

    private List<ChatRoomEntity> getChatRoomsByName(String chatRoomName) throws ChatManagerException {
        Query query = em.createQuery("SELECT cr FROM ChatRoomEntity cr WHERE cr.roomName = :chatRoomName");
        query.setParameter("chatRoomName", chatRoomName);
        return query.getResultList();
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
        ChatRoomImpl chatRoomImpl = new ChatRoomImpl(chatRoomEntity);
        return chatRoomImpl;
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
            return chatRoomEntitys;
        }
        //create maybe more default ChatRooms

        chatRoomEntitys.add(chatRoomEntity);
        return chatRoomEntitys;
    }
    
    
    private List<Message> mapMessages(List<MessageEntity> messages) {
        List<Message> result = new ArrayList<>();
        for (MessageEntity messageEntity : messages) {
            result.add(new Message(messageEntity));
        }
        return result;
    }

    @Override
    public List<Message> getOldMessages(ChatRoomNameChatRequest chatRequest) throws ChatManagerException {
        String chatRoomName = chatRequest.getChatRoomName();
        Query query = em.createQuery("SELECT me FROM MessageEntity me JOIN me.chatRoom cr WHERE cr.roomName = :chatRoomName ORDER BY me.creationDate");
        query.setParameter("chatRoomName", chatRoomName);
        List resultList = (List<MessageEntity>) query.getResultList();
        return mapMessages(resultList);
    }

    @Override
    public ChatRoom getChatRoomByChatRoomId(ChatRoomChatRequest chatRequest) throws ChatManagerException {
        Long chatRoomId = chatRequest.getChatRoomId();
        ChatRoomEntity chatRoomEntity = em.find(ChatRoomEntity.class, chatRoomId);
        return mapChatRoomEntityToChatRoom(chatRoomEntity);
    }

    @Override
    public ChatRoom getChatRoomByChatRoomName(ChatRoomNameChatRequest chatRequest) throws ChatManagerException {
        String chatRoomName = chatRequest.getChatRoomName();
        Query query = em.createQuery("SELECT cr FROM ChatRoomEntity cr WHERE cr.roomName = :chatRoomName");
        query.setParameter("chatRoomName", chatRoomName);
        List resultList = (List<ChatRoomEntity>) query.getResultList();
        if (resultList.isEmpty() | resultList.size() != 1) {
            throw new ChatManagerException("Invalid chatRoomName supplied, chatRoomName: " + chatRequest.getChatRoomName());
        }
        return mapChatRoomEntityToChatRoom((ChatRoomEntity) resultList.get(0));
    }
}
