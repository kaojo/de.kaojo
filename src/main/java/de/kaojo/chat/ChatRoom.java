package de.kaojo.chat;

import java.util.List;

/**
 *
 * @author jwinter
 */
public interface ChatRoom {

    public String getName();
    
    public Long getId();

    public List<ChatUser> getChatUsers();

    public List<Message> getMessageHistory();

    public boolean isEmpty();
    
    public ChatUser getOwner();
    
    public boolean isUnrestricted();

}
