/*
 * Diese Software ist Eigentum von Julian Winter
 * Alle Rechte sind vorbehalten.
 * Copyright 2015.
 */
package de.kaojo.ejb;

import de.kaojo.ejb.dto.ChatRequest;
import java.util.Arrays;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author julian.winter
 */
@Stateless
public class ChatManagerImpl implements ChatManager {

    /**
     * Method performs all the necessary task when a user joins a chatRoom
     * update DB
     *
     * @param chatEvent
     */
    @Override
    public void userjoined(ChatRequest chatRequest) {
        System.out.println("userJoined, chatRequest : " +chatRequest);
    }

    /**
     * Method performs all the necessary task when a user leaves a chatRoom
     * ipdate DB
     *
     * @param chatEvent
     */
    @Override
    public void userleft(ChatRequest chatRequest) {
        System.out.println("userLeft, chatRequest : " +chatRequest);
    }

    /**
     * Method that return a List<String> of all Chatrooms the User is currently
     * a Member off
     *
     * @param user
     * @return
     */
    @Override
    public List<String> getChatRooms(ChatRequest chatRequest) {
        return Arrays.asList("test", "ZimmerB");
    }

    /**
     * Method that returns a List<String> of all Chatrooms the User is allowed
     * to join
     *
     * @param user
     * @return
     */
    @Override
    public List<String> getAccessibleChatRooms(ChatRequest chatRequest) {
        return Arrays.asList("Zimmer1", "Zimmer2");
    }

    @Override
    public void sendMessage(ChatRequest chatRequest) {
        System.out.println("sendMessage, chatRequest : " +chatRequest);
    }

}
