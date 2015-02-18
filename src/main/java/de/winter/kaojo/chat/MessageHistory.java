/*
 * Diese Software ist Eigentum von Julian Winter
 * Alle Rechte sind vorbehalten.
 * Copyright 2015.
 */
package de.winter.kaojo.chat;

import java.util.ArrayList;
import org.apache.commons.collections.buffer.CircularFifoBuffer;

/**
 *
 * @author julian
 */
public class MessageHistory<T> extends ArrayList {

//    private final CircularFifoBuffer buffer;
    public MessageHistory() {
        Author author = new Author("Tester", "Herr Test");
//        this.add(new Message(author, text));
//        this.buffer = new CircularFifoBuffer(100);
        this.add(new Message(author, "text text text text text text text text text text "));
        for (int i = 0; i < 100; i++) {
            this.add(new Message(author, "text" + i));
        }
    }

//    @Override
//    public boolean add(Object e) {
//        return super.add(e);
//    }
}
