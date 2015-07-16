package de.kaojo.ejb.dto;

import de.kaojo.chat.Message;
import de.kaojo.ejb.dto.interfaces.MessageChatRequest;

/**
 *
 * @author jwinter
 */
public class MessageChatRequestImpl implements MessageChatRequest {

    private final Long userId;
    private final Long chatRoomId;
    private final Message message;

    public MessageChatRequestImpl(Long userId, Long chatRoomId, Message message) {
        this.userId = userId;
        this.chatRoomId = chatRoomId;
        this.message = message;
    }

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public Long getChatRoomId() {
        return chatRoomId;
    }

    @Override
    public Message getMessage() {
        return message;
    }

}
