/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.kaojo.ejb;

import de.kaojo.ejb.dto.NewUserRequest;
import de.kaojo.ejb.dto.UserDTO;
import de.kaojo.ejb.dto.interfaces.AccountIdChatRequest;
import de.kaojo.ejb.dto.interfaces.Credentials;
import de.kaojo.ejb.dto.interfaces.UserNameChatRequest;
import de.kaojo.ejb.exceptions.UserManagerException;
import javax.ejb.Local;

/**
 *
 * @author jwinter
 */
@Local
public interface UserManager {

    public UserDTO createNewUser(NewUserRequest newUserRequest);

    public boolean emailAllreadyExists(String email);

    public UserDTO getUserFromDB(Credentials credentials);

    public boolean userAllreadyExists(String userName);

    public UserDTO getUserWithoutPassword(String userName) throws UserManagerException;

    public String getDisplayNameFromAccountId(AccountIdChatRequest chatRequest) throws UserManagerException;

    public Long getAccountIdFromUserName(UserNameChatRequest chatRequest) throws UserManagerException;

}
