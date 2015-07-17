package de.kaojo.ejb.dto;

import de.kaojo.ejb.dto.interfaces.AccountIdChatRequest;

/**
 *
 * @author jwinter
 */
public class AccountIdChatRequestImpl implements AccountIdChatRequest {

    private final Long accountId;

    public AccountIdChatRequestImpl(Long accountId) {
        this.accountId = accountId;
    }

    @Override
    public Long getAccountId() {
        return this.accountId;
    }

}
