/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.kaojo.ejb.dto;

import de.kaojo.ejb.dto.interfaces.Credentials;

/**
 *
 * @author julian
 */
public class CredentialsImpl implements Credentials {

    private String loginId;
    private String password;

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
