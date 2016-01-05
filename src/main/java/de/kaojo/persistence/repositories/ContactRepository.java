package de.kaojo.persistence.repositories;

import de.kaojo.persistence.entities.ContactEntity;
import java.util.List;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;

/**
 *
 * @author julian
 */
@Repository(forEntity = ContactEntity.class)
public interface ContactRepository extends EntityRepository<ContactEntity, Long> {

    @Query("SELECT ce FROM ContactEntity ce WHERE ce.email = ?1")
    public List<ContactEntity> findByEmail(String email);

}
