package de.kaojo.ejb.dto;

import de.kaojo.ejb.dto.interfaces.ChatRoomNameChatRequest;

/**
 *
 * @author jwinter
 */
public class ChatRoomNameChatRequestImpl implements ChatRoomNameChatRequest {

    private final String chatRoomName;

    public ChatRoomNameChatRequestImpl(String chatRoomName) {
        this.chatRoomName = chatRoomName;
    }

    @Override
    public String getChatRoomName() {
        return chatRoomName;
    }

}
