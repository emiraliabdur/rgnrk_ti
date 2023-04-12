package com.rgnrk.rgnrk_ti.service.impl;

import com.rgnrk.rgnrk_ti.entity.UserStoryEntity;
import com.rgnrk.rgnrk_ti.exceptions.SessionNotFoundException;
import com.rgnrk.rgnrk_ti.exceptions.UserStoryForbiddenToDeleteException;
import com.rgnrk.rgnrk_ti.exceptions.UserStoryNotFoundException;
import com.rgnrk.rgnrk_ti.mapper.UserStoryMapper;
import com.rgnrk.rgnrk_ti.model.UserStoryDto;
import com.rgnrk.rgnrk_ti.repository.SessionRepository;
import com.rgnrk.rgnrk_ti.repository.UserStoryRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.rgnrk.rgnrk_ti.model.UserStoryDto.StatusEnum.PENDING;

@Slf4j
@Service
@Transactional
public class UserStoryServiceImpl {

    private final UserStoryRepository userStoryRepository;
    private final SessionRepository sessionRepository;
    private final UserStoryMapper userStoryMapper;

    public UserStoryServiceImpl(UserStoryRepository userStoryRepository, SessionRepository sessionRepository, UserStoryMapper userStoryMapper) {
        this.userStoryRepository = userStoryRepository;
        this.sessionRepository = sessionRepository;
        this.userStoryMapper = userStoryMapper;
    }

    public List<UserStoryDto> getSessionUserStories(String sessionId) {
        checkIfSessionExists(sessionId);

        List<UserStoryEntity> userStoryEntities = userStoryRepository.findAllBySessionId(sessionId);
        log.info("Returning {} story entities for the session {}", userStoryEntities.size(), sessionId);

        return userStoryMapper.toModels(userStoryEntities);
    }

    public UserStoryDto createUserStory(String sessionId, UserStoryDto userStory) {
        checkIfSessionExists(sessionId);

        log.info("User story with description {} is creating for {} poker planning session", userStory.getDescription(), sessionId);
        UserStoryEntity savedUserStoryEntity = userStoryRepository.save(userStoryMapper.toEntity(sessionId, userStory));

        return userStoryMapper.toModel(savedUserStoryEntity);
    }

    public UserStoryDto updateUserStory(String sessionId, UserStoryDto userStory) {
        checkIfSessionExists(sessionId);
        checkIfUserStoryExists(userStory.getIdUserStory());

        log.info("User story {} is updating for {} poker planning session", userStory.getIdUserStory(), sessionId);
        UserStoryEntity savedUserStoryEntity = userStoryRepository.save(userStoryMapper.toEntity(sessionId, userStory));

        return userStoryMapper.toModel(savedUserStoryEntity);
    }

    public UserStoryDto deleteUserStory(String userStoryId) {
        log.info("About to delete user story {}", userStoryId);

        Optional<UserStoryEntity> optionalEntity = userStoryRepository.findById(userStoryId);
        if (optionalEntity.isEmpty()) {
           throw new UserStoryNotFoundException(userStoryId);
        }
        UserStoryDto userStory = userStoryMapper.toModel(optionalEntity.get());
        if (userStory.getStatus() != PENDING) {
            throw new UserStoryForbiddenToDeleteException(userStoryId);
        }
        userStoryRepository.deleteById(userStoryId);

        return userStory;
    }

    private void checkIfSessionExists(String sessionId) {
        if (!sessionRepository.existsById(sessionId)) {
            throw new SessionNotFoundException(sessionId);
        }
    }

    private void checkIfUserStoryExists(String idUserStory) {
        if (!userStoryRepository.existsById(idUserStory)) {
            throw new UserStoryNotFoundException(idUserStory);
        }
    }

}
