package de.kaojo.ejb;

import de.kaojo.ejb.dto.interfaces.Credentials;
import de.kaojo.ejb.dto.NewUserRequest;
import de.kaojo.ejb.dto.UserDTO;
import de.kaojo.persistence.entities.AccountEntity;
import de.kaojo.persistence.entities.ContactEntity;
import de.kaojo.persistence.entities.RolesEntity;
import de.kaojo.persistence.entities.enums.Roles;
import de.kaojo.persistence.repositories.AccountRepository;
import de.kaojo.persistence.repositories.ContactRepository;
import de.kaojo.persistence.repositories.RolesRepository;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.Query;

/**
 *
 * @author julian
 */
@Stateless
public class UserManagerImpl implements UserManager {

    @Inject
    private AccountRepository accountRepository;

    @Inject
    private ContactRepository contactRepository;

    @Inject
    private RolesRepository rolesRepository;

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
            accountRepository.save(accountEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        Set<RolesEntity> rolesEntitys = getDefaultRolesEntities();
        accountEntity.setRoles(rolesEntitys);

        ContactEntity contactEntity = new ContactEntity();
        contactEntity.setEmail(newUserRequest.getEmail());
        try {
            contactRepository.save(contactEntity);
        } catch (Exception e) {
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
        return accountRepository.findByUserName(userName);
    }

    private List<ContactEntity> getContactsByEmail(String email) {
        return contactRepository.findByEmail(email);
    }

    private AccountEntity getUserByName(String userName) {
        List<AccountEntity> accounts = getUsersByName(userName);
        if (accounts.isEmpty() | accounts.size() != 1) {
            return null;
        }
        return accounts.get(0);
    }

    private Set<RolesEntity> getDefaultRolesEntities() {
        return rolesRepository.findByRole(Roles.user);
    }

}
