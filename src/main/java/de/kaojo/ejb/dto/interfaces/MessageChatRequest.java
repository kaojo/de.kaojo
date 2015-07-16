/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.kaojo.ejb.dto.interfaces;

import de.kaojo.chat.Message;

/**
 *
 * @author jwinter
 */
public interface MessageChatRequest {

    public Long getUserId();

    public Long getChatRoomId();

    public Message getMessage();

}
