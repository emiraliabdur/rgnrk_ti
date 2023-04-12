package com.rgnrk.rgnrk_ti.service.impl;

import com.rgnrk.rgnrk_ti.entity.MemberEntity;
import com.rgnrk.rgnrk_ti.exceptions.SessionNotFoundException;
import com.rgnrk.rgnrk_ti.mapper.MemberMapper;
import com.rgnrk.rgnrk_ti.model.MemberDto;
import com.rgnrk.rgnrk_ti.repository.MemberRepository;
import com.rgnrk.rgnrk_ti.repository.SessionRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class SessionMemberServiceImpl {

    private final SessionRepository sessionRepository;
    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

    public SessionMemberServiceImpl(SessionRepository sessionRepository, MemberRepository memberRepository, MemberMapper memberMapper) {
        this.sessionRepository = sessionRepository;
        this.memberRepository = memberRepository;
        this.memberMapper = memberMapper;
    }

    public List<MemberDto> getSessionMembers(String sessionId) {
        checkIfSessionExists(sessionId);

        List<MemberEntity> memberEntities = memberRepository.findAllBySessionId(sessionId);
        log.info("Returning {} members for the session {}", memberEntities.size(), sessionId);

        return memberMapper.toModels(memberEntities);
    }

    public MemberDto joinToTheSession(String sessionId, MemberDto member) {
        checkIfSessionExists(sessionId);

        MemberEntity entity = memberMapper.toEntity(sessionId, member);

        log.info("Member {} is about to join {} poker planning session", member.getIdMember(), sessionId);
        MemberEntity savedEntity = memberRepository.save(entity);

        return memberMapper.toModel(savedEntity);
    }

    public Optional<MemberDto> getSessionMember(String sessionId, String memberId) {
        Optional<MemberEntity> optionalEntity = memberRepository.findByIdAndSessionId(memberId, sessionId);

        return optionalEntity.map(memberMapper::toModel);
    }

    public Optional<MemberDto> logoutSessionMember(String sessionId, String memberId) {
        log.info("Member {} is about to leave {} poker planning session", memberId, sessionId);

        Optional<MemberEntity> optionalEntity = memberRepository.findByIdAndSessionId(memberId, sessionId);
        optionalEntity.ifPresent(memberRepository::delete);

        return optionalEntity.map(memberMapper::toModel);
    }

    private void checkIfSessionExists(String sessionId) {
        if (!sessionRepository.existsById(sessionId)) {
            throw new SessionNotFoundException(sessionId);
        }
    }
}
