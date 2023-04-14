package com.rgnrk.rgnrk_ti.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rgnrk.rgnrk_ti.model.MemberDto;
import com.rgnrk.rgnrk_ti.model.PokerPlanningSessionDto;
import com.rgnrk.rgnrk_ti.model.UserStoryDto;
import com.rgnrk.rgnrk_ti.model.VoteDto;
import com.rgnrk.rgnrk_ti.service.PokerPlanningSessionService;
import com.rgnrk.rgnrk_ti.service.SessionMemberService;
import com.rgnrk.rgnrk_ti.service.UserStoryService;
import com.rgnrk.rgnrk_ti.service.VoteService;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.rgnrk.rgnrk_ti.model.UserStoryDto.StatusEnum.*;
import static org.hamcrest.CoreMatchers.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@AutoConfigureMockMvc
class SessionManagementControllerTest {

    private static final String SESSION_ID_1 = "bfb2132c-d6c4-11ed-afa1-0242ac120002";
    private static final String TITLE_1 = "Session 1";
    private static final String VOTING_USER_STORY_ID_1 = "19ca7cdc-d6c5-11ed-afa1-0242ac120002";
    private static final String VOTING_USER_STORY_DESCR_1 = "Voting User Story 1";
    private static final String PENDING_USER_STORY_ID_2 = "39ca7cdc-d6c5-11ed-afa1-0242ac120002";
    private static final String PENDING_USER_STORY_DESCR_2 = "Pending User Story 2";
    private static final String VOTED_USER_STORY_ID_3 = "59ca7cdc-d6c5-11ed-afa1-0242ac120002";
    private static final String VOTED_USER_STORY_DESCR_3 = "Voted User Story 3";
    private static final String VOTE_VALUE = "Voted value";
    private static final String VOTING_USER_STORY_ID_3 = "263df944-d6c5-11ed-afa1-0242ac120002";
    private static final String VOTING_USER_STORY_DESCR_3 = "Voting User Story 3";

    private static final String MEMBER_ID_1 = "f68d8e76-d6c4-11ed-afa1-0242ac120002";
    private static final String MEMBER_NAME_1 = "Bobby";
    private static final String SESSION_ID_2 = "c34edce0-d6c4-11ed-afa1-0242ac120002";
    private static final String TITLE_2 = "Session 2";
    private static final String MEMBER_ID_2 = "00a23038-d6c5-11ed-afa1-0242ac120002";
    private static final String MEMBER_NAME_2 = "Johnny";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private  PokerPlanningSessionService pokerPlanningSessionService;
    @Autowired
    private  SessionMemberService sessionMemberService;
    @Autowired
    private  UserStoryService userStoryService;
    @Autowired
    private  VoteService voteService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup(){

    }

    @Test
    void sessionsGet() throws Exception {
        mockMvc.perform(get("/sessions")
                .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(2)))
                .andExpect(jsonPath("$..idSession", hasItems(SESSION_ID_1, SESSION_ID_2)))
                .andExpect(jsonPath("$..title", hasItems(TITLE_1, TITLE_2)));
    }

    @Test
    void sessionsPost() throws Exception {
        PokerPlanningSessionDto sessionDto = new PokerPlanningSessionDto();
        sessionDto.setTitle(RandomStringUtils.randomAlphabetic(36));

        mockMvc.perform(post("/sessions")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sessionDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idSession", notNullValue()))
                .andExpect(jsonPath("$.title", is(sessionDto.getTitle())));
    }

    @Test
    void sessionsIdSessionGet() throws Exception {
        mockMvc.perform(get("/sessions/" + SESSION_ID_1)
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idSession", is(SESSION_ID_1)))
                .andExpect(jsonPath("$.title", is(TITLE_1)));
    }

    @Test
    void sessionsIdSessionDelete() throws Exception {
        mockMvc.perform(delete("/sessions/" + SESSION_ID_1)
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idSession", is(SESSION_ID_1)))
                .andExpect(jsonPath("$.title", is(TITLE_1)));

        mockMvc.perform(get("/sessions/" + SESSION_ID_1 + "/stories" + VOTING_USER_STORY_ID_1)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound());
        mockMvc.perform(get("/sessions/" + SESSION_ID_1 + "/members" + MEMBER_ID_1)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound());
        mockMvc.perform(get("/sessions/" + SESSION_ID_1 + "/votes")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void sessionsIdSessionMembersGet() throws Exception {
        mockMvc.perform(get("/sessions/" + SESSION_ID_1 + "/members")
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(1)))
                .andExpect(jsonPath("$..idMember", hasItems(MEMBER_ID_1)))
                .andExpect(jsonPath("$..name", hasItems(MEMBER_NAME_1)));
    }

    @Test
    void sessionsIdSessionMembersPost() throws Exception {
        MemberDto memberDto = new MemberDto();
        memberDto.setName(RandomStringUtils.randomAlphabetic(36));

        mockMvc.perform(post("/sessions/" + SESSION_ID_1 + "/members")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idMember", notNullValue()))
                .andExpect(jsonPath("$.name", is(memberDto.getName())));
    }

    @Test
    void sessionsIdSessionMembersIdMemberDelete() throws Exception {
        mockMvc.perform(delete("/sessions/" + SESSION_ID_1 + "/members/" + MEMBER_ID_1)
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idMember", is(MEMBER_ID_1)))
                .andExpect(jsonPath("$.name", is(MEMBER_NAME_1)));

        mockMvc.perform(delete("/sessions/" + SESSION_ID_1 + "/members/" + UUID.randomUUID())
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void sessionsIdSessionStoriesPost() throws Exception {
        UserStoryDto userStoryDto = new UserStoryDto();
        userStoryDto.setIdUserStory(UUID.randomUUID().toString());
        userStoryDto.setDescription(RandomStringUtils.randomAlphabetic(36));

        mockMvc.perform(post("/sessions/" + SESSION_ID_1 + "/stories")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userStoryDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idUserStory", is(userStoryDto.getIdUserStory())))
                .andExpect(jsonPath("$.description", is(userStoryDto.getDescription())))
                .andExpect(jsonPath("$.status", is(PENDING.toString())));
    }

    @Test
    void sessionsIdSessionStoriesGet() throws Exception {
        mockMvc.perform(get("/sessions/" + SESSION_ID_1 + "/stories")
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(3)))
                .andExpect(jsonPath("$..idUserStory", hasItems(VOTING_USER_STORY_ID_1, PENDING_USER_STORY_ID_2, VOTED_USER_STORY_ID_3)))
                .andExpect(jsonPath("$..description", hasItems(VOTING_USER_STORY_DESCR_1, PENDING_USER_STORY_DESCR_2, VOTED_USER_STORY_DESCR_3)))
                .andExpect(jsonPath("$..status", hasItems(VOTING.toString(), PENDING.toString(), VOTED.toString())));


        UUID randomUUID = UUID.randomUUID();
        mockMvc.perform(get("/sessions/" + randomUUID + "/stories")
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Session " + randomUUID + " is missing")));
    }

    @Test
    void sessionsIdSessionStoriesIdUserStoryPut() throws Exception {
        UserStoryDto userStoryDto = new UserStoryDto();
        userStoryDto.setIdUserStory(VOTING_USER_STORY_ID_1);
        userStoryDto.setDescription(RandomStringUtils.randomAlphabetic(36));
        userStoryDto.setStatus(PENDING);

        mockMvc.perform(put("/sessions/" + SESSION_ID_1 + "/stories/" + VOTING_USER_STORY_ID_1)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userStoryDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idUserStory", is(userStoryDto.getIdUserStory())))
                .andExpect(jsonPath("$.description", is(userStoryDto.getDescription())))
                .andExpect(jsonPath("$.status", is(PENDING.toString())));

        mockMvc.perform(put("/sessions/" + SESSION_ID_1 + "/stories/" + UUID.randomUUID())
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userStoryDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    void sessionsIdSessionStoriesIdUserStoryDelete() throws Exception {
        mockMvc.perform(delete("/sessions/" + SESSION_ID_1 + "/stories/" + PENDING_USER_STORY_ID_2)
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description", is(PENDING_USER_STORY_DESCR_2)))
                .andExpect(jsonPath("$.status", is(PENDING.toString())));

        mockMvc.perform(delete("/sessions/" + SESSION_ID_1 + "/stories/" + VOTING_USER_STORY_ID_1)
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());

        mockMvc.perform(delete("/sessions/" + SESSION_ID_1 + "/stories/" + UUID.randomUUID())
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void sessionsIdSessionVotesGet() throws Exception {
        mockMvc.perform(get("/sessions/" + SESSION_ID_1 + "/votes")
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(1)))
                .andExpect(jsonPath("$..idMember", hasItems(MEMBER_ID_1)))
                .andExpect(jsonPath("$..idUserStory", hasItems(VOTED_USER_STORY_ID_3)))
                .andExpect(jsonPath("$..idUserStory", not(hasItems(VOTING_USER_STORY_ID_1))))
                .andExpect(jsonPath("$..value", hasItems(VOTE_VALUE)));
    }

    @Test
    void sessionsIdSessionVotesPost() throws Exception {
        VoteDto vote1 = new VoteDto();
        vote1.setIdUserStory(VOTING_USER_STORY_ID_3);
        vote1.setIdMember(MEMBER_ID_2);
        vote1.setValue(RandomStringUtils.randomAlphabetic(36));

        mockMvc.perform(post("/sessions/" + SESSION_ID_2 + "/votes")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vote1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idUserStory", is(vote1.getIdUserStory())))
                .andExpect(jsonPath("$.idMember", is(vote1.getIdMember())))
                .andExpect(jsonPath("$.value", is(vote1.getValue())));

        VoteDto vote2 = new VoteDto();
        vote2.setIdUserStory(VOTED_USER_STORY_ID_3);
        vote2.setIdMember(MEMBER_ID_1);
        vote2.setValue(RandomStringUtils.randomAlphabetic(36));

        mockMvc.perform(post("/sessions/" + SESSION_ID_1 + "/votes")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vote2)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Voting for the user story " + VOTED_USER_STORY_ID_3 + " is closed")));

        UUID randomUUID = UUID.randomUUID();
        mockMvc.perform(post("/sessions/" + randomUUID + "/votes")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vote2)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Session " + randomUUID + " is missing")));
    }
}