package de.kaojo.chat;

import javax.websocket.Session;

/**
 *
 * @author jwinter
 */
public class ChatUserImpl implements ChatUser {

    private final String name;
    private final Session session;

    public ChatUserImpl(String name, Session session) {
        this.name = name;
        this.session = session;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Session getSession() {
        return this.session;
    }

}
