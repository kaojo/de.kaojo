package de.kaojo.beans.app;

/**
 *
 * @author jwinter
 */
public class ChatEvent {

    private String chatUser;

    private String chatRoom;

    public String getChatRoom() {
        return chatRoom;
    }

    public void setChatRoom(String chatRoom) {
        this.chatRoom = chatRoom;
    }

    public String getName() {
        return chatUser;
    }

    public void setName(String name) {
        this.chatUser = name;
    }
}
