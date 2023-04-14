package com.rgnrk.rgnrk_ti.service.impl;

import com.rgnrk.rgnrk_ti.entity.MemberEntity;
import com.rgnrk.rgnrk_ti.mapper.MemberMapper;
import com.rgnrk.rgnrk_ti.mapper.MemberMapperImpl;
import com.rgnrk.rgnrk_ti.model.MemberDto;
import com.rgnrk.rgnrk_ti.repository.MemberRepository;
import com.rgnrk.rgnrk_ti.repository.SessionRepository;
import com.rgnrk.rgnrk_ti.repository.UserStoryRepository;
import com.rgnrk.rgnrk_ti.repository.VoteRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
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
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SessionMemberServiceImplTest {


    @InjectMocks
    private SessionMemberServiceImpl sessionMemberService;

    @Mock
    SessionRepository sessionRepository;
    @Mock
    MemberRepository memberRepository;
    @Mock
    UserStoryRepository userStoryRepository;
    @Mock
    VoteRepository voteRepository;
    @Spy
    MemberMapper memberMapper = new MemberMapperImpl();

    private MemberEntity member1;
    private MemberEntity member2;

    @BeforeEach
    public void setUp() {
        String sessionId = UUID.randomUUID().toString();

        member1 = new MemberEntity();
        member1.setName(RandomStringUtils.randomAlphabetic(10));
        member1.setSessionId(sessionId);
        member1.setId(UUID.randomUUID().toString());

        member2 = new MemberEntity();
        member2.setName(RandomStringUtils.randomAlphabetic(10));
        member2.setSessionId(sessionId);
        member2.setId(UUID.randomUUID().toString());

        lenient().when(memberRepository.findAllBySessionId(sessionId)).thenReturn(List.of(member1, member2));
        lenient().when(memberRepository.findByIdAndSessionId(member1.getId(), sessionId)).thenReturn(Optional.of(member1));
        lenient().when(sessionRepository.existsById(anyString())).thenReturn(true);
        lenient().when(memberRepository.save(ArgumentMatchers.any(MemberEntity.class)))
                .thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));
    }

    @Test
    public void shouldGetSessionMembers() {
        List<MemberDto> memberDtos = sessionMemberService.getSessionMembers(member1.getSessionId());

        assertThat(memberDtos, hasSize(2));
        assertThat(memberDtos.get(0).getIdMember(), equalTo(member1.getId()));
        assertThat(memberDtos.get(0).getName(), equalTo(member1.getName()));
        assertThat(memberDtos.get(1).getIdMember(), equalTo(member2.getId()));
        assertThat(memberDtos.get(1).getName(), equalTo(member2.getName()));

        verify(memberRepository).findAllBySessionId(anyString());
        verify(memberMapper).toModels(anyList());
    }

    @Test
    public void shouldJoinToSession() {
        MemberDto memberDto = new MemberDto();
        memberDto.setName(RandomStringUtils.randomAlphabetic(10));
        MemberDto joinedMemberDto = sessionMemberService.joinToTheSession(UUID.randomUUID().toString(), memberDto);

        assertThat(joinedMemberDto, equalTo(memberDto));

        verify(memberRepository).save(any(MemberEntity.class));
        verify(memberMapper).toEntity(anyString(), any());
        verify(memberMapper).toModel(any());
    }

    @Test
    public void shouldGetSessionMember() {
        Optional<MemberDto> memberDto = sessionMemberService.getSessionMember(member1.getSessionId(), member1.getId());

        assertThat(memberDto.isPresent(), is(true));
        assertThat(memberDto.get().getIdMember(), equalTo(member1.getId()));
        assertThat(memberDto.get().getName(), equalTo(member1.getName()));

        verify(memberRepository).findByIdAndSessionId(eq(member1.getId()), eq(member1.getSessionId()));
        verify(memberMapper).toModel(any());
    }

    @Test
    public void shouldLogoutSessionMember() {
        Optional<MemberDto> memberDto = sessionMemberService.logoutSessionMember(member1.getSessionId(), member1.getId());

        assertThat(memberDto.isPresent(), is(true));
        assertThat(memberDto.get().getIdMember(), equalTo(member1.getId()));
        assertThat(memberDto.get().getName(), equalTo(member1.getName()));

        verify(memberRepository).findByIdAndSessionId(eq(member1.getId()), eq(member1.getSessionId()));
        verify(voteRepository).deleteAllBySessionIdAndMemberId(eq(member1.getSessionId()), eq(member1.getId()));
        verify(memberMapper).toModel(any());
    }
}