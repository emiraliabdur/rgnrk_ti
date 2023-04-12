package com.rgnrk.rgnrk_ti.repository;

import com.rgnrk.rgnrk_ti.entity.SessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SessionRepository extends JpaRepository<SessionEntity, UUID> {
}
