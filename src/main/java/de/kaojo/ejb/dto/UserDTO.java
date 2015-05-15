/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.kaojo.ejb.dto;

import java.util.Date;

/**
 *
 * @author jwinter
 */
public class UserDTO {

    private final Boolean active;
    private final Date birthday;
    private final String displayName;
    private final String firstName;
    private final String lastName;
    private final String mail;
    private final String userId;

    public String getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Boolean getActive() {
        return active;
    }

    public Date getBirthday() {
        return birthday;
    }

    public String getMail() {
        return mail;
    }

    private UserDTO(Boolean active, Date birthday, String displayName, String firstName, String lastName, String mail, String userId) {
        this.active = active;
        this.birthday = birthday;
        this.displayName = displayName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mail = mail;
        this.userId = userId;
    }

    public static class UserDTOBuilder {

        private Boolean active;
        private Date birthday;
        private String displayName;
        private String firstName;
        private String lastName;
        private String mail;
        private String userId;
        
        
        public UserDTOBuilder() {
        }

        public UserDTOBuilder withActive(Boolean active) {
            this.active = active;
            return this;
        }

        public UserDTOBuilder withBirthday(Date birthday) {
            this.birthday = birthday;
            return this;
        }

        public UserDTOBuilder withDisplayName(String displayName) {
            this.displayName = displayName;
            return this;
        }

        public UserDTOBuilder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserDTOBuilder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserDTOBuilder withMail(String mail) {
            this.mail = mail;
            return this;
        }

        public UserDTOBuilder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public UserDTO build() {
            
            return new UserDTO(active, birthday, displayName, firstName, lastName, mail, userId);
        }
    }
}
