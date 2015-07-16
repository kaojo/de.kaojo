/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.kaojo.chat;

import de.kaojo.ejb.ChatManager;
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
        value = "/chatrooms/{room-name}/{user-token}",
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
            @PathParam("room-name") String chatRoom, @PathParam("user-token") String userToken) {
        System.out.println("openSession with roomName :" + chatRoom);
        System.out.println("openSession with userToken :" + userToken);

        if (chatRoom != null && userToken != null) {
            initSessionUserProperties(session, userToken, chatRoom);

            sendMessageToChatRoom(session, chatRoom, new Message(userToken, userToken + " joined chatroom!"));
        }
    }

    @OnMessage
    public void onMessage(Session session, @PathParam("room-name") String chatRoom, @PathParam("user-token") String userToken, Message message) {
        System.out.println("onMessage " + message);

        String chatUser = (String) session.getUserProperties().get(CHAT_USER_PARAM);

        ChatRequestImpl chatRequest = new ChatRequestImpl();
        chatRequest.MessageChatRequest(chatRoom, message, chatUser);
        chatManager.receiveMessage(chatRequest);

        sendMessageToChatRoom(session, chatRoom, message);
    }

    @OnClose
    public void onClose(Session session, @PathParam("room-name") String chatRoom, @PathParam("user-token") String userToken) {
        System.out.println("onClose with roomName: " + chatRoom);
        System.out.println("onClose with userToken: " + userToken);

        String chatUser = (String) session.getUserProperties().get(CHAT_USER_PARAM);

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

    private void initSessionUserProperties(Session session, String userToken, String chatRoom) {
        ChatRequestImpl chatRequest = new ChatRequestImpl();
        chatRequest.setUserToken(userToken);
        String chatUser = chatManager.getUserFromToken(chatRequest);

        Map<String, Object> userProperties = session.getUserProperties();
        userProperties.put(CHAT_USER_PARAM, chatUser);
        userProperties.put(CHAT_ROOM_PARAM, chatRoom);
    }
}
