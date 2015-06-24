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
import de.kaojo.security.cipher.SecureHashingAlgorithmHelper;
import java.security.NoSuchAlgorithmException;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 * Content dependency injection
 *
 * @author julian
 */
@Named("loginController")
@RequestScoped
public class LoginController {

    private String loginPass;
    private String loginName;
    private String email;
    private String emailConfirm;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmailConfirm() {
        return emailConfirm;
    }

    public void setEmailConfirm(String emailConfirm) {
        this.emailConfirm = emailConfirm;
    }

    public UserManager getUserManager() {
        return userManager;
    }

    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    public String loginButton() throws ServletException {
        Credentials credentials = new CredentialsImpl();
        credentials.build(loginName, loginPass);
        UserDTO userDTO = userManager.getUserFromDB(credentials);

        return performLogin(userDTO, loginName, loginPass);
    }

    public String register() throws NoSuchAlgorithmException, ServletException {
        if (!checkRegisterInput()) {
            return null;
        }
        // Store Account in DB
        String shaPassword = getHashedPassword(pass);
        NewUserRequest.NewUserRequestBuilder newUserRequestBuilder
                = new NewUserRequest.NewUserRequestBuilder(userName, email, shaPassword);
        NewUserRequest newUserRequest = newUserRequestBuilder.build();
        UserDTO userDTO = userManager.createNewUser(newUserRequest);

        return performLogin(userDTO, userName, pass);
    }

    private String performLogin(UserDTO userDTO, String userName, String password) throws ServletException {
        // If creating user failed, stay on Page and display a message
        if (userDTO == null) {
            addMessage("Fehlschlag", "Ein technisches Problem ist aufgetreten.");
            return null;
        }
        // Perform Login into Applikation
        this.user.build(userDTO);
        HttpServletRequest request = getRequest();
        request.login(userName, password);
        return "/pages/user/chat?faces-redirect=true;";
    }

    private boolean checkRegisterInput() {
        if (null == pass | !pass.equals(passConfirm)) {
            addMessage("Fehlschlag", "Die Passwörter stimmen nicht überein");
            return false;
        }
        if (null == email | !email.equals(emailConfirm)) {
            addMessage("Fehlschlag", "Die E-mail-Adressen stimmen nicht überein");
            return false;
        }
        if (userManager.userAllreadyExists(userName)) {
            addMessage("Fehlschlag", "Der Benutzername existiert bereits. Versuche es mit einem anderen Benutzernamen erneut");
            return false;
        }
        if (userManager.emailAllreadyExists(email)) {
            addMessage("Fehlschlag", "Die Email-Adressse existiert bereits. Versuche es mit einer anderen Email-Adressse erneut");
            return false;
        }
        return true;
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

    private String getHashedPassword(String pass) throws NoSuchAlgorithmException {
        return SecureHashingAlgorithmHelper.hashSHA256(pass);
    }

    public static HttpServletRequest getRequest() {
        HttpServletRequest request
                = (HttpServletRequest) FacesContext
                .getCurrentInstance()
                .getExternalContext()
                .getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }
}
