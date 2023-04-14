package com.rgnrk.rgnrk_ti.service.impl;

import com.rgnrk.rgnrk_ti.entity.SessionEntity;
import com.rgnrk.rgnrk_ti.mapper.PokerPlanningSessionMapper;
import com.rgnrk.rgnrk_ti.mapper.PokerPlanningSessionMapperImpl;
import com.rgnrk.rgnrk_ti.model.PokerPlanningSessionDto;
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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PokerPlanningSessionServiceImplTest {

    @InjectMocks
    private PokerPlanningSessionServiceImpl pokerPlanningSessionService;

    @Mock
    SessionRepository sessionRepository;
    @Mock
    MemberRepository memberRepository;
    @Mock
    UserStoryRepository userStoryRepository;
    @Mock
    VoteRepository voteRepository;
    @Spy
    PokerPlanningSessionMapper sessionMapper = new PokerPlanningSessionMapperImpl();

    private SessionEntity session1;
    private SessionEntity session2;

    @BeforeEach
    public void setUp() {
        session1 = new SessionEntity();
        session1.setTitle(RandomStringUtils.randomAlphabetic(10));
        session1.setId(UUID.randomUUID().toString());

        session2 = new SessionEntity();
        session2.setTitle(RandomStringUtils.randomAlphabetic(10));
        session2.setId(UUID.randomUUID().toString());

        lenient().when(sessionRepository.findAll()).thenReturn(List.of(session1, session2));
        lenient().when(sessionRepository.findById(anyString())).thenReturn(Optional.of(session1));
        lenient().when(sessionRepository.save(any(SessionEntity.class)))
                .thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));
    }

    @Test
    public void shouldGetPokerPlanningSessions() {
        List<PokerPlanningSessionDto> sessionDtos = pokerPlanningSessionService.getPokerPlanningSessions();

        assertThat(sessionDtos, hasSize(2));
        assertThat(sessionDtos.get(0).getIdSession(), equalTo(session1.getId()));
        assertThat(sessionDtos.get(0).getTitle(), equalTo(session1.getTitle()));
        assertThat(sessionDtos.get(1).getIdSession(), equalTo(session2.getId()));
        assertThat(sessionDtos.get(1).getTitle(), equalTo(session2.getTitle()));

        verify(sessionRepository).findAll();
        verify(sessionMapper).toModels(anyList());
    }

    @Test
    public void shouldCreatePokerPlanningSession() {
        PokerPlanningSessionDto sessionDto = new PokerPlanningSessionDto();
        sessionDto.setTitle(RandomStringUtils.randomAlphabetic(10));

        PokerPlanningSessionDto savedSessionDto = pokerPlanningSessionService.createPokerPlanningSession(sessionDto);

        assertThat(savedSessionDto, equalTo(sessionDto));

        verify(sessionRepository).save(any(SessionEntity.class));
        verify(sessionMapper).toEntity(any());
        verify(sessionMapper).toModel(any());
    }

    @Test
    public void shouldGetPokerPlanningSession() {
        Optional<PokerPlanningSessionDto> sessionDto = pokerPlanningSessionService.getPokerPlanningSession(session1.getId());

        assertThat(sessionDto.isPresent(), is(true));
        assertThat(sessionDto.get().getIdSession(), equalTo(session1.getId()));
        assertThat(sessionDto.get().getTitle(), equalTo(session1.getTitle()));

        verify(sessionRepository).findById(eq(session1.getId()));
        verify(sessionMapper).toModel(any());
    }

    @Test
    public void shouldDeletePokerPlanningSession() {
        Optional<PokerPlanningSessionDto> sessionDto = pokerPlanningSessionService.deletePokerPlanningSession(session1.getId());

        assertThat(sessionDto.isPresent(), is(true));
        assertThat(sessionDto.get().getIdSession(), equalTo(session1.getId()));
        assertThat(sessionDto.get().getTitle(), equalTo(session1.getTitle()));

        verify(sessionRepository).deleteById(eq(session1.getId()));
        verify(voteRepository).deleteAllBySessionId(eq(session1.getId()));
        verify(memberRepository).deleteAllBySessionId(eq(session1.getId()));
        verify(userStoryRepository).deleteAllBySessionId(eq(session1.getId()));
        verify(sessionMapper).toModel(any());
    }




}