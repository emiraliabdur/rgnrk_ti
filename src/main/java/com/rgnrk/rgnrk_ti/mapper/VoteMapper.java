package com.rgnrk.rgnrk_ti.mapper;

import com.rgnrk.rgnrk_ti.entity.VoteEntity;
import com.rgnrk.rgnrk_ti.model.VoteDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel="spring")
public interface VoteMapper {

    @Mapping(source = "memberId", target = "idMember")
    @Mapping(source = "userStoryId", target = "idUserStory")
    VoteDto toModel(VoteEntity entity);

    List<VoteDto> toModels(List<VoteEntity> entities);

    @Mapping(source = "idMember", target = "memberId")
    @Mapping(source = "idUserStory", target = "userStoryId")
    VoteEntity toEntity(VoteDto vote);
}
