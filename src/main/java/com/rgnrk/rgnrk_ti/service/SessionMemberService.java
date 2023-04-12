package com.rgnrk.rgnrk_ti.service;

import com.rgnrk.rgnrk_ti.model.MemberDto;

import java.util.List;
import java.util.Optional;

public interface SessionMemberService {

    List<MemberDto> getSessionMembers(String sessionId);

    MemberDto joinToTheSession(String sessionId, MemberDto member);

    Optional<MemberDto> getSessionMember(String sessionId, String memberId);

    Optional<MemberDto> logoutSessionMember(String sessionId, String memberId);
}
