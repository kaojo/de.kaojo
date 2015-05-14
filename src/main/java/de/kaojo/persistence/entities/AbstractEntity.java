/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.kaojo.persistence.entities;

import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

/**
 *
 * @author jwinter
 * @param <ID>
 */
@MappedSuperclass
public abstract class AbstractEntity<ID extends Serializable> implements Serializable {
    
    @Id
    private ID id;

    @Version
    private Integer version;

    /**
     *
     * @return 
     */
    public ID getId() {
        return id;
    }

    /**
     *
     * @return
     */
    public Integer getVersion() {
        return version;
    }
}
