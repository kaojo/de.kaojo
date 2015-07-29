package de.kaojo.ejb.dto.interfaces;

/**
 *
 * @author jwinter
 */
public interface NewChatRoomChatRequest {

    public String getRoomName();

    public Long getAccountId();
    
    public boolean isPublicChatRoom();
}
