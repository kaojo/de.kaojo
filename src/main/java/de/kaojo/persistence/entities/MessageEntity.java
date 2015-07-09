/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.kaojo.persistence.entities;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
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
public class MessageEntity extends AbstractEntity<Long> {

    @Column
    @ManyToOne
    private AccountEntity author;
    
    @Column
    @ManyToOne
    @JoinColumn(name = "room_id")  
    private ChatRoomEntity chatRoom;

    @Column
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date creationDate;

    public ChatRoomEntity getChatRoom() {
        return chatRoom;
    }

    public void setChatRoom(ChatRoomEntity chatRoom) {
        this.chatRoom = chatRoom;
    }
    
    

    /**
     * Get the value of timestamp
     *
     * @return the value of timestamp
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * Set the value of timestamp
     *
     * @param timestamp new value of timestamp
     */
    public void setCreationDate(Date timestamp) {
        this.creationDate = timestamp;
    }

    /**
     * Get the value of author
     *
     * @return the value of author
     */
    public AccountEntity getAuthor() {
        return author;
    }

    /**
     * Set the value of author
     *
     * @param author new value of author
     */
    public void setAuthor(AccountEntity author) {
        this.author = author;
    }

}
