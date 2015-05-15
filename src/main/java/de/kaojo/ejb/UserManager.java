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
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author julian
 */
@Stateless
public class UserManager {

    @PersistenceContext(unitName = "postgres")
    EntityManager em;

    public UserDTO getUserFromDB(Credentials credentials) {
        UserEntity userEntity = em.find(UserEntity.class, credentials.getLoginId());
        if (userEntity == null) {
            return null;
        }
        if (userEntity.getPassword().equals(credentials.getPassword())) {
            return null;
        }
        
        return mapUserEntityToUserDTO(userEntity);

    }

    public boolean userAllreadyExists(String userId) {
        UserEntity uE = em.find(UserEntity.class, userId);
        return (uE != null);
    }

    public UserDTO createNewUser(NewUserRequest newUserRequest) {
        UserEntity userEntity = new UserEntity();
        userEntity.setActive(Boolean.FALSE);
        userEntity.setBirthday(newUserRequest.getBirthday());
        userEntity.setDisplayName(newUserRequest.getDisplayName());
        userEntity.setFirstName(newUserRequest.getFirstName());
        userEntity.setLastName(newUserRequest.getLastName());
        userEntity.setMail(newUserRequest.getMail());
        userEntity.setPassword(newUserRequest.getPassword());
        try {
            em.getTransaction().begin();
            em.persist(userEntity);
            em.getTransaction().commit();
        } catch (Exception e) {
            return null;
        }
        return mapUserEntityToUserDTO(userEntity);
    }

    private UserDTO mapUserEntityToUserDTO(UserEntity userEntity) {

        UserDTO.UserDTOBuilder userDTOBuilder = new UserDTO.UserDTOBuilder();
        UserDTO userDTO
                = userDTOBuilder.withActive(userEntity.getActive())
                .withBirthday(userEntity.getBirthday())
                .withDisplayName(userEntity.getDisplayName())
                .withFirstName(userEntity.getFirstName())
                .withLastName(userEntity.getLastName())
                .withMail(userEntity.getMail())
                .withUserId(userEntity.getId()).build();
        
        return userDTO;
    }

}
