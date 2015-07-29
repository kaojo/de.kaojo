package de.kaojo.ejb.dto;

import de.kaojo.ejb.dto.interfaces.NewChatRoomChatRequest;

/**
 *
 * @author jwinter
 */
public class NewChatRoomChatRequestImpl implements NewChatRoomChatRequest {

    private final Long AccountId;
    private final String roomName;
    private final boolean publicChatRoom;

    public NewChatRoomChatRequestImpl(Long AccountId, String roomName, boolean publicChatRoom) {
        this.AccountId = AccountId;
        this.roomName = roomName;
        this.publicChatRoom = publicChatRoom;
    }

    @Override
    public Long getAccountId() {
        return AccountId;
    }

    @Override
    public String getRoomName() {
        return roomName;
    }

    @Override
    public boolean isPublicChatRoom() {
        return this.publicChatRoom;
    }

}
