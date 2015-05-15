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
public class NewUserRequest {

    private final Date birthday;
    private final String displayName;
    private final String firstName;
    private final String lastName;
    private final String mail;
    private final String password;
    private final String userId;

    private NewUserRequest(Date birthday, String displayName, String firstName, String lastName, String mail, String password, String userId) {
        this.birthday = birthday;
        this.displayName = displayName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mail = mail;
        this.password = password;
        this.userId = userId;
    }

    public Date getBirthday() {
        return birthday;
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

    public String getMail() {
        return mail;
    }

    public String getPassword() {
        return password;
    }

    public String getUserId() {
        return userId;
    }

    public static class NewUserRequestBuilder {

        private Date birthday;
        private String displayName;
        private String firstName;
        private String lastName;
        private final String mail;
        private final String password;
        private final String userId;

        public NewUserRequestBuilder(String userId, String mail, String password) {
            this.userId = userId;
            this.mail = mail;
            this.password = password;
        }
        
        public NewUserRequestBuilder withBirthday(Date birthday) {
            this.birthday = birthday;
            return this;
        }
        
        public NewUserRequestBuilder withDisplayName(String displayName) {
            this.displayName = displayName;
            return this;
        }
        
        public NewUserRequestBuilder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }
        
        public NewUserRequestBuilder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }
        
        public NewUserRequest build() {
            return new NewUserRequest(birthday,displayName,firstName,lastName,mail,password,userId);
        }

    }

}
