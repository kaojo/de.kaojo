/*
 * Diese Software ist Eigentum von Julian Winter
 * Alle Rechte sind vorbehalten.
 * Copyright 2015.
 */
package de.kaojo.context.model.user;

import de.kaojo.ejb.dto.UserDTO;
import java.io.Serializable;
import java.util.Date;
import javax.enterprise.context.SessionScoped;

/**
 *
 * @author julian
 */
@DefaultUser
@SessionScoped
public class UserImpl implements User, Serializable {

    private Boolean active;
    private Date birthday;
    private String displayName;
    private String firstName;
    private String lastName;
    private String email;
    private Long userId;
    private String userName;

    public UserImpl() {

    }

    @Override
    public String getUserName() {
        return userName;
    }

    /**
     *
     * @return
     */
    @Override
    public String getLastName() {
        return lastName;
    }

    /**
     *
     * @param lastName
     */
    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     *
     * @return
     */
    @Override
    public String getDisplayName() {
        if (displayName == null) {
            return firstName != null ? firstName + lastName : userName;
        }
        return displayName;
    }

    /**
     *
     * @param displayName
     */
    @Override
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public User build(UserDTO userDTO) {
        if (userDTO != null) {
            this.active = userDTO.getActive();
            this.birthday = userDTO.getBirthday();
            this.displayName = userDTO.getDisplayName();
            this.firstName = userDTO.getFirstName();
            this.lastName = userDTO.getLastName();
            this.email = userDTO.getEmail();
            this.userId = userDTO.getUserId();
            this.userName = userDTO.getUserName();

            return this;
        }
        return this;
    }

    @Override
    public Boolean getActive() {
        return active;
    }

    /**
     *
     * @param active
     */
    @Override
    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public Date getBirthday() {
        return birthday;
    }

    /**
     *
     * @param birthday
     */
    @Override
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     *
     * @return email
     */
    @Override
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     */
    @Override
    public void setEmail(String mail) {
        this.email = mail;
    }

    @Override
    public Long getUserId() {
        return userId;
    }

}
