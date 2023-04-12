package com.rgnrk.rgnrk_ti.repository;

import com.rgnrk.rgnrk_ti.entity.UserStoryEntity;
import com.rgnrk.rgnrk_ti.model.UserStoryDto;
import com.rgnrk.rgnrk_ti.model.UserStoryDto.StatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserStoryRepository extends JpaRepository<UserStoryEntity, String> {

    List<UserStoryEntity> findAllBySessionId(String sessionId);

    void deleteAllBySessionId(String sessionId);

    @Query("select u.status from UserStoryEntity u where u.id = :id")
    StatusEnum findStatusById(String id);

    @Modifying
    @Query("update UserStoryEntity u " +
            " set u.status = :status" +
            " where u.id = :id")
    void setStatus(StatusEnum status, String id);
}
