package com.rgnrk.rgnrk_ti.service.impl;

import com.rgnrk.rgnrk_ti.entity.UserStoryEntity;
import com.rgnrk.rgnrk_ti.exceptions.UserStoryForbiddenToDeleteException;
import com.rgnrk.rgnrk_ti.mapper.UserStoryMapper;
import com.rgnrk.rgnrk_ti.mapper.UserStoryMapperImpl;
import com.rgnrk.rgnrk_ti.model.UserStoryDto;
import com.rgnrk.rgnrk_ti.repository.MemberRepository;
import com.rgnrk.rgnrk_ti.repository.SessionRepository;
import com.rgnrk.rgnrk_ti.repository.UserStoryRepository;
import com.rgnrk.rgnrk_ti.repository.VoteRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.rgnrk.rgnrk_ti.model.UserStoryDto.StatusEnum.PENDING;
import static com.rgnrk.rgnrk_ti.model.UserStoryDto.StatusEnum.VOTING;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class UserStoryServiceImplTest {

    @InjectMocks
    private UserStoryServiceImpl userStoryService;

    @Mock
    SessionRepository sessionRepository;
    @Mock
    MemberRepository memberRepository;
    @Mock
    UserStoryRepository userStoryRepository;
    @Mock
    VoteRepository voteRepository;
    @Spy
    UserStoryMapper userStoryMapper = new UserStoryMapperImpl();

    private UserStoryEntity userStory1;
    private UserStoryEntity userStory2;

    @BeforeEach
    public void setUp() {
        String sessionId = UUID.randomUUID().toString();

        userStory1 = new UserStoryEntity();
        userStory1.setDescription(RandomStringUtils.randomAlphabetic(10));
        userStory1.setSessionId(sessionId);
        userStory1.setStatus(VOTING);
        userStory1.setId(UUID.randomUUID().toString());

        userStory2 = new UserStoryEntity();
        userStory2.setDescription(RandomStringUtils.randomAlphabetic(10));
        userStory2.setStatus(PENDING);
        userStory2.setSessionId(sessionId);
        userStory2.setId(UUID.randomUUID().toString());

        lenient().when(userStoryRepository.findAllBySessionId(sessionId)).thenReturn(List.of(userStory1, userStory2));
        lenient().when(userStoryRepository.findById(anyString())).thenReturn(Optional.of(userStory1));
        lenient().when(sessionRepository.existsById(anyString())).thenReturn(true);
        lenient().when(userStoryRepository.existsById(anyString())).thenReturn(true);
        lenient().doNothing().when(userStoryRepository).deleteById(anyString());
        lenient().when(userStoryRepository.save(any(UserStoryEntity.class)))
                .thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));
    }

    @Test
    public void shouldGetSessionUserStories() {
        List<UserStoryDto> userStoryDtos = userStoryService.getSessionUserStories(userStory1.getSessionId());

        assertThat(userStoryDtos, hasSize(2));
        assertThat(userStoryDtos.get(0).getIdUserStory(), equalTo(userStory1.getId()));
        assertThat(userStoryDtos.get(0).getDescription(), equalTo(userStory1.getDescription()));
        assertThat(userStoryDtos.get(0).getStatus(), equalTo(userStory1.getStatus()));
        assertThat(userStoryDtos.get(1).getIdUserStory(), equalTo(userStory2.getId()));
        assertThat(userStoryDtos.get(1).getDescription(), equalTo(userStory2.getDescription()));
        assertThat(userStoryDtos.get(1).getStatus(), equalTo(userStory2.getStatus()));

        verify(userStoryRepository).findAllBySessionId(anyString());
        verify(userStoryMapper).toModels(anyList());
    }

    @Test
    public void shouldCreateUserStory() {
        UserStoryDto userStoryDto = new UserStoryDto();
        userStoryDto.setStatus(UserStoryDto.StatusEnum.VOTED);
        userStoryDto.setIdUserStory(UUID.randomUUID().toString());
        userStoryDto.setDescription(RandomStringUtils.randomAlphabetic(10));

        UserStoryDto savedUserStory = userStoryService.createUserStory(UUID.randomUUID().toString(), userStoryDto);

        assertThat(savedUserStory, equalTo(userStoryDto));

        verify(userStoryRepository).save(any(UserStoryEntity.class));
        verify(userStoryMapper).toModel(any());
        verify(userStoryMapper).toEntity(anyString(), any());
    }

    @Test
    public void shouldUpdateUserStory() {
        UserStoryDto userStoryDto = new UserStoryDto();
        userStoryDto.setStatus(UserStoryDto.StatusEnum.VOTED);
        userStoryDto.setIdUserStory(UUID.randomUUID().toString());
        userStoryDto.setDescription(RandomStringUtils.randomAlphabetic(10));

        UserStoryDto savedUserStory = userStoryService.updateUserStory(UUID.randomUUID().toString(), userStoryDto);

        assertThat(savedUserStory, equalTo(userStoryDto));

        verify(userStoryRepository).existsById(eq(userStoryDto.getIdUserStory()));
        verify(userStoryRepository).save(any(UserStoryEntity.class));
        verify(userStoryMapper).toModel(any());
        verify(userStoryMapper).toEntity(anyString(), any());
    }

    @Test
    public void shouldDeleteUserStory() {
        when(userStoryRepository.findById(anyString())).thenReturn(Optional.of(userStory2));

        String userStoryId = UUID.randomUUID().toString();
        UserStoryDto deletedUserStoryDto = userStoryService.deleteUserStory(userStoryId);

        assertThat(deletedUserStoryDto.getIdUserStory(), equalTo(userStory2.getId()));
        assertThat(deletedUserStoryDto.getDescription(), equalTo(userStory2.getDescription()));
        assertThat(deletedUserStoryDto.getStatus(), equalTo(userStory2.getStatus()));

        verify(userStoryRepository).findById(eq(userStoryId));
        verify(userStoryRepository).deleteById(eq(userStoryId));
        verify(userStoryMapper).toModel(any());
    }

    @Test
    public void shouldNotDeletePendingUserStory() {
        String userStoryId = UUID.randomUUID().toString();
        UserStoryForbiddenToDeleteException thrown = assertThrowsExactly(
                UserStoryForbiddenToDeleteException.class,
                () -> userStoryService.deleteUserStory(userStoryId)
        );

        assertTrue(thrown.getMessage().contentEquals(String.format("User story %s is forbidden to remove", userStoryId)));
        verify(userStoryRepository).findById(eq(userStoryId));
        verify(userStoryMapper).toModel(any());
        verify(userStoryRepository, never()).deleteById(eq(userStoryId));
    }
}