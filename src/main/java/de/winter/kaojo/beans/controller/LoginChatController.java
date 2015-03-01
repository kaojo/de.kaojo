/*
 * Diese Software ist Eigentum von Julian Winter
 * Alle Rechte sind vorbehalten.
 * Copyright 2015.
 */
package de.winter.kaojo.beans.controller;

import de.winter.kaojo.beans.user.User;
import de.winter.kaojo.beans.user.UserImpl;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author julian
 */
@Named("loginChatController")
@RequestScoped
public class LoginChatController {

    private String loginPass;
    private String loginName;
    
    @Inject
    private User user;

    /**
     * Set the value of loginPass
     *
     * @param loginPass new value of loginPass
     */
    public void setLoginPass(String loginPass) {
        this.loginPass = loginPass;
    }

    /**
     * Set the value of loginName
     *
     * @param loginName new value of loginName
     */
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
    
    public String getLoginName(){
        return this.loginName;
    }

    public String login() {
        this.user = new UserImpl();
        this.user.setDisplayName(loginName);
        this.user.setUserId(loginName);
        return "chat";
    }

}
