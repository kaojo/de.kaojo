package de.winter.kaojo.beans.controller;

import de.winter.kaojo.beans.user.User;
import de.winter.kaojo.beans.user.UserImpl;
import de.winter.kaojo.chat.ChatRoom;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author julian
 */
@Named("chatController")
@SessionScoped
public class ChatController implements Serializable {

    private List<ChatRoom> chatRooms = new ArrayList<>();
    private String openRoom;

    @Inject
    private User user;

    public String openChatRoom() {
        if (openRoom != null) {
            ChatRoom chatRoom = new ChatRoom(openRoom, user);
            chatRooms.add(chatRoom);
        }
        return "chat";
    }

    public List<ChatRoom> getChatRooms() {
        return chatRooms;
    }
    
    public void setChatRooms(List<ChatRoom> iChatRooms) {
        this.chatRooms = iChatRooms;
    }

    public String getOpenRoom() {
        return openRoom;
    }
    
    public void setOpenRoom(String iOpenRoom) {
        this.openRoom = iOpenRoom;
    }

}
