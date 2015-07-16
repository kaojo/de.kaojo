package de.kaojo.context.controller;

import de.kaojo.chat.ChatRoom;
import de.kaojo.context.user.User;
import de.kaojo.context.user.DefaultUser;
import de.kaojo.ejb.ChatManager;
import de.kaojo.ejb.dto.ChatRoomChatRequestImpl;
import de.kaojo.ejb.dto.UserIdChatRequestImpl;
import de.kaojo.ejb.dto.interfaces.ChatRoomChatRequest;
import de.kaojo.ejb.dto.interfaces.UserIdChatRequest;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
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

    private String openRoom;
    private String joinRoom;
    private List<ChatRoom> chatRooms;
    private List<ChatRoom> accessibleRooms;
    private String userToken;

    @Inject
    @DefaultUser
    private User user;

    @EJB
    private ChatManager chatManager;

    public User getUser() {
        return user;
    }

    public String openChatRoom() {
        addMessage("Error opening ChatRoom '" + openRoom + "'");
        return "chat";
    }

    public String joinChatRoom() {
        if (joinRoom == null) {
            return "chat";
        }
        Long chatRoomId = null;
        int lastIndexOf = joinRoom.lastIndexOf(" (public)");
        String parsedRoom = joinRoom;
        if (lastIndexOf != -1) {
            parsedRoom = joinRoom.substring(0, lastIndexOf);
        }
        for (ChatRoom chatRoom : accessibleRooms) {
            if (parsedRoom.equals(chatRoom.getName())) {
                chatRoomId = chatRoom.getId();
            }
        }
        if (chatRoomId != null) {
            ChatRoomChatRequest chatRequest = new ChatRoomChatRequestImpl(chatRoomId, user.getUserId());
            chatManager.addUserToChatRoom(chatRequest);
            return "chat?faces-redirect=true;";
        }
        addMessage("Error happend!");
        return "chat";
    }

    public String leaveChatRoom(String chatRoom) {
        addMessage("Error leaving ChatRoom '" + chatRoom + "'");
        return "chat";
    }

    private void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public List<ChatRoom> getChatRooms() {
        UserIdChatRequest chatRequest = new UserIdChatRequestImpl(user.getUserId());
        chatRooms = chatManager.getChatRooms(chatRequest);
        return chatRooms;
    }

    public List<ChatRoom> getAccessibleRooms() {
        UserIdChatRequest chatRequest = new UserIdChatRequestImpl(user.getUserId());
        accessibleRooms = chatManager.getAccessibleChatRooms(chatRequest);
        return accessibleRooms;
    }

    public String getJoinRoom() {
        return joinRoom;
    }

    public void setJoinRoom(String joinRoom) {
        this.joinRoom = joinRoom;
    }

    public String getOpenRoom() {
        return openRoom;
    }

    public void setOpenRoom(String iOpenRoom) {
        this.openRoom = iOpenRoom;
    }

    public void setChatRooms(List<ChatRoom> chatRooms) {
        this.chatRooms = chatRooms;
    }

    public void setAccessibleRooms(List<ChatRoom> accessibleRooms) {
        this.accessibleRooms = accessibleRooms;
    }

    public String getUserToken() {
//        return chatManager.getUserFromToken(user.getUserName());
        return user.getDisplayName();
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

}
