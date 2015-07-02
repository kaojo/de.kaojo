/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.kaojo.websocket;

import de.kaojo.chat.Message;
import de.kaojo.chat.TextMessageDecoder;
import de.kaojo.chat.TextMessageEncoder;
import de.kaojo.ejb.ChatManager;
import de.kaojo.ejb.dto.ChatRequest;
import java.io.IOException;
import java.util.Map;
import javax.inject.Inject;
import javax.websocket.EncodeException;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(
        value = "/chatrooms/{room-name}/{chat-user}",
        encoders = {TextMessageEncoder.class},
        decoders = {TextMessageDecoder.class}
)
public class ChatEndpoint {

    @Inject
    ChatManager chatManager;

    public static final String CHAT_USER_PARAM = "chatUser";
    public static final String CHAT_ROOM_PARAM = "chatRoom";

    @OnOpen
    public void open(Session session,
            EndpointConfig c,
            @PathParam("room-name") String chatRoom, @PathParam("chat-user") String chatUser) {
        System.out.println("openSession with roomName :" + chatRoom);
        System.out.println("openSession with chatUser :" + chatUser);

        if (chatRoom != null && chatUser != null) {
            ChatRequest chatRequest = new ChatRequest();
            Map<String, Object> userProperties = session.getUserProperties();
            userProperties.put(CHAT_USER_PARAM, chatUser);
            userProperties.put(CHAT_ROOM_PARAM, chatRoom);
            chatManager.userjoined(chatRequest);
            for (Session ses : session.getOpenSessions()) {
                if (ses.isOpen() && chatRoom.equals(ses.getUserProperties().get(CHAT_ROOM_PARAM))) {
                    try {
                        ses.getBasicRemote().sendObject(new Message(chatUser, chatUser + " joined chatroom!"));
                    } catch (IOException | EncodeException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }

    @OnMessage
    public void onMessage(Session session, @PathParam("room-name") String chatRoom, @PathParam("chat-user") String chatUser, Message message) {
        System.out.println("onMessage " + message);
        ChatRequest chatRequest = new ChatRequest();
        chatManager.sendMessage(chatRequest);
        for (Session ses : session.getOpenSessions()) {
            if (ses.isOpen() && chatRoom.equals(ses.getUserProperties().get(CHAT_ROOM_PARAM))) {
                try {
                    ses.getBasicRemote().sendObject(message);
                } catch (IOException | EncodeException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    @OnClose
    public void onClose(Session session, @PathParam("room-name") String chatRoom, @PathParam("chat-user") String chatUser) {
        ChatRequest chatRequest = new ChatRequest();
        chatManager.userleft(chatRequest);
        for (Session ses : session.getOpenSessions()) {
            if (ses.isOpen() && chatRoom.equals(ses.getUserProperties().get(CHAT_ROOM_PARAM))) {
                try {
                    ses.getBasicRemote().sendObject(new Message(chatUser, chatUser + " left chatroom!"));
                } catch (IOException | EncodeException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    @OnError
    public void onError(Throwable t) {
        t.printStackTrace();
    }
}
