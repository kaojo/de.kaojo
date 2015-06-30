/*
 * Diese Software ist Eigentum von Julian Winter
 * Alle Rechte sind vorbehalten.
 * Copyright 2015.
 */
package de.kaojo.chat;

import java.util.Date;

/**
 *
 * @author julian
 */
public class Message {

    private Date timestamp;
    private Author author;
    private String content;
    
    public Message() {
    
    }

    public Message(Author author, String content) {
        this.author = author;
        this.content = content;
        this.timestamp = new Date();
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public Author getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }
}
