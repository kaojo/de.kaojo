package de.kaojo.context.controller;

import de.kaojo.chat.model.ChatRoom;
import de.kaojo.chat.model.ChatUser;
import de.kaojo.chat.model.Message;
import de.kaojo.context.model.user.User;
import de.kaojo.context.model.user.DefaultUser;
import de.kaojo.context.util.FacesContextHelper;
import de.kaojo.ejb.ChatManager;
import de.kaojo.ejb.dto.ChatRoomChatRequestImpl;
import de.kaojo.ejb.dto.AccountIdChatRequestImpl;
import de.kaojo.ejb.dto.ChatRoomNameChatRequestImpl;
import de.kaojo.ejb.dto.NewChatRoomChatRequestImpl;
import de.kaojo.ejb.dto.interfaces.ChatRoomChatRequest;
import de.kaojo.ejb.dto.interfaces.AccountIdChatRequest;
import de.kaojo.ejb.dto.interfaces.NewChatRoomChatRequest;
import de.kaojo.ejb.exceptions.ChatManagerException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
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

	private static final long serialVersionUID = 1L;
	private static final String CHAT_PAGE_REDIRECT = "chat?faces-redirect=true;";
    private static final String CHAT_PAGE_NO_REDIRECT = "chat";
    private static final Logger LOG = Logger.getLogger(ChatController.class.getName());

    private String openRoom;
    private boolean openPublicRoom;
    private String joinRoom;
    private List<ChatRoom> joindChatRooms = new ArrayList<ChatRoom>();
    private List<ChatRoom> accessibleRooms = new ArrayList<ChatRoom>();

    @Inject
    @DefaultUser
    private User user;

    @EJB
    private ChatManager chatManager;

    public String openChatRoom() {
        if (openRoom == null || openRoom.isEmpty()) {
            addMessage("You have to enter a name for your chatroom.");
            return CHAT_PAGE_NO_REDIRECT;
        }
        NewChatRoomChatRequest request = new NewChatRoomChatRequestImpl(user.getUserId(), openRoom, openPublicRoom);
        boolean result = false;
        try {
            result = chatManager.createNewChatRoom(request);
        } catch (ChatManagerException ex) {
            addMessage("Error opening ChatRoom '" + openRoom + "'");
            LOG.log(Level.SEVERE, null, ex);
            return CHAT_PAGE_NO_REDIRECT;
        }
        if (result) {
            ChatRoomNameChatRequestImpl chatRequest = new ChatRoomNameChatRequestImpl(openRoom);
            try {
                ChatRoom chatRoom = chatManager.getChatRoomByChatRoomName(chatRequest);
                joindChatRooms.add(chatRoom);
            } catch (ChatManagerException ex) {
                addMessage("Error opening ChatRoom '" + openRoom + "'");
                LOG.log(Level.SEVERE, null, ex);
            }
            return CHAT_PAGE_REDIRECT;
        }
        return CHAT_PAGE_NO_REDIRECT;
    }

    public String joinChatRoom() {
        if (joinRoom == null) {
            return CHAT_PAGE_REDIRECT;
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
                if (chatManager.addUserToChatRoom(chatRequest)) {
                    ChatRoom chatRoom = getChatRoomFromDB(chatRoomId);
                    joindChatRooms.add(chatRoom);
                }
            } catch (ChatManagerException ex) {
                LOG.log(Level.SEVERE, null, ex);
                addMessage("Can't join ChatRoom with name '" + joinRoom + "'");
            }
            return "chat?faces-redirect=true;";
        }
        addMessage("Error happend!");
        return "chat";
    }

    public String leaveChatRoom(ChatRoom chatRoom) {
    	
    	Long chatRoomId = chatRoom.getId();
    	
        if (chatRoomId != null) {
            Long currentUserId = user.getUserId();
			ChatRoomChatRequest chatRoomChatRequest = new ChatRoomChatRequestImpl(chatRoomId, currentUserId);
			try {
				if (chatManager.removeUserFromChatRoom(chatRoomChatRequest)) {

					AccountIdChatRequest accountIdChatRequest = new AccountIdChatRequestImpl(user.getUserId());
					this.joindChatRooms = chatManager.getChatRooms(accountIdChatRequest);			
				}
			} catch (ChatManagerException ex) {
				Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
				addMessage("Can't remove user with username '" + user.getUserName() + "' from ChatRoom with name '"+ chatRoom.getName() + "'");
			}

            return "chat?faces-redirect=true;";
        }
        
        addMessage("Error happend!");
        return "chat";
    }

    public void loadMoreMessages(String chatRoom) {
        ChatRoomNameChatRequestImpl chatRequest = new ChatRoomNameChatRequestImpl(chatRoom);
        List<Message> oldMessages = new ArrayList<>();
        try {
            oldMessages = chatManager.getOldMessages(chatRequest);
        } catch (ChatManagerException ex) {
            addMessage("Old Messages couldn't get loaded");
            LOG.log(Level.SEVERE, null, ex);
        }
        ChatRoom room = getChatRoomByName(chatRoom);
        if (room != null) {
            room.setMessageHistory(oldMessages);
        }
    }

    private void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public List<ChatRoom> getJoindChatRooms() {
        if (joindChatRooms.isEmpty()) {
            AccountIdChatRequest chatRequest = new AccountIdChatRequestImpl(user.getUserId());
            try {
                joindChatRooms = chatManager.getChatRooms(chatRequest);
            } catch (ChatManagerException ex) {
                LOG.log(Level.SEVERE, null, ex);
            }
        }
        return joindChatRooms;
    }

    public List<ChatRoom> getAccessibleRooms() {
        AccountIdChatRequest chatRequest = new AccountIdChatRequestImpl(user.getUserId());
        try {
            accessibleRooms = chatManager.getAccessibleChatRooms(chatRequest);
        } catch (ChatManagerException ex) {
            FacesContextHelper.manageException(ex);
            LOG.log(Level.SEVERE, null, ex);
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
        this.joindChatRooms = chatRooms;
    }

    public void setAccessibleRooms(List<ChatRoom> accessibleRooms) {
        this.accessibleRooms = accessibleRooms;
    }

    public boolean isOpenPublicRoom() {
        return openPublicRoom;
    }

    public void setOpenPublicRoom(boolean openPublicRoom) {
        this.openPublicRoom = openPublicRoom;
    }

    private ChatRoom getChatRoomByName(String chatRoom) {
        for (ChatRoom room : joindChatRooms) {
            if (room.getName().equals(chatRoom)) {
                return room;
            }
        }
        return null;
    }

    private ChatRoom getChatRoomFromDB(Long chatRoomId) throws ChatManagerException {
        ChatRoomChatRequestImpl chatRequest = new ChatRoomChatRequestImpl(chatRoomId, user.getUserId());
        return chatManager.getChatRoomByChatRoomId(chatRequest);
    }

    public String joinRoomVisible() {
        if (accessibleRooms.isEmpty()) {
            return "display:none";
        }
        return null;
    }

}
