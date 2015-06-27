/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.kaojo.websocket;

import de.kaojo.chat.ChatRoom;
import de.kaojo.chat.ChatRoomImpl;
import de.kaojo.chat.Message;
import de.kaojo.chat.TextMessageDecoder;
import de.kaojo.chat.TextMessageEncoder;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(
   value = "/chatrooms/{room-name}",
   encoders = { TextMessageEncoder.class },
   decoders = { TextMessageDecoder.class }
)
public class ChatEndpoint {

    private static ConcurrentHashMap<String, ChatRoom> chatRooms;
    public static final String CHAT_USER_PARAM = "chatUser";

    @OnOpen
    public void open(Session session,
            EndpointConfig c,
            @PathParam("room-name") String roomName) {

        ChatRoom chatRoom = chatRooms.get(roomName);
        Map<String, String> pathParameters = session.getPathParameters();
        String chatUser = pathParameters.get(CHAT_USER_PARAM);

        if (chatRoom != null && chatUser != null) {
            Map<String, Object> userProperties = session.getUserProperties();
            userProperties.put(CHAT_USER_PARAM, chatUser);
            chatRoom.joinChatRoom(session);
        } else {
            chatRoom = new ChatRoomImpl(roomName);
            chatRoom.joinChatRoom(session);
            chatRooms.put(roomName, chatRoom);
        }
    }

    @OnMessage
    public void onMessage(Session session, @PathParam("room-name") String roomName, Message message) {

        ChatRoom chatRoom = chatRooms.get(roomName);
        chatRoom.sendMessage(message);

    }

    @OnClose
    public void onClose(Session session, @PathParam("room-name") String roomName) {

        ChatRoom chatRoom = chatRooms.get(roomName);

        if (chatRoom != null) {
            chatRoom.leaveChatRoom(session);
            if (chatRoom.isEmpty()) {
                chatRooms.remove(roomName);
            }
        }
    }

    @OnError
    public void onError(Throwable t) {
        t.printStackTrace();
    }
}
