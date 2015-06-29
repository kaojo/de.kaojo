/*
 * Diese Software ist Eigentum von Julian Winter
 * Alle Rechte sind vorbehalten.
 * Copyright 2015.
 */
package de.kaojo.ejb;

import de.kaojo.chat.ChatEvent;
import de.kaojo.chat.JoinedEvent;
import de.kaojo.chat.LeftEvent;
import de.kaojo.context.user.User;
import java.util.List;
import javax.ejb.Local;
import javax.enterprise.event.Observes;

/**
 *
 * @author julian.winter
 */
@Local
public interface ChatManager {

    public List<String> getChatRooms(User user);

    public List<String> getAccessibleChatRooms(User user);

    public void userjoined(@Observes @JoinedEvent ChatEvent chatEvent);

    public void userleft(@Observes @LeftEvent ChatEvent chatEvent);

}
