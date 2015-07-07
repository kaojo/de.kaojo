/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.kaojo.chat;

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
        value = "/chatrooms/{room-name}/{user-toker}",
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
            @PathParam("room-name") String chatRoom, @PathParam("user-toker") String userToker) {
        System.out.println("openSession with roomName :" + chatRoom);
        System.out.println("openSession with userToker :" + userToker);

        if (chatRoom != null && userToker != null) {
            initSessionUserProperties(session, userToker, chatRoom);
            
            ChatRequest chatRequest = new ChatRequest();
            chatManager.userjoined(chatRequest);

            sendMessageToChatRoom(session, chatRoom, new Message(userToker, userToker + " joined chatroom!"));
        }
    }

    @OnMessage
    public void onMessage(Session session, @PathParam("room-name") String chatRoom, @PathParam("user-toker") String userToker, Message message) {
        System.out.println("onMessage " + message);
        
        String chatUser = (String) session.getUserProperties().get(CHAT_USER_PARAM);
        message.setAuthor(chatUser);

        ChatRequest chatRequest = new ChatRequest();
        chatManager.sendMessage(chatRequest);

        sendMessageToChatRoom(session, chatRoom, message);
    }

    @OnClose
    public void onClose(Session session, @PathParam("room-name") String chatRoom, @PathParam("user-toker") String userToker) {
        System.out.println("onClose with roomName: " + chatRoom);
        System.out.println("onClose with userToker: " + userToker);

        String chatUser = (String) session.getUserProperties().get(CHAT_USER_PARAM);
        
        ChatRequest chatRequest = new ChatRequest();
        chatManager.userleft(chatRequest);

        sendMessageToChatRoom(session, chatRoom, new Message(chatUser, chatUser + " left chatroom!"));

    }

    @OnError
    public void onError(Throwable t) {
        t.printStackTrace();
    }

    private void sendMessageToChatRoom(Session session, String chatRoom, Message message) {
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

    private void initSessionUserProperties(Session session, String userToker, String chatRoom) {
        Map<String, Object> userProperties = session.getUserProperties();
        String chatUser = chatManager.getUserFromToken(userToker);
        userProperties.put(CHAT_USER_PARAM, chatUser);
        userProperties.put(CHAT_ROOM_PARAM, chatRoom);
    }
}
