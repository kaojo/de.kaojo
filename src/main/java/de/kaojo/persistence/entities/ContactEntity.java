/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.kaojo.persistence.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

/**
 *
 * @author jwinter
 */
@Entity
@Table(name = "CONTACT_DATA")
public class ContactEntity extends AbstractEntity<Long> {

    @Column
    private String country;

    @Column
    private String countryCode;

    @Column
    @Pattern(regexp = "^[_A-Za-z0-9-\\\\+]+(\\\\.[_A-Za-z0-9-]+)*"
            + "@[A-Za-z0-9-]+(\\\\.[A-Za-z0-9]+)*(\\\\.[A-Za-z]{2,})$;")
    private String email;

    @Column
    private Long phoneNumber;

    @Column
    private Long postalCode;

    @Column
    private String recipient;

    @Column
    private String street;

    @Column
    private String streetNumber;

    @Column
    private String town;

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String eMail) {
        this.email = eMail;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Long getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(Long postalCode) {
        this.postalCode = postalCode;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

}
