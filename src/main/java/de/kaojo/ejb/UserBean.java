/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.kaojo.ejb;

import de.kaojo.context.login.Credentials;
import javax.ejb.Stateful;

/**
 *
 * @author jwinter
 */
@Stateful
public class UserBean implements User {

    @Override
    public void initialize(Credentials credentials) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean updateUser(de.kaojo.context.user.User contextUser) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
