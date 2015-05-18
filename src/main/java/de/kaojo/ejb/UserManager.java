/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.kaojo.ejb;

import de.kaojo.ejb.dto.Credentials;
import de.kaojo.ejb.dto.NewUserRequest;
import de.kaojo.ejb.dto.UserDTO;
import de.kaojo.persistence.entities.UserEntity;
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
        UserEntity userEntity = getUserByName(credentials.getLoginId());
        if (userEntity == null) {
            return null;
        }
        if (userEntity.getPassword().equals(credentials.getPassword())) {
            return null;
        }

        return mapUserEntityToUserDTO(userEntity);

    }

    public boolean userAllreadyExists(String userName) {
        List<UserEntity> users = getUsersByName(userName);
        if (users.isEmpty()) {
            return false;
        }
        if (users.size() != 1) {
            return true;
        }
        UserEntity user = users.get(0);
        return user.getUserName().equals(userName);
    }

    public UserDTO createNewUser(NewUserRequest newUserRequest) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(newUserRequest.getUserName());
        userEntity.setActive(Boolean.FALSE);
        userEntity.setBirthday(newUserRequest.getBirthday());
        userEntity.setDisplayName(newUserRequest.getDisplayName());
        userEntity.setFirstName(newUserRequest.getFirstName());
        userEntity.setLastName(newUserRequest.getLastName());
        userEntity.setMail(newUserRequest.getMail());
        userEntity.setPassword(newUserRequest.getPassword());
        try {
            em.persist(userEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return mapUserEntityToUserDTO(userEntity);
    }

    private UserDTO mapUserEntityToUserDTO(UserEntity userEntity) {

        UserDTO.UserDTOBuilder userDTOBuilder = new UserDTO.UserDTOBuilder();
        UserDTO userDTO
                = userDTOBuilder.withActive(userEntity.getActive() == null ? false : userEntity.getActive())
                .withBirthday(userEntity.getBirthday())
                .withDisplayName(userEntity.getDisplayName())
                .withFirstName(userEntity.getFirstName())
                .withLastName(userEntity.getLastName())
                .withMail(userEntity.getMail())
                .withUserName(userEntity.getUserName())
                .build();

        return userDTO;
    }

    private List<UserEntity> getUsersByName(String userName) {
        Query query = em.createQuery("SELECT ku FROM UserEntity ku WHERE ku.userName = :userName");
        query.setParameter("userName", userName);
        query.setMaxResults(10);
        return new ArrayList<>(query.getResultList());
    }
    
    private UserEntity getUserByName(String userName) {
        List<UserEntity> usersByName = getUsersByName(userName);
        if (usersByName.isEmpty() | usersByName.size() != 1) {
            return null;
        }
        return usersByName.get(0);
    }

}
