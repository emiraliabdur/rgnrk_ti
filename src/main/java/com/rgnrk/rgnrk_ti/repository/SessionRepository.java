package com.rgnrk.rgnrk_ti.repository;

import com.rgnrk.rgnrk_ti.entity.SessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<SessionEntity, String> {
}
