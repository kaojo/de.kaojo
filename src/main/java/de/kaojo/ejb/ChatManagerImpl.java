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
import de.kaojo.persistence.entities.AccountEntity;
import de.kaojo.persistence.entities.ChatRoomEntity;
import de.kaojo.persistence.entities.MessageEntity;
import de.kaojo.persistence.entities.TextMessageEntity;
import de.kaojo.persistence.repositories.AccountRepository;
import de.kaojo.persistence.repositories.ChatRoomRepository;
import de.kaojo.persistence.repositories.MessageRepository;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author julian.winter
 */
@Stateless
public class ChatManagerImpl implements ChatManager {

    @Inject
    private ChatRoomRepository chatRoomRepository;

    @Inject
    private AccountRepository accountRepository;

    @Inject
    private MessageRepository messageRepository;

    @Override
    public List<ChatRoom> getAllChatRooms(AccountIdChatRequest chatRequest) throws ChatManagerException {
        List<ChatRoomEntity> cre = chatRoomRepository.findByMembersOrInvitesAccountId(chatRequest.getAccountId());
        return mapChatRoomEntities(cre);
    }

    @Override
    public List<ChatRoom> getChatRooms(AccountIdChatRequest chatRequest) {
        return getMemberedChatRooms(chatRequest);
    }

    @Override
    public List<ChatRoom> getAccessibleChatRooms(AccountIdChatRequest chatRequest) {
        List<ChatRoom> invitedChatRooms = getInvitedChatRooms(chatRequest);
        List<ChatRoom> publicChatRooms = getPublicChatRooms(chatRequest);
        if (!publicChatRooms.isEmpty()) {
            invitedChatRooms.addAll(publicChatRooms);
        }
        return invitedChatRooms;
    }

    @Override
    public boolean addUserToChatRoom(ChatRoomChatRequest chatRequest) {
        AccountEntity accountEntity = accountRepository.findBy(chatRequest.getUserId());
        ChatRoomEntity chatRoomEntity = chatRoomRepository.findBy(chatRequest.getChatRoomId());
        chatRoomEntity.getMembers().add(accountEntity);
        return true;
    }

    @Override
    public boolean removeUserFromChatRoom(ChatRoomChatRequest chatRequest) throws ChatManagerException {
        AccountEntity accountEntity = accountRepository.findBy(chatRequest.getUserId());
        ChatRoomEntity chatRoomEntity = chatRoomRepository.findBy(chatRequest.getChatRoomId());
        if (chatRoomEntity == null | accountEntity == null) {
            throw new ChatManagerException("Could not remove User from chatroom.");
        }
        return chatRoomEntity.getMembers().remove(accountEntity);
    }

    @Override
    public boolean inviteUserToChatRoom(ChatRoomChatRequest chatRequest) throws ChatManagerException {
        AccountEntity accountEntity = accountRepository.findBy(chatRequest.getUserId());
        ChatRoomEntity chatRoomEntity = chatRoomRepository.findBy(chatRequest.getChatRoomId());
        if (chatRoomEntity == null | accountEntity == null) {
            throw new ChatManagerException("Could not invite User to chatroom.");
        }
        return chatRoomEntity.getInvites().add(accountEntity);
    }

    @Override
    public boolean addAdminToChatRoom(ChatRoomChatRequest chatRequest) throws ChatManagerException {
        AccountEntity accountEntity = accountRepository.findBy(chatRequest.getUserId());
        ChatRoomEntity chatRoomEntity = chatRoomRepository.findBy(chatRequest.getChatRoomId());
        if (chatRoomEntity == null | accountEntity == null) {
            throw new ChatManagerException("Could not add User as admin to chatroom.");
        }
        return chatRoomEntity.getAdmins().add(accountEntity);
    }

    @Override
    public boolean deleteChatRoom(ChatRoomChatRequest chatRequest) {
        ChatRoomEntity chatRoomEntity = chatRoomRepository.findBy(chatRequest.getChatRoomId());
        chatRoomRepository.remove(chatRoomEntity);
        return true;
    }

    @Override
    public boolean createNewChatRoom(NewChatRoomChatRequest chatRequest) throws ChatManagerException {
        List<ChatRoom> chatRooms = getChatRoomsByName(chatRequest.getRoomName());
        AccountEntity accountEntity = accountRepository.findBy(chatRequest.getAccountId());
        if (!chatRooms.isEmpty() || accountEntity == null) {
            return false;
        }
        ChatRoomEntity chatRoomE = new ChatRoomEntity();
        chatRoomE.setRoomName(chatRequest.getRoomName());
        chatRoomE.setUnrestricted(chatRequest.isPublicChatRoom());
        chatRoomRepository.save(chatRoomE);
        chatRoomE.setOwner(accountEntity);
        chatRoomE.getMembers().add(accountEntity);
        chatRoomE.getAdmins().add(accountEntity);
        return true;
    }

    @Override
    public boolean receiveMessage(MessageChatRequest chatRequest) throws ChatManagerException {
        AccountEntity accountEntity = accountRepository.findBy(chatRequest.getUserId());
        ChatRoomEntity chatRoomEntity = chatRoomRepository.findBy(chatRequest.getChatRoomId());
        if (chatRoomEntity == null | accountEntity == null) {
            throw new ChatManagerException("Could not receive Message.");
        }
        Message message = chatRequest.getMessage();
        MessageEntity messageE = new TextMessageEntity();
        messageE.setCreationDate(message.getTimestamp());
        messageE.setContent(message.getContent());
        messageRepository.save(messageE);
        messageE.setAuthor(accountEntity);
        messageE.setChatRoom(chatRoomEntity);
        return true;
    }

    @Override
    public Long getChatRoomIdFromChatRoomName(ChatRoomNameChatRequest chatRequest) throws ChatManagerException {
        List<ChatRoom> resultList = getChatRoomsByName(chatRequest.getChatRoomName());
        if (resultList.isEmpty() | resultList.size() != 1) {
            throw new ChatManagerException("Invalid chatRoomName supplied, chatRoomName: " + chatRequest.getChatRoomName());
        }
        return resultList.get(0).getId();
    }

    private List<ChatRoom> getMemberedChatRooms(AccountIdChatRequest chatRequest) {
        return mapChatRoomEntities(chatRoomRepository.findByMembersAccountId(chatRequest.getAccountId()));
    }

    private List<ChatRoom> getInvitedChatRooms(AccountIdChatRequest chatRequest) {
        return mapChatRoomEntities(chatRoomRepository.findByInvitesAccountIdExludeMembers(chatRequest.getAccountId()));
    }

    private List<ChatRoom> getPublicChatRooms(AccountIdChatRequest chatRequest) {
        return mapChatRoomEntities(chatRoomRepository.findByUnrestrictedExludeMembered(chatRequest.getAccountId()));
    }

    private List<ChatRoom> getChatRoomsByName(String chatRoomName) {
        return mapChatRoomEntities(chatRoomRepository.findByRoomName(chatRoomName));
    }

    private ChatRoom mapChatRoomEntityToChatRoom(ChatRoomEntity chatRoomEntity) {
        ChatRoomImpl chatRoomImpl = new ChatRoomImpl(chatRoomEntity);
        return chatRoomImpl;
    }

    @Override
    public List<Message> getOldMessages(ChatRoomNameChatRequest chatRequest) throws ChatManagerException {
        return mapMessageEntity(messageRepository.findByChatRoomRoomName(chatRequest.getChatRoomName()));
    }

    @Override
    public ChatRoom getChatRoomByChatRoomId(ChatRoomChatRequest chatRequest) throws ChatManagerException {
        return mapChatRoomEntityToChatRoom(chatRoomRepository.findBy(chatRequest.getChatRoomId()));
    }

    @Override
    public ChatRoom getChatRoomByChatRoomName(ChatRoomNameChatRequest chatRequest) throws ChatManagerException {
        List<ChatRoom> resultList = getChatRoomsByName(chatRequest.getChatRoomName());
        if (resultList.isEmpty() | resultList.size() != 1) {
            throw new ChatManagerException("Invalid chatRoomName supplied, chatRoomName: " + chatRequest.getChatRoomName());
        }
        return resultList.get(0);
    }

    private List<ChatRoom> mapChatRoomEntities(List<ChatRoomEntity> cre) {
        List<ChatRoom> result = new ArrayList<>();
        cre.stream().forEach((chatRoomEntity) -> {
            result.add(new ChatRoomImpl(chatRoomEntity));
        });
        return result;
    }

    private List<Message> mapMessageEntity(List<MessageEntity> me) {
        List<Message> result = new ArrayList<>();
        me.stream().forEach(messageEntity -> {
            result.add(new Message(messageEntity));
        });
        return result;
    }
}
