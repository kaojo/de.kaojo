package de.kaojo.context.controller;

import de.kaojo.beans.app.DefaultChatManager;
import de.kaojo.beans.app.ChatManager;
import de.kaojo.context.user.User;
import de.kaojo.context.user.DefaultUser;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author julian
 */
@Named("chatController")
@RequestScoped
public class ChatController implements Serializable {

    private List<String> chatRooms = new ArrayList<>();
    private String openRoom;
    private List<String> accessibleRooms = new ArrayList<>();

    @Inject
    @DefaultUser
    private User user;

    @Inject
    @DefaultChatManager
    private ChatManager chatmanager;

    public User getUser() {
        return user;
    }

    public String openChatRoom() {
        addMessage("Error opening ChatRoom '" + openRoom + "'");
        return "chat";
    }

    public String joinChatRoom() {
        addMessage("Error joining ChatRoom '" + openRoom + "'");
        return "chat";
    }

    private void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public List<String> getChatRooms() {
        if (chatRooms == null) {
            chatRooms = chatmanager.getChatRooms(user);
        }
        return chatRooms;
    }

    public List<String> getAccessibleRooms() {
        if (accessibleRooms == null) {
            accessibleRooms = chatmanager.getAccessibleChatRooms(user);
        }
        return accessibleRooms;
    }

    public String getOpenRoom() {
        return openRoom;
    }

    public void setOpenRoom(String iOpenRoom) {
        this.openRoom = iOpenRoom;
    }

    public void setChatRooms(List<String> chatRooms) {
        this.chatRooms = chatRooms;
    }

    public void setAccessibleRooms(List<String> accessibleRooms) {
        this.accessibleRooms = accessibleRooms;
    }

}
