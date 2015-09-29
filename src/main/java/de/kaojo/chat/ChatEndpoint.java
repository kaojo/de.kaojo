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
import java.util.logging.Level;
import java.util.logging.Logger;
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

    private static final Logger LOG = Logger.getLogger(ChatEndpoint.class.getName());
    public static final String CHAT_USER_PARAM = "chatUser";
    public static final String ACCOUNT_ID_PARAM = "accountId";
    public static final String CHAT_ROOM_PARAM = "chatRoom";
    public static final String CHAT_ROOM_ID_PARAM = "chatRoomId";

    /**
     * Is always invoked after loading the chat
     *
     * @param session
     * @param c
     * @param chatRoom
     */
    @OnOpen
    public void open(Session session,
            EndpointConfig c,
            @PathParam("room-name") String chatRoom) {
        String name = session.getUserPrincipal().getName();
        if (chatRoom != null) {
            initSessionUserProperties(session, name, chatRoom);

            sendMessageToChatRoom(session, chatRoom, new Message(name, name + " joined chatroom!"));
        }
    }

    /**
     * Is invoked via JS on chat page
     *
     * @param session
     * @param chatRoom
     * @param message
     */
    @OnMessage
    public void onMessage(Session session, @PathParam("room-name") String chatRoom, Message message) {

        String userName = (String) session.getUserProperties().get(CHAT_USER_PARAM);
        message.setAuthor(userName);
        Long userId = (Long) session.getUserProperties().get(ACCOUNT_ID_PARAM);
        Long chatRoomId = (Long) session.getUserProperties().get(CHAT_ROOM_ID_PARAM);

        MessageChatRequest chatRequest = new MessageChatRequestImpl(userId, chatRoomId, message);
        try {
            chatManager.receiveMessage(chatRequest);
        } catch (ChatManagerException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }

        sendMessageToChatRoom(session, chatRoom, message);
    }

    /**
     * Is invoked when Client closes the websocket session
     *
     * @param session
     * @param chatRoom
     */
    @OnClose
    public void onClose(Session session, @PathParam("room-name") String chatRoom) {

        String chatUser = (String) session.getUserProperties().get(CHAT_USER_PARAM);

        sendMessageToChatRoom(session, chatRoom, new Message(chatUser, chatUser + " left chatroom!"));

    }

    @OnError
    public void onError(Throwable t) {
        LOG.log(Level.SEVERE, null, t);
    }

    private void sendMessageToChatRoom(Session session, String chatRoom, Message message) {
        for (Session ses : session.getOpenSessions()) {
            if (ses.isOpen() && chatRoom.equals(ses.getUserProperties().get(CHAT_ROOM_PARAM))) {
                try {
                    ses.getBasicRemote().sendObject(message);
                } catch (IOException | EncodeException ex) {
                    LOG.log(Level.SEVERE, null, ex);
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
            LOG.log(Level.SEVERE, null, ex);
        }
    }
}
