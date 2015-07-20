/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.kaojo.chat;

import de.kaojo.chat.model.Message;
import de.kaojo.ejb.ChatManager;
import de.kaojo.ejb.dto.AccountIdChatRequestImpl;
import de.kaojo.ejb.dto.interfaces.ChatRoomNameChatRequest;
import de.kaojo.ejb.dto.ChatRoomNameChatRequestImpl;
import de.kaojo.ejb.dto.interfaces.UserNameChatRequest;
import de.kaojo.ejb.dto.UserNameChatRequestImpl;
import de.kaojo.ejb.dto.interfaces.MessageChatRequest;
import de.kaojo.ejb.dto.MessageChatRequestImpl;
import de.kaojo.ejb.dto.interfaces.AccountIdChatRequest;
import de.kaojo.ejb.exceptions.ChatManagerException;
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
        value = "/chatrooms/{room-name}",
        encoders = {TextMessageEncoder.class},
        decoders = {TextMessageDecoder.class}
)
public class ChatEndpoint {

    @Inject
    ChatManager chatManager;

    public static final String CHAT_USER_PARAM = "chatUser";
    public static final String ACCOUNT_ID_PARAM = "accountId";
    public static final String CHAT_ROOM_PARAM = "chatRoom";
    public static final String CHAT_ROOM_ID_PARAM = "chatRoomId";

    @OnOpen
    public void open(Session session,
            EndpointConfig c,
            @PathParam("room-name") String chatRoom) {
        String name = session.getUserPrincipal().getName();
        System.out.println("openSession with roomName :" + chatRoom);
        if (chatRoom != null) {
            initSessionUserProperties(session, name, chatRoom);

            sendMessageToChatRoom(session, chatRoom, new Message(name, name + " joined chatroom!"));
        }
    }

    @OnMessage
    public void onMessage(Session session, @PathParam("room-name") String chatRoom, Message message) {
        System.out.println("onMessage " + message);

        String userName = (String) session.getUserProperties().get(CHAT_USER_PARAM);
        message.setAuthor(userName);
        Long userId = (Long) session.getUserProperties().get(ACCOUNT_ID_PARAM);
        Long chatRoomId = (Long) session.getUserProperties().get(CHAT_ROOM_ID_PARAM);

        MessageChatRequest chatRequest = new MessageChatRequestImpl(userId, chatRoomId, message);
        try {
            chatManager.receiveMessage(chatRequest);
        } catch (ChatManagerException ex) {
            System.out.println(ex.getMessage());
        }

        sendMessageToChatRoom(session, chatRoom, message);
    }

    @OnClose
    public void onClose(Session session, @PathParam("room-name") String chatRoom) {
        System.out.println("onClose with roomName: " + chatRoom);

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

    private void initSessionUserProperties(Session session, String name, String chatRoom) {

        try {
            UserNameChatRequest uChatRequest = new UserNameChatRequestImpl(name);
            Long accountId = chatManager.getAccountIdFromUserName(uChatRequest);

            AccountIdChatRequest aChatRequest = new AccountIdChatRequestImpl(accountId);
            String displayName = chatManager.getDisplayNameFromAccountId(aChatRequest);

            ChatRoomNameChatRequest chatRoomNameChatRequest = new ChatRoomNameChatRequestImpl(chatRoom);
            Long chatRoomId = chatManager.getChatRoomIdFromChatRoomName(chatRoomNameChatRequest);

            Map<String, Object> userProperties = session.getUserProperties();
            userProperties.put(CHAT_USER_PARAM, displayName);
            userProperties.put(ACCOUNT_ID_PARAM, accountId);
            userProperties.put(CHAT_ROOM_PARAM, chatRoom);
            userProperties.put(CHAT_ROOM_ID_PARAM, chatRoomId);
        } catch (ChatManagerException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
