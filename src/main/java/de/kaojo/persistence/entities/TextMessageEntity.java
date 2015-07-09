/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.kaojo.persistence.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author jwinter
 */
@Entity
public class TextMessageEntity extends MessageEntity {

    @Column
    @NotNull
    @Size(min = 0)
    private String text;

    /**
     * Get the value of text
     *
     * @return the value of text
     */
    public String getText() {
        return text;
    }

    /**
     * Set the value of text
     *
     * @param text new value of text
     */
    public void setText(String text) {
        this.text = text;
    }

}
