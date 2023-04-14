package com.rgnrk.rgnrk_ti.service.impl;

import com.rgnrk.rgnrk_ti.entity.VoteEntity;
import com.rgnrk.rgnrk_ti.exceptions.VotingIsClosedException;
import com.rgnrk.rgnrk_ti.mapper.VoteMapper;
import com.rgnrk.rgnrk_ti.mapper.VoteMapperImpl;
import com.rgnrk.rgnrk_ti.model.VoteDto;
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
import java.util.UUID;

import static com.rgnrk.rgnrk_ti.model.UserStoryDto.StatusEnum.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VoteServiceImplTest {

    @InjectMocks
    private VoteServiceImpl voteService;

    @Mock
    SessionRepository sessionRepository;
    @Mock
    UserStoryRepository userStoryRepository;
    @Mock
    VoteRepository voteRepository;
    @Spy
    VoteMapper voteMapper = new VoteMapperImpl();

    private VoteEntity voteEntity1;
    private VoteEntity voteEntity2;
    private String sessionId;

    @BeforeEach
    public void setUp() {
        sessionId = UUID.randomUUID().toString();

        voteEntity1 = new VoteEntity();
        voteEntity1.setValue(RandomStringUtils.randomAlphabetic(10));
        voteEntity1.setMemberId(UUID.randomUUID().toString());
        voteEntity1.setUserStoryId(UUID.randomUUID().toString());

        voteEntity2 = new VoteEntity();
        voteEntity2.setValue(RandomStringUtils.randomAlphabetic(10));
        voteEntity2.setMemberId(UUID.randomUUID().toString());
        voteEntity2.setUserStoryId(UUID.randomUUID().toString());

        lenient().when(sessionRepository.existsById(anyString())).thenReturn(true);

        lenient().when(voteRepository.findAllBySessionIdAndUserStoryStatusIn(eq(sessionId), anyCollection()))
                .thenReturn(List.of(voteEntity1, voteEntity2));

        lenient().when(userStoryRepository.existsById(anyString())).thenReturn(true);
        lenient().when(userStoryRepository.findStatusById(anyString())).thenReturn(VOTING);
        lenient().when(voteRepository.save(any(VoteEntity.class)))
                .thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));
        lenient().doNothing().when(voteRepository).deleteAllBySessionId(anyString());
    }

    @Test
    void shouldGetVisibleVotes() {
        List<VoteDto> voteDtos = voteService.getVisibleVotes(sessionId);

        assertThat(voteDtos, hasSize(2));
        assertThat(voteDtos.get(0).getIdUserStory(), equalTo(voteEntity1.getUserStoryId()));
        assertThat(voteDtos.get(0).getIdMember(), equalTo(voteEntity1.getMemberId()));
        assertThat(voteDtos.get(0).getValue(), equalTo(voteEntity1.getValue()));
        assertThat(voteDtos.get(1).getIdUserStory(), equalTo(voteEntity2.getUserStoryId()));
        assertThat(voteDtos.get(1).getIdMember(), equalTo(voteEntity2.getMemberId()));
        assertThat(voteDtos.get(1).getValue(), equalTo(voteEntity2.getValue()));

        verify(voteRepository).findAllBySessionIdAndUserStoryStatusIn(eq(sessionId), eq(List.of(PENDING, VOTED)));
        verify(voteMapper).toModels(anyList());
    }

    @Test
    void shouldEmitVote() {
        VoteDto voteDto = new VoteDto();
        voteDto.setIdMember(UUID.randomUUID().toString());
        voteDto.setIdUserStory(UUID.randomUUID().toString());
        voteDto.setValue(RandomStringUtils.randomAlphabetic(10));

        VoteDto savedVoteDto = voteService.emitVote(sessionId, voteDto);

        assertThat(savedVoteDto, equalTo(voteDto));
        verify(voteRepository).save(any());
        verify(userStoryRepository).setStatus(eq(VOTING), eq(voteDto.getIdUserStory()));
        verify(userStoryRepository).findStatusById(anyString());
        verify(voteMapper).toModel(any());
        verify(voteMapper).toEntity(any());
    }

    @Test
    void shouldNotEmitVote() {
        when(userStoryRepository.findStatusById(anyString())).thenReturn(VOTED);

        VoteDto voteDto = new VoteDto();
        voteDto.setIdMember(UUID.randomUUID().toString());
        voteDto.setIdUserStory(UUID.randomUUID().toString());
        voteDto.setValue(RandomStringUtils.randomAlphabetic(10));

        VotingIsClosedException thrown = assertThrowsExactly(
                VotingIsClosedException.class,
                () -> voteService.emitVote(sessionId, voteDto)
        );
        assertTrue(thrown.getMessage().contentEquals(String.format("Voting for the user story %s is closed", voteDto.getIdUserStory())));
        verify(userStoryRepository).findStatusById(anyString());
    }

    @Test
    void shouldDeleteVotesInSession() {
        voteService.deleteVotesInSession(sessionId);

        verify(voteRepository).deleteAllBySessionId(eq(sessionId));
    }
}