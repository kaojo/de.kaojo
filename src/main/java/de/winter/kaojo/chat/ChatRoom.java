/*
 * Diese Software ist Eigentum von Julian Winter
 * Alle Rechte sind vorbehalten.
 * Copyright 2015.
 */
package de.winter.kaojo.chat;

import de.winter.kaojo.beans.user.User;
import de.winter.kaojo.websockets.ChatEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import javax.websocket.EncodeException;
import javax.websocket.Session;

/**
 *
 * @author julian
 */
public class ChatRoom {

    private String name;
    private ChatEndpoint chatEndpoint;
    private MessageHistory<Message> messageHistory;
    private ConcurrentHashMap<String, ChatUser> chatUsers;

    public ChatRoom(String name, User user) {
        this.name = name;
        this.chatEndpoint = new ChatEndpoint();
        this.messageHistory = new MessageHistory<>(100);
        if (user != null && user.getUserId() != null && user.getDisplayName() != null) {
            this.chatUsers = new ConcurrentHashMap(8, 0.9F, 1);
            this.chatUsers.put(user.getUserId(), new ChatUser(user.getUserId(), user.getDisplayName()));
        }
    }

    public void sendMessage(String m, Session c) throws IOException, EncodeException {
        this.chatEndpoint.message(m, c);
    }

    public void joinChatRoom(Session peer) {
        this.chatEndpoint.onOpen(peer);
    }

    public void leaveChatRoom(Session peer) {
        this.chatEndpoint.onClose(peer);
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
        chatUsers.remove(user.getUserId());
    }

}
