/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.kaojo.ejb;

import de.kaojo.ejb.dto.interfaces.Credentials;
import de.kaojo.ejb.dto.NewUserRequest;
import de.kaojo.ejb.dto.UserDTO;
import de.kaojo.persistence.entities.AccountEntity;
import de.kaojo.persistence.entities.ContactEntity;
import de.kaojo.persistence.entities.RolesEntity;
import de.kaojo.persistence.entities.enums.Roles;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author julian
 */
@Stateless
public class UserManagerImpl implements UserManager {

    @PersistenceContext(unitName = "postgres")
    EntityManager em;

    @Override
    public UserDTO getUserFromDB(Credentials credentials) {
        AccountEntity userEntity = getUserByName(credentials.getLoginId());
        if (userEntity == null) {
            return null;
        }
        if (!userEntity.getPassword().equals(credentials.getPassword())) {
            return null;
        }

        return mapUserEntityToUserDTO(userEntity);

    }

    @Override
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

    @Override
    public boolean emailAllreadyExists(String email) {
        List<ContactEntity> contacts = getContactsByEmail(email);
        if (contacts.isEmpty()) {
            return false;
        }
        if (contacts.size() != 1) {
            return true;
        }
        ContactEntity contact = contacts.get(0);
        return contact.getEmail().equals(email);
    }

    @Override
    public UserDTO createNewUser(NewUserRequest newUserRequest) {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setUserName(newUserRequest.getUserName());
        accountEntity.setActive(Boolean.FALSE);
        accountEntity.setDisplayName(newUserRequest.getDisplayName());
        accountEntity.setPassword(newUserRequest.getPassword());
        accountEntity.setCreationDate(new Date());
        accountEntity.setLastModified(new Date());
        accountEntity.setLastLogin(new Date());
        try {
            em.persist(accountEntity);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        Set<RolesEntity> rolesEntitys = getDefaultRolesEntities();
        accountEntity.setRoles(rolesEntitys);

        ContactEntity contactEntity = new ContactEntity();
        contactEntity.setEmail(newUserRequest.getEmail());
        try {
            em.persist(contactEntity);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        accountEntity.setContactEntity(contactEntity);
        return mapUserEntityToUserDTO(accountEntity);
    }

    @Override
    public UserDTO getUserWithoutPassword(String userName) {
        AccountEntity account = getUserByName(userName);
        if (account == null) {
            return null;
        }
        return mapUserEntityToUserDTO(account);
    }

    private UserDTO mapUserEntityToUserDTO(AccountEntity userEntity) {

        UserDTO.UserDTOBuilder userDTOBuilder = new UserDTO.UserDTOBuilder();
        UserDTO userDTO
                = userDTOBuilder.withActive(userEntity.getActive() == null ? false : userEntity.getActive())
                .withDisplayName(userEntity.getDisplayName())
                .withUserName(userEntity.getUserName())
                .withUserId(userEntity.getId())
                .build();

        return userDTO;
    }

    private List<AccountEntity> getUsersByName(String userName) {
        Query query = em.createQuery("SELECT ku FROM AccountEntity ku WHERE ku.userName = :userName");
        query.setParameter("userName", userName);
        query.setMaxResults(10);
        return new ArrayList<>(query.getResultList());
    }

    private List<ContactEntity> getContactsByEmail(String email) {
        Query query = em.createQuery("SELECT ce FROM ContactEntity ce WHERE ce.email = :email");
        query.setParameter("email", email);
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

    private Set<RolesEntity> getDefaultRolesEntities() {
        Query query = em.createQuery("SELECT re FROM RolesEntity re WHERE re.roles = :defaultRole");
        query.setParameter("defaultRole", Roles.user);
        query.setMaxResults(10);
        Set resultList = new HashSet(query.getResultList());
        if (resultList.isEmpty()) {
            resultList = createDefaultRoles();
        }
        return resultList;
    }

    private Set<RolesEntity> createDefaultRoles() {
        Set<RolesEntity> rolesEntitys = new HashSet<>();
        RolesEntity rolesEntity = new RolesEntity();
        rolesEntity.setRoles(Roles.user);

        try {
            em.persist(rolesEntity);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        //create maybe more default Roles

        rolesEntitys.add(rolesEntity);
        return rolesEntitys;
    }

}
