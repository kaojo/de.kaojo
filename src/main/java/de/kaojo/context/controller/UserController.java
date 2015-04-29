/*
 * Diese Software ist Eigentum von Julian Winter
 * Alle Rechte sind vorbehalten.
 * Copyright 2015.
 */
package de.kaojo.context.controller;

import de.kaojo.context.user.User;
import de.kaojo.context.user.DefaultUser;
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
    @DefaultUser
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
