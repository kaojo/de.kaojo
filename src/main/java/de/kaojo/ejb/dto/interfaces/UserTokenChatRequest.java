/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.kaojo.ejb.dto.interfaces;

/**
 *
 * @author jwinter
 */
public interface UserTokenChatRequest {

    public UserTokenChatRequest UserTokenChatRequest(String userToken);
    
    public String getUserToken();

}