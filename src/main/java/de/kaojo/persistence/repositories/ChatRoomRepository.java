/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.kaojo.persistence.repositories;

import de.kaojo.persistence.entities.ChatRoomEntity;
import java.util.List;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;

/**
 *
 * @author julian
 */
@Repository(forEntity = ChatRoomEntity.class)
public interface ChatRoomRepository extends EntityRepository<ChatRoomEntity, Long> {

    @Query("SELECT cr FROM ChatRoomEntity cr WHERE (SELECT ac FROM AccountEntity ac WHERE ac.id = ?1) MEMBER OF cr.invites OR (SELECT ac FROM AccountEntity ac WHERE ac.id = ?1) MEMBER OF cr.members")
    public List<ChatRoomEntity> findByMembersOrInvitesAccountId(Long accountId);

    @Query("SELECT DISTINCT cr FROM ChatRoomEntity cr JOIN cr.members m WHERE m.id = ?1")
    public List<ChatRoomEntity> findByMembersAccountId(Long accountId);

    @Query("SELECT DISTINCT cr FROM ChatRoomEntity cr JOIN cr.invites i WHERE i.id = ?1 AND ?1 <> ALL (SELECT m.id FROM cr.members m)")
    public List<ChatRoomEntity> findByInvitesAccountIdExludeMembers(Long accountId);

    @Query("SELECT DISTINCT cr FROM ChatRoomEntity cr WHERE cr.unrestricted = true AND ?1 <> ALL (SELECT m.id FROM cr.members m)")
    public List<ChatRoomEntity> findByUnrestrictedExludeMembered(Long accountId);

    @Query("SELECT cr FROM ChatRoomEntity cr WHERE cr.roomName = ?1")
    public List<ChatRoomEntity> findByRoomName(String roomName);
}
