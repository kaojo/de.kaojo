/*
 * Diese Software ist Eigentum von Julian Winter
 * Alle Rechte sind vorbehalten.
 * Copyright 2015.
 */
package de.kaojo.chat.model;

import de.kaojo.persistence.entities.AccountEntity;
import de.kaojo.persistence.entities.ChatRoomEntity;
import de.kaojo.persistence.entities.MessageEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author julian
 */
public class ChatRoomImpl implements ChatRoom {

    private String name;
    private Long id;
    private List<ChatUser> chatUsers = new ArrayList<>();
    private List<Message> messageHistory = new ArrayList<>();
    private ChatUser owner;
    private boolean unrestricted = false;

    public ChatRoomImpl(Long id, String name, List<ChatUser> chatUsers, List<Message> messages, ChatUser owner, boolean unrestricted) {
        this.owner = owner;
        this.unrestricted = unrestricted;
        this.id = id;
        this.name = name;
        this.chatUsers.addAll(chatUsers);
        this.messageHistory.addAll(messages);
    }

    public ChatRoomImpl(ChatRoomEntity chatRoomEntity) {
        id = chatRoomEntity.getId();
        name = chatRoomEntity.getRoomName();
        Set<AccountEntity> members = chatRoomEntity.getMembers();
        chatUsers = mapMembers(members);
        owner = chatRoomEntity.getOwner() != null ? new ChatUserImpl(chatRoomEntity.getOwner()) : null;
        Set<MessageEntity> messages = chatRoomEntity.getMessages();
        messageHistory = mapMessages(messages);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ChatUser getOwner() {
        return owner;
    }

    public void setOwner(ChatUser owner) {
        this.owner = owner;
    }

    @Override
    public boolean isUnrestricted() {
        return unrestricted;
    }

    public void setUnrestricted(boolean unrestricted) {
        this.unrestricted = unrestricted;
    }


    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public List<ChatUser> getChatUsers() {
        return chatUsers;
    }

    public void setChatUsers(List<ChatUser> chatUsers) {
        this.chatUsers = chatUsers;
    }

    @Override
    public List<Message> getMessageHistory() {
        return messageHistory;
    }

    public void setMessageHistory(List<Message> messageHistory) {
        this.messageHistory = messageHistory;
    }

    @Override
    public boolean isEmpty() {
        return chatUsers.isEmpty();
    }

    @Override
    public String toString() {
        return unrestricted ? name + " (public)" : name;
    }

    private List<ChatUser> mapMembers(Set<AccountEntity> members) {
        List<ChatUser> result = new ArrayList<>();
        for (AccountEntity accountEntity : members) {
            result.add(new ChatUserImpl(accountEntity));
        }
        return result;
    }

    private List<Message> mapMessages(Set<MessageEntity> messages) {
        List<Message> result = new ArrayList<>();
        for (MessageEntity messageEntity : messages) {
            result.add(new Message(messageEntity));
        }
        return result;
    }
}
