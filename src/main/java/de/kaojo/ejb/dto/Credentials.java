/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.kaojo.ejb.dto;

/**
 *
 * @author julian
 */
public interface Credentials {

    public Credentials build(String loginId, String password);

    public String getLoginId();

    public String getPassword();

}