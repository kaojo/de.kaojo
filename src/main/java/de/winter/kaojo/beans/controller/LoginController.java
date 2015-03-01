/*
 * Diese Software ist Eigentum von Julian Winter
 * Alle Rechte sind vorbehalten.
 * Copyright 2015.
 */
package de.winter.kaojo.beans.controller;

import javax.faces.bean.ManagedBean;

/**
 *
 * @author julian
 */
@ManagedBean(name = "loginController")
public class LoginController {

    private String loginPass;
    private String loginName;

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

    public String login() {

        return "chat";
    }

}
