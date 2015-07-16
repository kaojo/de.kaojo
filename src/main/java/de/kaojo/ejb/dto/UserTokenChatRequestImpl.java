package de.kaojo.ejb.dto;

import de.kaojo.ejb.dto.interfaces.UserTokenChatRequest;

/**
 *
 * @author jwinter
 */
public class UserTokenChatRequestImpl implements UserTokenChatRequest {

    private final String userToken;

    public UserTokenChatRequestImpl(String userToken) {
        this.userToken = userToken;
    }

    @Override
    public String getUserToken() {
        return this.userToken;
    }

}
