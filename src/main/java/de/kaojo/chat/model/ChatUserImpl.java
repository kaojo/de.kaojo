package de.kaojo.chat.model;

import de.kaojo.persistence.entities.AccountEntity;

/**
 *
 * @author jwinter
 */
public class ChatUserImpl implements ChatUser {

    private String displayName;
    private Long id;

    ChatUserImpl(AccountEntity accountEntity) {
        id = accountEntity.getId();
        displayName = accountEntity.getDisplayName() == null ? accountEntity.getUserName() : accountEntity.getDisplayName();
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
