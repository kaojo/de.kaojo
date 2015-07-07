/*
 * Diese Software ist Eigentum von Julian Winter
 * Alle Rechte sind vorbehalten.
 * Copyright 2015.
 */
package de.kaojo.chat;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.websocket.EncodeException;
import javax.websocket.Session;

/**
 *
 * @author julian
 */
public class ChatRoomImpl implements Serializable, ChatRoom {

    private String name;
    private List<ChatUser> chatUsers;

    public ChatRoomImpl() {
    }

    public ChatRoomImpl(String name) {
        this.chatUsers = new ArrayList<>();
        this.name = name;
    }

    @Override
    public List<ChatUser> getAllChatUsers() {
        return chatUsers;
    }

    @Override
    public void sendMessage(Message message) {
        broadCastMessage(message);
    }

    @Override
    public void leaveChatRoom(Session session) {
        String chatUserName = getChatUserFromSession(session);
        for (ChatUser chatUser: chatUsers) {
            if (chatUserName != null && chatUserName.equals(chatUser.getName())) {
                chatUsers.remove(chatUser);
            }
        }
        broadCastMessage(new Message(name, chatUserName + " hat den Raum verlassen."));
        
        //notify ChatManager
        

    }

    @Override
    public void joinChatRoom(Session session) {
        String chatUser = getChatUserFromSession(session);
        chatUsers.add(new ChatUserImpl(chatUser, session));
        broadCastMessage(new Message(name, chatUser + " hat den Raum betreten."));

        //notify ChatManager
        

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
        for (ChatUser chatUser : chatUsers) {
            try {
                chatUser.getSession().getBasicRemote().sendObject(message);
            } catch (IOException | EncodeException ex) {
                ex.printStackTrace();
            }
        }
    }

    private String getChatUserFromSession(Session session) {
        String chatUser = (String) session.getUserProperties().get("");
        return chatUser;
    }

}
