package com.rgnrk.rgnrk_ti.mapper;

import com.rgnrk.rgnrk_ti.entity.SessionEntity;
import com.rgnrk.rgnrk_ti.model.PokerPlanningSession;

public interface PokerPlanningSessionMapper {

    PokerPlanningSession toModel(SessionEntity entity);
    SessionEntity toEntity(PokerPlanningSession session);
}
