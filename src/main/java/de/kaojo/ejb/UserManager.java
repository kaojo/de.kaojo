/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.kaojo.ejb;

import de.kaojo.ejb.dto.NewUserRequest;
import de.kaojo.ejb.dto.UserDTO;
import de.kaojo.ejb.dto.interfaces.Credentials;
import javax.ejb.Local;

/**
 *
 * @author jwinter
 */
@Local
public interface UserManager {

    UserDTO createNewUser(NewUserRequest newUserRequest);

    boolean emailAllreadyExists(String email);

    UserDTO getUserFromDB(Credentials credentials);

    boolean userAllreadyExists(String userName);
    
}
