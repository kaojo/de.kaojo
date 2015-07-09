/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.kaojo.persistence.entities;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

/**
 *
 * @author jwinter
 */
@Entity
@Table(name = "PERSON_DATA")
public class PersonEntity extends AbstractEntity<Long> {

    @Column
    @Temporal(javax.persistence.TemporalType.DATE)
    @Past
    private Date birthday;
    
    @Column
    @Size(max=32)
    private String firstName;

    @Column
    @Size(max=32)
    private String lastName;

    @Column
    private String maritalStatus; // ENUM

    @Column
    @Size(max=32)
    private String middleName;

    @Column
    private String salutation; // ENUM

    @Column
    private String title; // ENUM    

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getSalutation() {
        return salutation;
    }

    public void setSalutation(String salutation) {
        this.salutation = salutation;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
