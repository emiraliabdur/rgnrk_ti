package com.rgnrk.rgnrk_ti.controller;

import com.rgnrk.rgnrk_ti.api.SessionsApi;
import com.rgnrk.rgnrk_ti.model.MemberDto;
import com.rgnrk.rgnrk_ti.model.PokerPlanningSessionDto;
import com.rgnrk.rgnrk_ti.model.UserStoryDto;
import com.rgnrk.rgnrk_ti.model.VoteDto;
import com.rgnrk.rgnrk_ti.service.PokerPlanningSessionService;
import com.rgnrk.rgnrk_ti.service.SessionMemberService;
import com.rgnrk.rgnrk_ti.service.UserStoryService;
import com.rgnrk.rgnrk_ti.service.VoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@Validated
public class SessionManagementController implements SessionsApi {

    private final PokerPlanningSessionService pokerPlanningSessionService;
    private final SessionMemberService sessionMemberService;
    private final UserStoryService userStoryService;
    private final VoteService voteService;

    public SessionManagementController(PokerPlanningSessionService pokerPlanningSessionService, SessionMemberService sessionMemberService, UserStoryService userStoryService, VoteService voteService) {
        this.pokerPlanningSessionService = pokerPlanningSessionService;
        this.sessionMemberService = sessionMemberService;
        this.userStoryService = userStoryService;
        this.voteService = voteService;
    }

    @Override
    public ResponseEntity<List<PokerPlanningSessionDto>> sessionsGet() {
        List<PokerPlanningSessionDto> pokerPlanningSessions = pokerPlanningSessionService.getPokerPlanningSessions();
        return new ResponseEntity<>(pokerPlanningSessions, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PokerPlanningSessionDto> sessionsPost(PokerPlanningSessionDto session) {
        PokerPlanningSessionDto savedSession = pokerPlanningSessionService.createPokerPlanningSession(session);
        return new ResponseEntity<>(savedSession, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<PokerPlanningSessionDto> sessionsIdSessionGet(String idSession) {
        Optional<PokerPlanningSessionDto> optionalSession = pokerPlanningSessionService.getPokerPlanningSession(idSession);
        return optionalSession.map(session -> new ResponseEntity<>(session, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<PokerPlanningSessionDto> sessionsIdSessionDelete(String idSession) {
        Optional<PokerPlanningSessionDto> optionalSession = pokerPlanningSessionService.deletePokerPlanningSession(idSession);

        return optionalSession.map(session -> new ResponseEntity<>(session, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @Override
    public ResponseEntity<List<MemberDto>> sessionsIdSessionMembersGet(String idSession) {
        List<MemberDto> members = sessionMemberService.getSessionMembers(idSession);
        return CollectionUtils.isEmpty(members)
                ? new ResponseEntity<>(members, HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(members, HttpStatus.OK);
    }

    @Override
   public ResponseEntity<MemberDto> sessionsIdSessionMembersPost(String idSession, MemberDto member) {
        MemberDto joinedMember = sessionMemberService.joinToTheSession(idSession, member);

        return new ResponseEntity<>(joinedMember, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<MemberDto> sessionsIdSessionMembersIdMemberDelete(String idSession, String idMember) {
        Optional<MemberDto> optionalMember = sessionMemberService.logoutSessionMember(idSession, idMember);

        return optionalMember
                .map(member -> new ResponseEntity<>(member, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @Override
    public ResponseEntity<UserStoryDto> sessionsIdSessionStoriesPost(String idSession, UserStoryDto userStory) {
        UserStoryDto createdUserStory = userStoryService.createUserStory(idSession, userStory);

        return new ResponseEntity<>(createdUserStory, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<UserStoryDto>> sessionsIdSessionStoriesGet(String idSession) {
        List<UserStoryDto> userStories = userStoryService.getSessionUserStories(idSession);
        return new ResponseEntity<>(userStories, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UserStoryDto> sessionsIdSessionStoriesIdUserStoryPut(String idSession, String idUserStory, UserStoryDto userStory) {
        UserStoryDto updatedUserStory = userStoryService.updateUserStory(idSession, userStory);

        return new ResponseEntity<>(updatedUserStory, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UserStoryDto> sessionsIdSessionStoriesIdUserStoryDelete(String idSession, String idUserStory) {
        UserStoryDto deletedUserStory = userStoryService.deleteUserStory(idUserStory);
        return new ResponseEntity<>(deletedUserStory, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<VoteDto>> sessionsIdSessionVotesGet(String idSession) {
        List<VoteDto> votes = voteService.getVotes(idSession);
        return new ResponseEntity<>(votes, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<VoteDto> sessionsIdSessionVotesPost(String idSession, VoteDto vote) {
        VoteDto savedVote = voteService.emitVote(idSession, vote);

        return new ResponseEntity<>(savedVote, HttpStatus.CREATED);
    }
}
