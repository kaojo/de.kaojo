/*
 * Diese Software ist Eigentum von Julian Winter
 * Alle Rechte sind vorbehalten.
 * Copyright 2015.
 */
package de.winter.kaojo.chat;

import de.winter.kaojo.websockets.ChatEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
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

    public List<Message> getMessageHistory() {
        return messageHistory;
    }

    public ChatRoom(String name) {
        this.name = name;
        this.chatEndpoint = new ChatEndpoint();
        this.messageHistory = new MessageHistory<Message>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
