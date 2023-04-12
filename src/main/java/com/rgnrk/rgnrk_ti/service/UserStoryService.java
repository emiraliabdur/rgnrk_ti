package com.rgnrk.rgnrk_ti.service;

import com.rgnrk.rgnrk_ti.model.UserStoryDto;

import java.util.List;

public interface UserStoryService {

    List<UserStoryDto> getSessionUserStories(String sessionId);

    UserStoryDto createUserStory(String sessionId, UserStoryDto userStory);

    UserStoryDto updateUserStory(String sessionId, UserStoryDto userStory);

    UserStoryDto deleteUserStory(String userStoryId);
}
