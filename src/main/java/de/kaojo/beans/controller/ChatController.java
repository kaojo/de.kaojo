package de.kaojo.beans.controller;

import de.kaojo.beans.user.User;
import de.kaojo.beans.user.UserQ;
import de.kaojo.chat.ChatRoom;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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
    private List<String> accessibleRooms = new ArrayList<>();

    @Inject
    @UserQ
    private User user;

    public User getUser() {
        return user;
    }

    public String openChatRoom() {
        if (openRoom != null) {
            ChatRoom chatRoom = new ChatRoom(openRoom, user);
            chatRooms.add(chatRoom);
            addMessage("You opened ChatRoom '" + openRoom + "'");
            openRoom = null;
        }
        return "chat";
    }

    public String joinChatRoom() {
        return "chat";
    }

    private void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
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

    public List<String> getAccessibleRooms() {
        return accessibleRooms;
    }

    public void setAccessibleRooms(List<String> accessibleRooms) {
        this.accessibleRooms = accessibleRooms;
    }

}
