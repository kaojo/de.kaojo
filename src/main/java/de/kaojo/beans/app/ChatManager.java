/*
 * Diese Software ist Eigentum von Julian Winter
 * Alle Rechte sind vorbehalten.
 * Copyright 2015.
 */
package de.kaojo.beans.app;

import de.kaojo.context.user.User;
import java.util.List;
import javax.enterprise.event.Observes;

/**
 *
 * @author julian.winter
 */
public interface ChatManager {

    public List<String> getChatRooms(User user);

    public List<String> getAccessibleChatRooms(User user);

    public void userjoined(@Observes @JoinedEvent ChatEvent chatEvent);

    public void userleft(@Observes @LeftEvent ChatEvent chatEvent);

}
