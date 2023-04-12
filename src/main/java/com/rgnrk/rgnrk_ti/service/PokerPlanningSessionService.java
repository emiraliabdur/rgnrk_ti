package com.rgnrk.rgnrk_ti.service;

import com.rgnrk.rgnrk_ti.model.PokerPlanningSessionDto;

import java.util.List;
import java.util.Optional;

public interface PokerPlanningSessionService {

    List<PokerPlanningSessionDto> getPokerPlanningSessions();

    PokerPlanningSessionDto createPokerPlanningSession(PokerPlanningSessionDto session);

    Optional<PokerPlanningSessionDto> getPokerPlanningSession(String sessionId);

    Optional<PokerPlanningSessionDto> deletePokerPlanningSession(String sessionId);
}
