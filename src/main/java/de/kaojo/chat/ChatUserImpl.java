package de.kaojo.chat;

/**
 *
 * @author jwinter
 */
public class ChatUserImpl implements ChatUser {

    private String displayName;
    private Long id;

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
