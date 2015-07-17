package de.kaojo.context.controller;

import de.kaojo.chat.model.ChatRoom;
import de.kaojo.context.model.user.User;
import de.kaojo.context.model.user.DefaultUser;
import de.kaojo.ejb.ChatManager;
import de.kaojo.ejb.dto.ChatRoomChatRequestImpl;
import de.kaojo.ejb.dto.AccountIdChatRequestImpl;
import de.kaojo.ejb.dto.interfaces.ChatRoomChatRequest;
import de.kaojo.ejb.dto.interfaces.AccountIdChatRequest;
import de.kaojo.ejb.exceptions.ChatManagerException;
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

    @Inject
    @DefaultUser
    private User user;

    @EJB
    private ChatManager chatManager;

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
            try {
                chatManager.addUserToChatRoom(chatRequest);
            } catch (ChatManagerException ex) {
                System.out.println(ex.getMessage());
            }
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
        AccountIdChatRequest chatRequest = new AccountIdChatRequestImpl(user.getUserId());
        try {
            chatRooms = chatManager.getChatRooms(chatRequest);
        } catch (ChatManagerException ex) {
            System.out.println(ex.getMessage());
        }
        return chatRooms;
    }

    public List<ChatRoom> getAccessibleRooms() {
        AccountIdChatRequest chatRequest = new AccountIdChatRequestImpl(user.getUserId());
        try {
            accessibleRooms = chatManager.getAccessibleChatRooms(chatRequest);
        } catch (ChatManagerException ex) {
            System.out.println(ex.getMessage());
        }
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

}
