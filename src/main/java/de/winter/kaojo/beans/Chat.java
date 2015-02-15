/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.winter.kaojo.beans;

import java.io.Serializable;
import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;

/**
 *
 * @author julian
 */
@ManagedBean
@SessionScoped
public class Chat implements Serializable {

    /**
     * Creates a new instance of Chat
     */
    public Chat() {
    }

}
