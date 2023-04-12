package com.rgnrk.rgnrk_ti.service;

import com.rgnrk.rgnrk_ti.model.VoteDto;

import java.util.List;

public interface VoteService {

    List<VoteDto> getVotes(String sessionId);

    VoteDto emitVote(String sessionId, VoteDto vote);
}
