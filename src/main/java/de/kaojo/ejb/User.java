/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.kaojo.ejb;

import de.kaojo.context.login.Credentials;
import javax.ejb.Local;

/**
 *
 * @author jwinter
 */
@Local
public interface User {

    public void initialize(Credentials credentials);

    public boolean updateUser(de.kaojo.context.user.User contextUser);
}
