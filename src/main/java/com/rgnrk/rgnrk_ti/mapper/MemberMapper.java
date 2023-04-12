package com.rgnrk.rgnrk_ti.mapper;

import com.rgnrk.rgnrk_ti.entity.MemberEntity;
import com.rgnrk.rgnrk_ti.model.MemberDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel="spring")
public interface MemberMapper {

    @Mapping(source = "id", target = "idMember")
    MemberDto toModel(MemberEntity entity);

    List<MemberDto> toModels(List<MemberEntity> entities);

    @Mapping(source = "member.idMember", target = "id")
    @Mapping(source = "sessionId", target = "sessionId")
    @Mapping(source = "member.name", target = "name")
    MemberEntity toEntity(String sessionId, MemberDto member);
}
