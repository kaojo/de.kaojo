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
    private final String email;
    private final String password;
    private final String userName;

    private NewUserRequest(Date birthday, String displayName, String firstName, String lastName, String email, String password, String userName) {
        this.birthday = birthday;
        this.displayName = displayName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.userName = userName;
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

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }

    public static class NewUserRequestBuilder {

        private Date birthday;
        private String displayName;
        private String firstName;
        private String lastName;
        private final String email;
        private final String password;
        private final String userName;

        public NewUserRequestBuilder(String userName, String email, String password) {
            this.userName = userName;
            this.email = email;
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
            return new NewUserRequest(birthday,displayName,firstName,lastName,email,password,userName);
        }

    }

}
