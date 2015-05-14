/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.kaojo.ejb.dto;

/**
 *
 * @author jwinter
 */
public class UserRequest {
    
    private final String userId;
    private final String passwordMd5;
    
    public UserRequest(String userId, String passwordMd5) {
        this.userId = userId;
        this.passwordMd5 = passwordMd5;
    }
    
    public String getUserName() {
        return this.userId;
    }
    
    public String getPasswordMd5() {
        return this.passwordMd5;
    }
}
