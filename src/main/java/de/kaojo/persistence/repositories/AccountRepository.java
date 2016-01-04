/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.kaojo.persistence.repositories;

import de.kaojo.persistence.entities.AccountEntity;
import java.util.List;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;

/**
 *
 * @author julian
 */
@Repository(forEntity = AccountEntity.class)
public interface AccountRepository extends EntityRepository<AccountEntity, Long> {

    @Query("SELECT ac FROM AccountEntity ac WHERE ac.userName = ?1")
    public List<AccountEntity> findByUserName(String userName);

}
