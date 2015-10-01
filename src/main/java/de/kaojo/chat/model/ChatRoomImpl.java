/*
 * Diese Software ist Eigentum von Julian Winter
 * Alle Rechte sind vorbehalten.
 * Copyright 2015.
 */
package de.kaojo.chat.model;

import de.kaojo.persistence.entities.AccountEntity;
import de.kaojo.persistence.entities.ChatRoomEntity;
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
    private int messageHistorySize;
    private ChatUser owner;
    private boolean unrestricted = false;

    public ChatRoomImpl(ChatRoomEntity chatRoomEntity) {
        id = chatRoomEntity.getId();
        name = chatRoomEntity.getRoomName();
        chatUsers = mapInvitesAndMembers(chatRoomEntity);
        owner = chatRoomEntity.getOwner() != null ? new ChatUserImpl(chatRoomEntity.getOwner()) : null;
        unrestricted = chatRoomEntity.isUnrestricted();
        messageHistorySize = chatRoomEntity.getMessages().size();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ChatUser getOwner() {
        if (this.owner == null) {
            ChatUserMinimalImpl noneOwner = new ChatUserMinimalImpl();
            noneOwner.setDisplayName("none");
            return noneOwner;
        }
        return this.owner;
    }

    public void setOwner(ChatUser owner) {
        this.owner = owner;
    }

    public int getMessageHistorySize() {
        return this.messageHistorySize;
    }

    @Override
    public boolean isUnrestricted() {
        return this.unrestricted;
    }

    public void setUnrestricted(boolean unrestricted) {
        this.unrestricted = unrestricted;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Long getId() {
        return this.id;
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

    /**
     *
     * @param messageHistory
     */
    @Override
    public void setMessageHistory(List<Message> messageHistory) {
        this.messageHistory = messageHistory;
    }

    @Override
    public boolean isEmpty() {
        return chatUsers.isEmpty();
    }

    @Override
    public String toString() {
        return this.name;
    }

    private List<ChatUser> mapInvitesAndMembers(ChatRoomEntity chatRoomEntity) {
        List<ChatUser> result = new ArrayList<>();
        Set<AccountEntity> members = chatRoomEntity.getMembers();
        Set<AccountEntity> invites = chatRoomEntity.getInvites();
        if (chatRoomEntity.isUnrestricted()) {
            for (AccountEntity accountEntity : members) {
                ChatUserImpl chatUser = new ChatUserImpl(accountEntity);
                chatUser.setJoined(true);
                result.add(chatUser);
            }
        } else {
            for (AccountEntity accountEntity : invites) {
                ChatUserImpl chatUser = new ChatUserImpl(accountEntity);
                chatUser.setJoined(members.contains(accountEntity));
                result.add(chatUser);
            }
        }
        return result;
    }

}
