package de.kaojo.ejb.dto;

import de.kaojo.ejb.dto.interfaces.NewChatRoomChatRequest;

/**
 *
 * @author jwinter
 */
public class NewChatRoomChatRequestImpl implements NewChatRoomChatRequest {

    private final Long AccountId;
    private final String roomName;

    public NewChatRoomChatRequestImpl(Long AccountId, String roomName) {
        this.AccountId = AccountId;
        this.roomName = roomName;
    }

    @Override
    public Long getAccountId() {
        return AccountId;
    }

    @Override
    public String getRoomName() {
        return roomName;
    }

}
