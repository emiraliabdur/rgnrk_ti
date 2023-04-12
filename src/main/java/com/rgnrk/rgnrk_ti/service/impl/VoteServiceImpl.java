package com.rgnrk.rgnrk_ti.service;

import com.rgnrk.rgnrk_ti.entity.VoteEntity;
import com.rgnrk.rgnrk_ti.exceptions.SessionNotFoundException;
import com.rgnrk.rgnrk_ti.exceptions.VotingIsNotAcceptedException;
import com.rgnrk.rgnrk_ti.mapper.VoteMapper;
import com.rgnrk.rgnrk_ti.model.UserStoryDto.StatusEnum;
import com.rgnrk.rgnrk_ti.model.VoteDto;
import com.rgnrk.rgnrk_ti.repository.SessionRepository;
import com.rgnrk.rgnrk_ti.repository.UserStoryRepository;
import com.rgnrk.rgnrk_ti.repository.VoteRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@Transactional
public class VoteService {

    private final VoteRepository voteRepository;
    private final VoteMapper voteMapper;
    private final SessionRepository sessionRepository;
    private final UserStoryRepository userStoryRepository;

    public VoteService(SessionRepository sessionRepository, VoteRepository voteRepository, VoteMapper voteMapper, UserStoryRepository userStoryRepository) {
        this.sessionRepository = sessionRepository;
        this.voteRepository = voteRepository;
        this.voteMapper = voteMapper;
        this.userStoryRepository = userStoryRepository;
    }

    public List<VoteDto> getVotes(String sessionId) {
        List<VoteEntity> voteEntities = voteRepository.getAllBySessionId(sessionId);
        log.info("Returning {} votes for the session {}", voteEntities.size(), sessionId);

        return voteMapper.toModels(voteEntities);
    }

    public VoteDto emitVote(String sessionId, VoteDto vote) {
        checkIfSessionExists(sessionId);
        checkIfVotingAllowed(vote.getIdUserStory());

        log.info("Member {} emits vote for the user story {}", vote.getIdMember(), vote.getIdUserStory());
        VoteEntity entity = voteMapper.toEntity(vote);
        VoteEntity savedVoteEntity = voteRepository.save(entity);
        return voteMapper.toModel(savedVoteEntity);

    }

    private void checkIfSessionExists(String sessionId) {
        if (!sessionRepository.existsById(sessionId)) {
            throw new SessionNotFoundException(sessionId);
        }
    }

    private void checkIfVotingAllowed(String userStoryId) {
        StatusEnum status = userStoryRepository.findStatusById(userStoryId);
        if (status == StatusEnum.VOTED) {
            throw new VotingIsNotAcceptedException(userStoryId);
        }
    }
}
