/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.kaojo.persistence.entities;

import de.kaojo.persistence.entities.enums.Roles;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

/**
 *
 * @author jwinter
 */
@Entity
@Table(name = "ROLES")
public class RolesEntity extends AbstractEntity<Long>{

    @Enumerated(EnumType.STRING)
    private Roles roles; //enum list

    public Roles getRoles() {
        return roles;
    }

    public void setRoles(Roles roles) {
        this.roles = roles;
    }

}
