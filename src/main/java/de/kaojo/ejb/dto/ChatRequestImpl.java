/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.kaojo.ejb.dto;

import de.kaojo.chat.Message;
import de.kaojo.ejb.dto.interfaces.ChatRoomChatRequest;
import de.kaojo.ejb.dto.interfaces.MessageChatRequest;
import de.kaojo.ejb.dto.interfaces.UserIdChatRequest;
import de.kaojo.ejb.dto.interfaces.UserNameChatRequest;
import de.kaojo.ejb.dto.interfaces.UserTokenChatRequest;

/**
 *
 * @author jwinter
 */
public class ChatRequestImpl implements UserIdChatRequest, UserNameChatRequest, UserTokenChatRequest, ChatRoomChatRequest, MessageChatRequest {

    private Long chatRoomId;

    private String userName;

    private String userToken;

    private Long userId;

    private String chatRoomName;

    private Message message;

    /**
     * Get the value of message
     *
     * @return the value of message
     */
    public Message getMessage() {
        return message;
    }

    /**
     * Set the value of message
     *
     * @param message new value of message
     */
    public void setMessage(Message message) {
        this.message = message;
    }

    /**
     * Get the value of chatRoomName
     *
     * @return the value of chatRoomName
     */
    public String getChatRoomName() {
        return chatRoomName;
    }

    /**
     * Set the value of chatRoomName
     *
     * @param chatRoomName new value of chatRoomName
     */
    public void setChatRoomName(String chatRoomName) {
        this.chatRoomName = chatRoomName;
    }

    /**
     * Get the value of chatRoomId
     *
     * @return the value of chatRoomId
     */
    public Long getChatRoomId() {
        return chatRoomId;
    }

    /**
     * Set the value of chatRoomId
     *
     * @param chatRoomId new value of chatRoomId
     */
    public void setChatRoomId(Long chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

    /**
     * Get the value of userId
     *
     * @return the value of userId
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * Set the value of userId
     *
     * @param userId new value of userId
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * Get the value of userToken
     *
     * @return the value of userToken
     */
    public String getUserToken() {
        return userToken;
    }

    /**
     * Set the value of userToken
     *
     * @param userToken new value of userToken
     */
    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    /**
     * Get the value of userName
     *
     * @return the value of userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Set the value of userName
     *
     * @param userName new value of userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public UserNameChatRequest UserNameChatRequest(String userName) {
        this.userName = userName;
        return this;
    }

    @Override
    public UserTokenChatRequest UserTokenChatRequest(String userToken) {
        this.userToken = userToken;
        return this;
    }

    @Override
    public ChatRoomChatRequest ChatRoomChatRequest(Long chatRoomId, Long userId) {
        this.chatRoomId = chatRoomId;
        this.userId = userId;
        return this;
    }

    @Override
    public MessageChatRequest MessageChatRequest(String chatRoomName, Message message, String userName) {
        this.chatRoomName = chatRoomName;
        this.message = message;
        this.userName = userName;
        return this;
    }

    @Override
    public UserIdChatRequest UserIdChatRequest(Long userId) {
        this.userId = userId;
        return this;
    }

}
