/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.kaojo.persistence.entities;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author jwinter
 */
@Entity
@Table(name = "CHAT_ROOM")
public class ChatRoomEntity extends AbstractEntity<Long> {

    @ManyToMany
    @JoinTable(name = "CHAT_ROOM_INVITES")
    private Set<AccountEntity> invites;

    @ManyToMany
    @JoinTable(name = "CHAT_ROOM_MEMBERS")
    private Set<AccountEntity> members;

    @Column
    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL)
    Set<MessageEntity> messages;

    @Column
    private String roomName;

    public Set<AccountEntity> getInvites() {
        return invites;
    }

    public void setInvites(Set<AccountEntity> invites) {
        this.invites = invites;
    }

    public Set<AccountEntity> getMembers() {
        return members;
    }

    public void setMembers(Set<AccountEntity> members) {
        this.members = members;
    }

    public Set<MessageEntity> getMessages() {
        return messages;
    }

    public void setMessages(Set<MessageEntity> messages) {
        this.messages = messages;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    
}
