package de.kaojo.ejb.dto;

import de.kaojo.ejb.dto.interfaces.UserIdChatRequest;

/**
 *
 * @author jwinter
 */
public class UserIdChatRequestImpl implements UserIdChatRequest {

    private final Long userId;

    public UserIdChatRequestImpl(Long userId) {
        this.userId = userId;
    }

    @Override
    public Long getUserId() {
        return this.userId;
    }

}
