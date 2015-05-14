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

    private Boolean active;
    private Date birthday;
    private String displayName;
    private String firstName;
    private String lastName;
    private String mail;
    private String userId;

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
    
    public UserDTOBuilder getBuilder() {
        return new UserDTOBuilder(this);
    }

    public class UserDTOBuilder {

        private Boolean active;
        private Date birthday;
        private String displayName;
        private String firstName;
        private String lastName;
        private String mail;
        private String userId;
        
        private final UserDTO userDTO;
        
        private UserDTOBuilder(UserDTO userDTO) {
            this.userDTO = userDTO;
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
            
            this.userDTO.active = this.active;
            this.userDTO.birthday = this.birthday;
            this.userDTO.displayName = this.displayName;
            this.userDTO.firstName = this.firstName;
            this.userDTO.lastName = this.lastName;
            this.userDTO.mail = this.mail;
            this.userDTO.userId = this.userId;
            
            return userDTO;
        }
    }
}
