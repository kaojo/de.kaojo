/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.kaojo.ejb;

import de.kaojo.ejb.dto.Credentials;
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

    @PersistenceContext
    EntityManager em;

    public UserDTO getUserFromDB(Credentials credentials) {
        UserEntity userEntity = em.find(UserEntity.class, credentials.getLoginId());
        if (userEntity == null) {
            return null;
        }
        if ( userEntity.getPassword().equals(credentials.getPassword())) {
            return null;
        }
        UserDTO userDTO = new UserDTO();
        userDTO.getBuilder().withActive(userEntity.getActive())
                .withBirthday(userEntity.getBirthday())
                .withDisplayName(userEntity.getDisplayName())
                .withFirstName(userEntity.getFirstName())
                .withLastName(userEntity.getLastName())
                .withMail(userEntity.getMail())
                .withUserId(userEntity.getId()).build();
        return userDTO;

    }

}
