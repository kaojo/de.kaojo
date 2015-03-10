package de.winter.kaojo.beans.controller;

import de.winter.kaojo.beans.user.User;
import de.winter.kaojo.beans.user.UserQ;
import de.winter.kaojo.chat.Author;
import de.winter.kaojo.chat.ChatRoom;
import de.winter.kaojo.chat.Message;
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
    private String messageText;

    @Inject
    @UserQ
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public String sendMessage(ChatRoom chatRoom) {
        Message message = new Message(new Author(user.getUserId(), user.getDisplayName()), messageText);
        chatRoom.receiveMessage(message);
        return "chat";
    }

    public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public String leaveChatRoom(ChatRoom chatRoom) {
        chatRoom.removeUser(user);
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

    public List<String> getAccessibleRooms() {
        return accessibleRooms;
    }

    public void setAccessibleRooms(List<String> accessibleRooms) {
        this.accessibleRooms = accessibleRooms;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

}
