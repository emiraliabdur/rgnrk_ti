package com.rgnrk.rgnrk_ti.mapper;

import com.rgnrk.rgnrk_ti.entity.UserStoryEntity;
import com.rgnrk.rgnrk_ti.model.UserStoryDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel="spring")
public interface UserStoryMapper {

    @Mapping(source = "id", target = "idUserStory")
    UserStoryDto toModel(UserStoryEntity entity);

    List<UserStoryDto> toModels(List<UserStoryEntity> entities);

    @Mapping(source = "sessionId", target = "sessionId")
    @Mapping(source = "userStory.idUserStory", target = "id")
    @Mapping(source = "userStory.description", target = "description")
    @Mapping(source = "userStory.status", target = "status")
    UserStoryEntity toEntity(String sessionId, UserStoryDto userStory);
}
