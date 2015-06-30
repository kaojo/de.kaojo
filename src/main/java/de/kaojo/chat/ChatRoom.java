package de.kaojo.chat;

import java.util.List;
import javax.ejb.Local;
import javax.websocket.Session;

/**
 *
 * @author jwinter
 */
@Local
public interface ChatRoom {

    public String getName();

    public List<ChatUser> getAllChatUsers();

    public void sendMessage(Message message);

    public void leaveChatRoom(Session session);

    public void joinChatRoom(Session session);

    public List<Message> getMessageHistory();

    public boolean isEmpty();

}
