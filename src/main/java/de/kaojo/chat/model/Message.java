/*
 * Diese Software ist Eigentum von Julian Winter
 * Alle Rechte sind vorbehalten.
 * Copyright 2015.
 */
package de.kaojo.chat.model;

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

    public Date getTimestamp() {
        return timestamp;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }
    
}
