/*
 * Diese Software ist Eigentum von Julian Winter
 * Alle Rechte sind vorbehalten.
 * Copyright 2015.
 */
package de.kaojo.chat.model;

import de.kaojo.persistence.entities.MessageEntity;
import java.util.Date;

/**
 *
 * @author julian
 */
public class Message {

    private Date timestamp;
    private String author;
    private String content;

    public Message() {

    }

    public Message(String author, String content) {
        this(author, content, new Date());
    }

    public Message(String author, String content, Date date) {
        this.author = author;
        this.content = content;
        this.timestamp = date;
    }

    public Message(MessageEntity messageEntity) {
        author = messageEntity.getAuthor().getUserName();
        timestamp = messageEntity.getCreationDate();
        content = messageEntity.getContent();
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

}
