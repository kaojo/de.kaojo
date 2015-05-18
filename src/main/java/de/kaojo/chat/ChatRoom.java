/*
 * Diese Software ist Eigentum von Julian Winter
 * Alle Rechte sind vorbehalten.
 * Copyright 2015.
 */
package de.kaojo.chat;

import de.kaojo.context.user.User;
import de.kaojo.context.user.DefaultUser;
import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author julian
 */
@Named
public class ChatRoom implements Serializable {

    @Inject
    @DefaultUser
    private User user;

    private String name;
    private MessageHistory<Message> messageHistory;
    private ConcurrentHashMap<String, ChatUser> chatUsers;
    private String messageArea;

    public ChatRoom(String name, User user) {
        this.name = name;
        this.messageHistory = new MessageHistory<>(100);
        if (user != null && user.getUserName() != null && user.getDisplayName() != null) {
            this.chatUsers = new ConcurrentHashMap(8, 0.9F, 1);
            this.chatUsers.put(user.getUserName(), new ChatUser(user.getUserName(), user.getDisplayName()));
        }
    }

    public String sendMessage() {
        Message message = new Message(new Author(user.getUserName(), user.getDisplayName()), messageArea);
        receiveMessage(message);
        return "chat";
    }

    public String leaveChatRoom() {
        return "chat";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMessageHistory(MessageHistory<Message> messageHistory) {
        this.messageHistory = messageHistory;
    }

    public MessageHistory<Message> getMessageHistory() {
        return messageHistory;
    }

    public void setChatUsers(ConcurrentHashMap<String, ChatUser> chatUsers) {
        this.chatUsers = chatUsers;
    }

    public ConcurrentHashMap<String, ChatUser> getChatUsers() {
        return chatUsers;
    }

    public void receiveMessage(Message message) {
        messageHistory.add(message);
    }

    public void removeUser(User user) {
        chatUsers.remove(user.getUserName());
    }

    public String getMessageArea() {
        return messageArea;
    }

    public void setMessageArea(String messageArea) {
        this.messageArea = messageArea;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
