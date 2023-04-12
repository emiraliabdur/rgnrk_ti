package com.rgnrk.rgnrk_ti.repository;

import com.rgnrk.rgnrk_ti.entity.UserStoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserStoryRepository extends JpaRepository<UserStoryEntity, UUID> {
}
