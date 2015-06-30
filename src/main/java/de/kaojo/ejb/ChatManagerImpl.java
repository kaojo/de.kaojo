/*
 * Diese Software ist Eigentum von Julian Winter
 * Alle Rechte sind vorbehalten.
 * Copyright 2015.
 */
package de.kaojo.ejb;

import de.kaojo.chat.ChatEvent;
import de.kaojo.chat.JoinedEvent;
import de.kaojo.chat.LeftEvent;
import de.kaojo.context.controller.DefaultChatManager;
import de.kaojo.context.user.User;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;

/**
 *
 * @author julian.winter
 */
@SessionScoped
@DefaultChatManager
public class ChatManagerImpl implements Serializable, ChatManager {

    /**
     * Method performs all the necessary task when a user joins a chatRoom
     * update DB
     *
     * @param chatEvent
     */
    @Override
    public void userjoined(@Observes @JoinedEvent ChatEvent chatEvent) {
        System.out.println("ChatEvent.name : " +chatEvent.getName());
        System.out.println("ChatEvent.room : " +chatEvent.getChatRoom());
    }

    /**
     * Method performs all the necessary task when a user leaves a chatRoom
     * ipdate DB
     *
     * @param chatEvent
     */
    @Override
    public void userleft(@Observes @LeftEvent ChatEvent chatEvent) {
        System.out.println("ChatEvent.name : " +chatEvent.getName());
        System.out.println("ChatEvent.room : " +chatEvent.getChatRoom());
    }

    /**
     * Method that return a List<String> of all Chatrooms the User is currently
     * a Member off
     *
     * @param user
     * @return
     */
    @Override
    public List<String> getChatRooms(User user) {
        return Arrays.asList("ZimmerA", "ZimmerB");
    }

    /**
     * Method that returns a List<String> of all Chatrooms the User is allowed
     * to join
     *
     * @param user
     * @return
     */
    @Override
    public List<String> getAccessibleChatRooms(User user) {
        return Arrays.asList("Zimmer1", "Zimmer2");
    }

}
