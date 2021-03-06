/*
 * Diese Software ist Eigentum von Julian Winter
 * Alle Rechte sind vorbehalten.
 * Copyright 2015.
 */
package de.kaojo.context.controller;

import de.kaojo.context.model.user.User;
import de.kaojo.context.model.user.DefaultUser;
import static de.kaojo.context.util.FacesContextHelper.getRequest;
import de.kaojo.ejb.UserManager;
import de.kaojo.ejb.dto.interfaces.Credentials;
import de.kaojo.ejb.dto.CredentialsImpl;
import de.kaojo.ejb.dto.NewUserRequest;
import de.kaojo.ejb.dto.UserDTO;
import de.kaojo.security.util.SecureHashingAlgorithmHelper;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Content dependency injection
 *
 * @author julian
 */
@Named("loginController")
@RequestScoped
public class LoginController {

    private static final String CHAT_PAGE_REDIRECT = "/pages/user/chat?faces-redirect=true;";
    private static final String LOGOUT_PAGE_REDIRECT = "/pages/public/logout?faces-redirect=true;";
    private static final Logger LOG = Logger.getLogger(LoginController.class.getName());

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

    public String loginButton() throws ServletException, NoSuchAlgorithmException {
        if (!isLoginRequired()) {
            return CHAT_PAGE_REDIRECT;
        }
        Credentials credentials = new CredentialsImpl();
        if (loginName != null && loginPass != null) {
            String hashedPassword = getHashedPassword(loginPass);
            credentials.build(loginName, hashedPassword);
            UserDTO userDTO = userManager.getUserFromDB(credentials);

            return performLogin(userDTO, loginName, loginPass);
        }
        addMessage("Fehler beim Anmelden", "Username oder Passwort fehlt!");
        return null;
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

        LOG.log(Level.INFO, "User {0} logged in.", userName);
        return CHAT_PAGE_REDIRECT;
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

    public String logoutButton() throws ServletException {
        HttpServletRequest request = getRequest();
        request.logout();
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        session.invalidate();
        return LOGOUT_PAGE_REDIRECT;
    }

    private boolean isLoginRequired() {
        return !getRequest().isUserInRole("user");
    }
}
