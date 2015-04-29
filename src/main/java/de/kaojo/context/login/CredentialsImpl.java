/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.kaojo.context.login;

import javax.enterprise.context.RequestScoped;

/**
 *
 * @author julian
 */
@RequestScoped
@DefaultCredentials
public class CredentialsImpl implements Credentials {

    String loginId;
    String password;

    @Override
    public String getLoginId() {
        return loginId;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Credentials build(String loginId, String password) {
        this.loginId = loginId;
        this.password = password;

        return this;
    }

}
