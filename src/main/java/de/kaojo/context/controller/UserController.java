package de.kaojo.context.controller;

import de.kaojo.context.model.user.User;
import de.kaojo.context.model.user.DefaultUser;
import static de.kaojo.context.util.FacesContextHelper.getRequest;
import de.kaojo.ejb.UserManager;
import de.kaojo.ejb.dto.UserDTO;
import de.kaojo.ejb.exceptions.UserManagerException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author julian
 */
@Named("userController")
@SessionScoped
public class UserController implements Serializable {

    @Inject
    @DefaultUser
    private User user;

    @EJB
    private UserManager userManger;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        if (user.getUserId() == null) {
            try {
                initUserFromDB();
            } catch (UserManagerException ex) {
                Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return user;
    }

    public void changeDisplayName() {
        this.user.setDisplayName(name);
    }

    private void initUserFromDB() throws UserManagerException {
        FacesContext context = FacesContext.getCurrentInstance();
        if (context != null) {
            HttpServletRequest request = getRequest();
            String remoteUser = request.getRemoteUser();
            if (remoteUser != null) {
                UserDTO userDto = userManger.getUserWithoutPassword(remoteUser);
                user = user.build(userDto);
            }
        }
    }

}
