/*
 * Diese Software ist Eigentum von Julian Winter
 * Alle Rechte sind vorbehalten.
 * Copyright 2015.
 */
package de.kaojo.context.controller;

import de.kaojo.context.user.User;
import de.kaojo.context.user.DefaultUser;
import de.kaojo.ejb.UserManager;
import de.kaojo.ejb.dto.Credentials;
import de.kaojo.ejb.dto.CredentialsImpl;
import de.kaojo.ejb.dto.UserDTO;
import javax.ejb.EJB;
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

    @EJB
    private UserManager userManager;

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
        Credentials credentials = new CredentialsImpl();
        credentials.build(loginName, loginPass);
        UserDTO userDTO = userManager.getUserFromDB(credentials);

        this.user.build(userDTO);
        return "chat?faces-redirect=true";
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
