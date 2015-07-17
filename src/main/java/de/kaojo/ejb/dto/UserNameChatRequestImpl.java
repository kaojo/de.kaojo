package de.kaojo.ejb.dto;

import de.kaojo.ejb.dto.interfaces.UserNameChatRequest;

/**
 *
 * @author jwinter
 */
public class UserNameChatRequestImpl implements UserNameChatRequest {

    private final String userName;

    public UserNameChatRequestImpl(String userName) {
        this.userName = userName;
    }

    @Override
    public String getUserName() {
        return this.userName;
    }

}
