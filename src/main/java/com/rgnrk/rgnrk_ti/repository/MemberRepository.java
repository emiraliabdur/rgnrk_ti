package com.rgnrk.rgnrk_ti.repository;

import com.rgnrk.rgnrk_ti.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, String> {

    List<MemberEntity> findAllBySessionId(String sessionId);

    Optional<MemberEntity> findByIdAndSessionId(String id, String sessionId);

    void deleteAllBySessionId(String sessionId);
}
