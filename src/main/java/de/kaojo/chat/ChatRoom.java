package de.kaojo.chat;

import java.util.List;
import java.util.Set;
import javax.websocket.Session;

/**
 *
 * @author jwinter
 */
public interface ChatRoom {

    public String getName();

    public Set<ChatUser> getAllChatUsers();

    public void sendMessage(Message message);

    public void leaveChatRoom(Session session);

    public void joinChatRoom(Session session);

    public List<Message> getMessageHistory();

    public boolean isEmpty();

}
