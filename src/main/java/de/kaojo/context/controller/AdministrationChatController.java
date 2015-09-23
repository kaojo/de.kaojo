/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.kaojo.context.controller;

import de.kaojo.chat.model.ChatRoom;
import de.kaojo.context.model.user.DefaultUser;
import de.kaojo.context.model.user.User;
import de.kaojo.ejb.ChatManager;
import de.kaojo.ejb.dto.AccountIdChatRequestImpl;
import de.kaojo.ejb.dto.interfaces.AccountIdChatRequest;
import de.kaojo.ejb.exceptions.ChatManagerException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author jwinter
 */
@Named("administrationChatController")
@RequestScoped
public class AdministrationChatController implements Serializable {

    @Inject
    @DefaultUser
    User user;

    @EJB
    private ChatManager chatManager;

    List<ChatRoom> allChatRooms = new ArrayList();

    public List<ChatRoom> getAllChatRooms() {
        if (allChatRooms.isEmpty()) {
            AccountIdChatRequest accountIdChatRequest = new AccountIdChatRequestImpl(user.getUserId());
            try {
                this.allChatRooms = chatManager.getAllChatRooms(accountIdChatRequest);
            } catch (ChatManagerException ex) {
                Logger.getLogger(AdministrationChatController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return allChatRooms;
    }

}
