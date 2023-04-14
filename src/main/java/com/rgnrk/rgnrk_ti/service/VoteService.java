package com.rgnrk.rgnrk_ti.service;

import com.rgnrk.rgnrk_ti.model.VoteDto;

import java.util.List;

public interface VoteService {

    List<VoteDto> getVisibleVotes(String sessionId);

    VoteDto emitVote(String sessionId, VoteDto vote);

    void deleteVotesInSession(String sessionId);
}
