/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.kaojo.persistence.entities;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author jwinter
 */
@Entity
@Table(name = "CHAT_MESSAGE")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class MessageEntity extends AbstractEntity<Long> {

    @ManyToOne
    @JoinColumn(name = "account_id")
    private AccountEntity author;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private ChatRoomEntity chatRoom;

    @Column
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date creationDate;

    public AccountEntity getAuthor() {
        return author;
    }

    public void setAuthor(AccountEntity author) {
        this.author = author;
    }

    public abstract String getContent();

    public abstract void setContent(String content);

    public ChatRoomEntity getChatRoom() {
        return chatRoom;
    }

    public void setChatRoom(ChatRoomEntity chatRoom) {
        this.chatRoom = chatRoom;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

}
