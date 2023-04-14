package com.rgnrk.rgnrk_ti.repository;

import com.rgnrk.rgnrk_ti.entity.VoteEntity;
import com.rgnrk.rgnrk_ti.model.UserStoryDto.StatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface VoteRepository extends JpaRepository<VoteEntity, String> {

    @Query("select v from VoteEntity v " +
            " left join UserStoryEntity us on us.id = v.userStoryId " +
            " where us.sessionId = :sessionId and us.status in :statuses"
    )
    List<VoteEntity> findAllBySessionIdAndUserStoryStatusIn(String sessionId, Collection<StatusEnum> statuses);

    @Modifying
    @Query("delete from VoteEntity v " +
            " where v.userStoryId in (select u.id from UserStoryEntity u where u.sessionId = :sessionId)"
    )
    void deleteAllBySessionId(String sessionId);

    @Modifying
    @Query("delete from VoteEntity v " +
            " where v.userStoryId in (select u.id from UserStoryEntity u where u.sessionId = :sessionId)" +
            " and v.memberId = :memberId"
    )
    void deleteAllBySessionIdAndMemberId(String sessionId, String memberId);
}
