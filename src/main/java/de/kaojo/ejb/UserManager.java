/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.kaojo.ejb;

import de.kaojo.ejb.dto.Credentials;
import de.kaojo.ejb.dto.NewUserRequest;
import de.kaojo.ejb.dto.UserDTO;
import de.kaojo.persistence.entities.AccountEntity;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author julian
 */
@Stateless
public class UserManager {

    @PersistenceContext(unitName = "postgres")
    EntityManager em;

    public UserDTO getUserFromDB(Credentials credentials) {
        AccountEntity userEntity = getUserByName(credentials.getLoginId());
        if (userEntity == null) {
            return null;
        }
        if (userEntity.getPassword().equals(credentials.getPassword())) {
            return null;
        }

        return mapUserEntityToUserDTO(userEntity);

    }

    public boolean userAllreadyExists(String userName) {
        List<AccountEntity> users = getUsersByName(userName);
        if (users.isEmpty()) {
            return false;
        }
        if (users.size() != 1) {
            return true;
        }
        AccountEntity user = users.get(0);
        return user.getUserName().equals(userName);
    }

    public UserDTO createNewUser(NewUserRequest newUserRequest) {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setUserName(newUserRequest.getUserName());
        accountEntity.setActive(Boolean.FALSE);
        accountEntity.setDisplayName(newUserRequest.getDisplayName());
        accountEntity.setPassword(newUserRequest.getPassword());
        try {
            em.persist(accountEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return mapUserEntityToUserDTO(accountEntity);
    }

    private UserDTO mapUserEntityToUserDTO(AccountEntity userEntity) {

        UserDTO.UserDTOBuilder userDTOBuilder = new UserDTO.UserDTOBuilder();
        UserDTO userDTO
                = userDTOBuilder.withActive(userEntity.getActive() == null ? false : userEntity.getActive())
                .withDisplayName(userEntity.getDisplayName())
                .withUserName(userEntity.getUserName())
                .build();

        return userDTO;
    }

    private List<AccountEntity> getUsersByName(String userName) {
        Query query = em.createQuery("SELECT ku FROM UserEntity ku WHERE ku.userName = :userName");
        query.setParameter("userName", userName);
        query.setMaxResults(10);
        return new ArrayList<>(query.getResultList());
    }
    
    private AccountEntity getUserByName(String userName) {
        List<AccountEntity> usersByName = getUsersByName(userName);
        if (usersByName.isEmpty() | usersByName.size() != 1) {
            return null;
        }
        return usersByName.get(0);
    }

}
