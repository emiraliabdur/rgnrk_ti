package com.rgnrk.rgnrk_ti.mapper;

import com.rgnrk.rgnrk_ti.entity.UserStoryEntity;
import com.rgnrk.rgnrk_ti.model.UserStory;

public interface UserStoryMapper {

    UserStory toModel(UserStoryEntity entity);
    UserStoryEntity toEntity(UserStory session);
}
