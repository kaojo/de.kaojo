package de.kaojo.chat;

import java.io.Serializable;

/**
 *
 * @author jwinter
 */
public class ChatEvent implements Serializable{

    private String chatUser;

    private String chatRoom;
    
    public ChatEvent() {
    }
    
    public ChatEvent(String chatRoom, String chatUser) {
        this.chatRoom = chatRoom;
        this.chatUser = chatUser;
    }

    public String getChatRoom() {
        return chatRoom;
    }

    public String getName() {
        return chatUser;
    }

}
