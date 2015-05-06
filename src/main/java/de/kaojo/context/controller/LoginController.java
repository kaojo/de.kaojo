/*
 * Diese Software ist Eigentum von Julian Winter
 * Alle Rechte sind vorbehalten.
 * Copyright 2015.
 */
package de.kaojo.context.controller;

import de.kaojo.context.login.Credentials;
import de.kaojo.context.login.DefaultCredentials;
import de.kaojo.context.user.User;
import de.kaojo.context.user.DefaultUser;
import de.kaojo.persistence.entities.Data;
import de.kaojo.persistence.entities.Test;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author julian
 */
@Named("loginController")
@RequestScoped
public class LoginController {

    private String loginPass;
    private String loginName;

    @Inject
    @DefaultUser
    private User user;

    @Inject
    @DefaultCredentials
    private Credentials credentials;

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

    public String getLoginName() {
        return this.loginName;
    }

    public String login() {
        Test test = new Test();
        Data data = new Data();
        data.setId(loginName);
        data.setMessage(loginName);
        data.setName(loginName);
        
        test.save(data);
        
        this.user.build(loginName, loginName, loginName);
        this.credentials = credentials.build(loginName, loginPass);
        return "chat?faces-redirect=true";
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
