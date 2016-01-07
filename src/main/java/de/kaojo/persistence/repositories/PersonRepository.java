/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.kaojo.persistence.repositories;

import de.kaojo.persistence.entities.PersonEntity;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

/**
 *
 * @author julian
 */
@Repository(forEntity = PersonEntity.class)
public interface PersonRepository extends EntityRepository<PersonEntity, Long> {

}
