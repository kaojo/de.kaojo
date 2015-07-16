package de.kaojo.ejb.dto;

import de.kaojo.ejb.dto.interfaces.ChatRoomChatRequest;

/**
 *
 * @author jwinter
 */
public class ChatRoomChatRequestImpl implements ChatRoomChatRequest {

    private final long chatRoomId;
    private final long userId;

    public ChatRoomChatRequestImpl(long chatRoomId, long userId) {
        this.chatRoomId = chatRoomId;
        this.userId = userId;
    }

    @Override
    public Long getChatRoomId() {
        return chatRoomId;
    }

    @Override
    public Long getUserId() {
        return userId;
    }

}
