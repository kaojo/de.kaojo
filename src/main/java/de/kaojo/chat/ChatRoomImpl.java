/*
 * Diese Software ist Eigentum von Julian Winter
 * Alle Rechte sind vorbehalten.
 * Copyright 2015.
 */
package de.kaojo.chat;

import de.kaojo.websockets.ChatEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javax.websocket.EncodeException;
import javax.websocket.Session;

/**
 *
 * @author julian
 */
public class ChatRoomImpl implements ChatRoom {

    private final String name;
    private final ConcurrentHashMap<String, ChatUser> chatUsers;

    public ChatRoomImpl(String name) {
        this.chatUsers = new ConcurrentHashMap<>();
        this.name = name;
    }

    @Override
    public Set<ChatUser> getAllChatUsers() {
        return (Set<ChatUser>) chatUsers.elements();
    }

    @Override
    public void sendMessage(Message message) {
        broadCastMessage(message);
    }

    @Override
    public void leaveChatRoom(Session session) {
        String chatUser = getChatUserFromSession(session);
        chatUsers.remove(chatUser);
        broadCastMessage(new Message(new Author(name, name), chatUser + " hat den Raum verlassen."));
    }

    @Override
    public void joinChatRoom(Session session) {
        String chatUser = getChatUserFromSession(session);
        chatUsers.put(chatUser, new ChatUserImpl(chatUser, session));
        broadCastMessage(new Message(new Author(name, name), chatUser + " hat den Raum betreten."));
    }

    @Override
    public List<Message> getMessageHistory() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isEmpty() {
        return chatUsers.isEmpty();
    }

    @Override
    public String getName() {
        return name;
    }

    private void broadCastMessage(Message message) {
        for (ChatUser chatUser : chatUsers.values()) {
            try {
                chatUser.getSession().getBasicRemote().sendObject(message);
            } catch (IOException | EncodeException ex) {
                ex.printStackTrace();
            }
        }
    }

    private String getChatUserFromSession(Session session) {
        String chatUser = (String) session.getUserProperties().get(ChatEndpoint.CHAT_USER_PARAM);
        return chatUser;
    }

}
