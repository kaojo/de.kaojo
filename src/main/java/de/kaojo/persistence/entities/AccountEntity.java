/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.kaojo.persistence.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * @author jwinter
 */
@Entity
@Table(name = "ACCOUNT")
public class AccountEntity extends AbstractEntity<Long> implements Serializable {

    @Column
    private Boolean active;

    @ManyToMany(mappedBy = "invites")
    private Set<ChatRoomEntity> invitedChatRooms;

    @ManyToMany(mappedBy = "members")
    private Set<ChatRoomEntity> memberChatRooms;

    @ManyToMany(mappedBy = "owner")
    private Set<ChatRoomEntity> ownedChatRooms;

    @OneToOne(orphanRemoval = true)
    private ContactEntity contactEntity;

    @Column
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date creationDate;

    @Column
    @Size(max = 32)
    private String displayName;

    @Column
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date lastLogin;

    @Column
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date lastModified;

    @OneToMany(mappedBy = "author")
    private Set<MessageEntity> messages;

    @Column
    private String password;

    @OneToOne(orphanRemoval = true)
    private PersonEntity personEntity;

    @ManyToMany
    @JoinTable(name = "ACCOUNT_ROLES")
    private Set<RolesEntity> roles;

    @Column(unique = true)
    @NotNull
    @Size(max = 32)
    @Pattern(regexp = "^[0-9a-zA-Z]*$")
    private String userName;

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public ContactEntity getContactEntity() {
        return contactEntity;
    }

    public void setContactEntity(ContactEntity contactEntity) {
        this.contactEntity = contactEntity;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Set<ChatRoomEntity> getInvitedChatRooms() {
        return invitedChatRooms;
    }

    public void setInvitedChatRooms(Set<ChatRoomEntity> invitedChatRooms) {
        this.invitedChatRooms = invitedChatRooms;
    }

    public Set<ChatRoomEntity> getMemberChatRooms() {
        return memberChatRooms;
    }

    public void setMemberChatRooms(Set<ChatRoomEntity> memberChatRooms) {
        this.memberChatRooms = memberChatRooms;
    }

    public Set<MessageEntity> getMessages() {
        return messages;
    }

    public void setMessages(Set<MessageEntity> messages) {
        this.messages = messages;
    }

    public Set<ChatRoomEntity> getOwnedChatRooms() {
        return ownedChatRooms;
    }

    public void setOwnedChatRooms(Set<ChatRoomEntity> ownedChatRooms) {
        this.ownedChatRooms = ownedChatRooms;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public PersonEntity getPersonEntity() {
        return personEntity;
    }

    public void setPersonEntity(PersonEntity personEntity) {
        this.personEntity = personEntity;
    }

    public Set<RolesEntity> getRoles() {
        return roles;
    }

    public void setRoles(Set<RolesEntity> roles) {
        this.roles = roles;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
