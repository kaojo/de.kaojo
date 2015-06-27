package de.kaojo.chat;

/**
 *
 * @author jwinter
 */
public class ChatEvent {

    private final String chatUser;

    private final String chatRoom;
    
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
