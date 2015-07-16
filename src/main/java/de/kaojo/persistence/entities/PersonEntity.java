/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.kaojo.persistence.entities;

import de.kaojo.persistence.entities.enums.MaritalStatus;
import de.kaojo.persistence.entities.enums.Salutation;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
    @Size(max = 32)
    private String firstName;

    @Column
    @Size(max = 32)
    private String lastName;

    @Enumerated(EnumType.STRING)
    private MaritalStatus maritalStatus; // ENUM

    @Column
    @Size(max = 32)
    private String middleName;

    @Column
    private String salutation; // ENUM

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

    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public Salutation getPosition() {
        return Salutation.getType(this.salutation);
    }

    public void setPosition(Salutation position) {

        if (position == null) {
            this.salutation = null;
        } else {
            this.salutation = position.getSalutation();
        }
    }

}
