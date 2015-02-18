/*
 * Diese Software ist Eigentum von Julian Winter
 * Alle Rechte sind vorbehalten.
 * Copyright 2015.
 */
package de.winter.kaojo.chat;

import java.util.Date;

/**
 *
 * @author julian
 */
public class Message {

    private final Date timestamp;
    private final Author author;
    private final String content;

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
