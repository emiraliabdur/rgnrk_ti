package com.rgnrk.rgnrk_ti.service.impl;

import com.rgnrk.rgnrk_ti.entity.SessionEntity;
import com.rgnrk.rgnrk_ti.mapper.PokerPlanningSessionMapper;
import com.rgnrk.rgnrk_ti.model.PokerPlanningSessionDto;
import com.rgnrk.rgnrk_ti.repository.MemberRepository;
import com.rgnrk.rgnrk_ti.repository.SessionRepository;
import com.rgnrk.rgnrk_ti.repository.UserStoryRepository;
import com.rgnrk.rgnrk_ti.repository.VoteRepository;
import com.rgnrk.rgnrk_ti.service.PokerPlanningSessionService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class PokerPlanningSessionServiceImpl implements PokerPlanningSessionService {

    private final SessionRepository sessionRepository;
    private final MemberRepository memberRepository;
    private final UserStoryRepository userStoryRepository;
    private final VoteRepository voteRepository;
    private final PokerPlanningSessionMapper sessionMapper;

    public PokerPlanningSessionServiceImpl(SessionRepository sessionRepository, MemberRepository memberRepository,
                                           UserStoryRepository userStoryRepository, PokerPlanningSessionMapper sessionMapper, VoteRepository voteRepository) {
        this.sessionRepository = sessionRepository;
        this.memberRepository = memberRepository;
        this.userStoryRepository = userStoryRepository;
        this.sessionMapper = sessionMapper;
        this.voteRepository = voteRepository;
    }

    public List<PokerPlanningSessionDto> getPokerPlanningSessions() {
        List<SessionEntity> sessionEntities = sessionRepository.findAll();
        log.info("Returning {} poker planning sessions", sessionEntities.size());

        return sessionMapper.toModels(sessionEntities);
    }

    public PokerPlanningSessionDto createPokerPlanningSession(PokerPlanningSessionDto session) {
        SessionEntity sessionEntity = sessionMapper.toEntity(session);
        log.info("About to save {} poker planning session", sessionEntity);
        SessionEntity savedEntity = sessionRepository.save(sessionEntity);

        return sessionMapper.toModel(savedEntity);
    }

    public Optional<PokerPlanningSessionDto> getPokerPlanningSession(String sessionId) {
        Optional<SessionEntity> sessionEntity = sessionRepository.findById(sessionId);
        log.info("Returning poker planning session with id {}", sessionId);

        return sessionEntity.map(sessionMapper::toModel);
    }

    public Optional<PokerPlanningSessionDto> deletePokerPlanningSession(String sessionId) {

        Optional<SessionEntity> sessionEntity = sessionRepository.findById(sessionId);
        if (sessionEntity.isPresent()) {
            log.info("Deleting poker planning session with id {}", sessionId);
            voteRepository.deleteAllBySessionId(sessionId);
            memberRepository.deleteAllBySessionId(sessionId);
            userStoryRepository.deleteAllBySessionId(sessionId);
            sessionRepository.deleteById(sessionId);
        }

        return sessionEntity.map(sessionMapper::toModel);
    }

}
