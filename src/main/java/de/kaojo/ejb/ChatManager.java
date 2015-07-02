/*
 * Diese Software ist Eigentum von Julian Winter
 * Alle Rechte sind vorbehalten.
 * Copyright 2015.
 */
package de.kaojo.ejb;

import de.kaojo.ejb.dto.ChatRequest;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author julian.winter
 */
@Local
public interface ChatManager {

    public List<String> getChatRooms(ChatRequest chatRequest);

    public List<String> getAccessibleChatRooms(ChatRequest chatRequest);

    public void userjoined(ChatRequest chatRequest);

    public void userleft(ChatRequest chatRequest);
    
    public void sendMessage(ChatRequest chatRequest);

}
