/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.kaojo.persistence.repositories;

import de.kaojo.persistence.entities.MessageEntity;
import java.util.List;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;

/**
 *
 * @author julian
 */
@Repository(forEntity = MessageEntity.class)
public interface MessageRepository extends EntityRepository<MessageEntity, Long> {

    @Query("SELECT me FROM MessageEntity me JOIN me.chatRoom cr WHERE cr.roomName = ?1 ORDER BY me.creationDate")
    public List<MessageEntity> findByChatRoomRoomName(String roomName);

}
