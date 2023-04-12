package com.rgnrk.rgnrk_ti.mapper;

import com.rgnrk.rgnrk_ti.entity.SessionEntity;
import com.rgnrk.rgnrk_ti.model.PokerPlanningSessionDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel="spring")
public interface PokerPlanningSessionMapper {

    @Mapping(source = "id", target = "idSession")
    PokerPlanningSessionDto toModel(SessionEntity entity);

    List<PokerPlanningSessionDto> toModels(List<SessionEntity> sessionEntities);

    @Mapping(source = "idSession", target = "id")
    SessionEntity toEntity(PokerPlanningSessionDto session);
}
