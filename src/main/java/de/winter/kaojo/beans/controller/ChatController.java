package de.winter.kaojo.beans.controller;

import de.winter.kaojo.chat.ChatRoom;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author julian
 */
@ManagedBean(name = "chatController")
public class ChatController implements Serializable {

    private List<ChatRoom> chatRooms;

    @PostConstruct
    public void init() {
        chatRooms = new ArrayList<ChatRoom>();
        chatRooms.add(new ChatRoom("Wohnzimmer"));
    }

    public List<ChatRoom> getChatRooms() {
        return chatRooms;
    }

}
