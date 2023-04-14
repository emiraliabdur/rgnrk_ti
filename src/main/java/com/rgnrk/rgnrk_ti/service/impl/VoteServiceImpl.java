package com.rgnrk.rgnrk_ti.service.impl;

import com.rgnrk.rgnrk_ti.entity.VoteEntity;
import com.rgnrk.rgnrk_ti.exceptions.SessionNotFoundException;
import com.rgnrk.rgnrk_ti.exceptions.VotingIsClosedException;
import com.rgnrk.rgnrk_ti.mapper.VoteMapper;
import com.rgnrk.rgnrk_ti.model.UserStoryDto.StatusEnum;
import com.rgnrk.rgnrk_ti.model.VoteDto;
import com.rgnrk.rgnrk_ti.repository.SessionRepository;
import com.rgnrk.rgnrk_ti.repository.UserStoryRepository;
import com.rgnrk.rgnrk_ti.repository.VoteRepository;
import com.rgnrk.rgnrk_ti.service.VoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.rgnrk.rgnrk_ti.model.UserStoryDto.StatusEnum.*;

@Slf4j
@Service
@Transactional
public class VoteServiceImpl implements VoteService {

    private final VoteRepository voteRepository;
    private final VoteMapper voteMapper;
    private final SessionRepository sessionRepository;
    private final UserStoryRepository userStoryRepository;

    public VoteServiceImpl(SessionRepository sessionRepository, VoteRepository voteRepository, VoteMapper voteMapper, UserStoryRepository userStoryRepository) {
        this.sessionRepository = sessionRepository;
        this.voteRepository = voteRepository;
        this.voteMapper = voteMapper;
        this.userStoryRepository = userStoryRepository;
    }

    public List<VoteDto> getVisibleVotes(String sessionId) {
        checkIfSessionExists(sessionId);

        List<VoteEntity> voteEntities = voteRepository.findAllBySessionIdAndUserStoryStatusIn(sessionId, List.of(PENDING, VOTED));
        log.info("Returning {} votes for the session {}", voteEntities.size(), sessionId);

        return voteMapper.toModels(voteEntities);
    }

    public VoteDto emitVote(String sessionId, VoteDto vote) {
        checkIfSessionExists(sessionId);
        checkIfVotingAllowed(vote.getIdUserStory());

        log.info("Member {} emits vote for the user story {}", vote.getIdMember(), vote.getIdUserStory());
        VoteEntity entity = voteMapper.toEntity(vote);
        VoteEntity savedVoteEntity = voteRepository.save(entity);
        markStoryAsVoting(vote);

        return voteMapper.toModel(savedVoteEntity);

    }

    @Override
    public void deleteVotesInSession(String sessionId) {
        log.info("About to delete all votes in the session {}", sessionId);
        voteRepository.deleteAllBySessionId(sessionId);
    }

    private void markStoryAsVoting(VoteDto vote) {
        userStoryRepository.setStatus(VOTING, vote.getIdUserStory());
    }

    private void checkIfSessionExists(String sessionId) {
        if (!sessionRepository.existsById(sessionId)) {
            throw new SessionNotFoundException(sessionId);
        }
    }

    private void checkIfVotingAllowed(String userStoryId) {
        StatusEnum status = userStoryRepository.findStatusById(userStoryId);
        if (status == VOTED) {
            throw new VotingIsClosedException(userStoryId);
        }
    }
}
