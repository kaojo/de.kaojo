/*
 * Diese Software ist Eigentum von Julian Winter
 * Alle Rechte sind vorbehalten.
 * Copyright 2015.
 */
package de.winter.kaojo.chat;

import de.winter.kaojo.beans.user.User;
import de.winter.kaojo.websockets.ChatEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import javax.inject.Inject;
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
    private ConcurrentHashMap<String, ChatUser> chatUsers = new ConcurrentHashMap(8,0.9F,1);

    public MessageHistory<Message> getMessageHistory() {
        return messageHistory;
    }

    public ChatRoom(String name, User user) {
        this.name = name;
        this.chatEndpoint = new ChatEndpoint();
        this.messageHistory = new MessageHistory<>(100);
        this.chatUsers.put(user.getUserId(), new ChatUser(user.getUserId(), user.getDisplayName()));
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

}
