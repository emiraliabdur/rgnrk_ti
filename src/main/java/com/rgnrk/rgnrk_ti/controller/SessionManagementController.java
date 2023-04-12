package com.rgnrk.rgnrk_ti.controller;

import com.rgnrk.rgnrk_ti.model.PokerPlanningSession;
import com.rgnrk.rgnrk_ti.service.PokerPlanningSessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/sessions")
@Tag(name = "session management")
@Validated
public class SessionController {

    private final PokerPlanningSessionService pokerPlanningSessionService;

    public SessionController(PokerPlanningSessionService pokerPlanningSessionService) {
        this.pokerPlanningSessionService = pokerPlanningSessionService;
    }

    @Operation(summary = "List of existing poker planning sessions")
    @ApiResponse(responseCode = "200", description = "A JSON array of session",
            content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = PokerPlanningSession.class))) })
    @GetMapping
    public ResponseEntity<List<PokerPlanningSession>> getPokerPlanningSessions() {
        List<PokerPlanningSession> pokerPlanningSessions = pokerPlanningSessionService.getPokerPlanningSessions();
        return new ResponseEntity<>(pokerPlanningSessions, HttpStatus.OK);
    }

    @Operation(summary = "Creation of a new session")
    @ApiResponse(responseCode = "201", description = "Created JSON object",
            content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = PokerPlanningSession.class))) })
    @PostMapping
    public ResponseEntity<PokerPlanningSession> createPokerPlanningSession(@RequestBody @Valid PokerPlanningSession session) {
        PokerPlanningSession savedSession = pokerPlanningSessionService.createPokerPlanningSession(session);
        return new ResponseEntity<>(savedSession, HttpStatus.CREATED);
    }

    @Operation(summary = "Session information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The Resource",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = PokerPlanningSession.class)) }),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
    })
    @GetMapping("/{idSession}")
    public ResponseEntity<PokerPlanningSession> getPokerPlanningSession(@PathVariable("idSession") String idSession) {
        Optional<PokerPlanningSession> optionalSession = pokerPlanningSessionService.getPokerPlanningSession(UUID.fromString(idSession));
        return optionalSession.map(session -> new ResponseEntity<>(session, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Destroy session")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Session destroyed info",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = PokerPlanningSession.class)) }),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
    })
    @DeleteMapping("/{idSession}")
    public ResponseEntity<PokerPlanningSession> deletePokerPlanningSession(
            @PathVariable("idSession") String idSession
    ) {
        Optional<PokerPlanningSession> optionalSession = pokerPlanningSessionService.deletePokerPlanningSession(UUID.fromString(idSession));

        return optionalSession.map(session -> new ResponseEntity<>(session, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }



}
