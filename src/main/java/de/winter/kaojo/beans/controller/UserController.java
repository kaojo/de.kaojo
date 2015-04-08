/*
 * Diese Software ist Eigentum von Julian Winter
 * Alle Rechte sind vorbehalten.
 * Copyright 2015.
 */
package de.winter.kaojo.beans.controller;

import de.winter.kaojo.beans.user.User;
import de.winter.kaojo.beans.user.UserQ;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author julian
 */
@Named("userController")
@RequestScoped
public class UserController implements Serializable {
    
    @Inject
    @UserQ
    private User user;
    
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }
    public void changeDisplayName(){
        this.user.setDisplayName(name);
    }
    
}
