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
import de.kaojo.ejb.dto.NewUserRequest;
import de.kaojo.ejb.dto.UserDTO;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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
    private String mail;
    private String mailConfirm;
    private String pass;
    private String passConfirm;
    private String userName;

    @Inject
    @DefaultUser
    private User user;
    @EJB
    private UserManager userManager;

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

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

    public String getLoginPass() {
        return loginPass;
    }

    public String getPassConfirm() {
        return passConfirm;
    }

    public void setPassConfirm(String loginPassConfirm) {
        this.passConfirm = loginPassConfirm;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getMailConfirm() {
        return mailConfirm;
    }

    public void setMailConfirm(String mailConfirm) {
        this.mailConfirm = mailConfirm;
    }

    public UserManager getUserManager() {
        return userManager;
    }

    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    public String login() {
        Credentials credentials = new CredentialsImpl();
        credentials.build(loginName, loginPass);
        UserDTO userDTO = userManager.getUserFromDB(credentials);
        if (userDTO == null) {
            addMessage("Fehlschlag", "Die Zugangsdaten sind nicht korrekt.");
            return null;
        }
        this.user.build(userDTO);
        if (user.getUserName() != null) {
            return "chat?faces-redirect=true";
        }

        addMessage("Fehlschlag", "Die Zugangsdaten sind nicht korrekt.");
        return null;
    }

    public String register() {
        if (null == pass | !pass.equals(passConfirm)) {
            addMessage("Fehlschlag", "Die Passwörter stimmen nicht überein");
            return null;
        }
        if (null == mail | !mail.equals(mailConfirm)) {
            addMessage("Fehlschlag", "Die E-mail-Adressen stimmen nicht überein");
            return null;
        }
        if (userManager.userAllreadyExists(userName)) {
            addMessage("Fehlschlag", "Der Benutzername existiert bereits. Versuche es mit einem anderen Benutzernamen erneut");
            return null;
        }

        NewUserRequest.NewUserRequestBuilder newUserRequestBuilder
                = new NewUserRequest.NewUserRequestBuilder(userName, mail, pass);
        NewUserRequest newUserRequest = newUserRequestBuilder.build();
        UserDTO userDTO = userManager.createNewUser(newUserRequest);
        if (userDTO != null) {
            this.user.build(userDTO);
            return "chat?faces-redirect=true;";
        }
        addMessage("Fehlschlag", "Ein technisches Problem ist aufgetreten.");
        return null;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
