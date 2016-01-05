/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.kaojo.persistence.repositories;

import de.kaojo.persistence.entities.RolesEntity;
import de.kaojo.persistence.entities.enums.Roles;
import java.util.Set;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;

/**
 *
 * @author julian
 */
@Repository(forEntity = RolesEntity.class)
public interface RolesRepository extends EntityRepository<RolesEntity, Long> {

    @Query("SELECT re FROM RolesEntity re WHERE re.roles = :defaultRole")
    public Set<RolesEntity> findByRole(Roles role);

}
