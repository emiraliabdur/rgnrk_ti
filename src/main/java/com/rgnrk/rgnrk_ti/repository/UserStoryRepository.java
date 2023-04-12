package com.rgnrk.rgnrk_ti.repository;

import com.rgnrk.rgnrk_ti.entity.UserStoryEntity;
import com.rgnrk.rgnrk_ti.model.UserStoryDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserStoryRepository extends JpaRepository<UserStoryEntity, String> {

    List<UserStoryEntity> findAllBySessionId(String sessionId);

    void deleteAllBySessionId(String sessionId);

    @Query("select u.status from UserStoryEntity u where u.id = :id")
    UserStoryDto.StatusEnum findStatusById(String id);
}
