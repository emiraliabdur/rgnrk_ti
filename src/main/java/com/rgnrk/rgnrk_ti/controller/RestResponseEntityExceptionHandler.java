package com.rgnrk.rgnrk_ti.controller;

import com.rgnrk.rgnrk_ti.exceptions.SessionNotFoundException;
import com.rgnrk.rgnrk_ti.exceptions.UserStoryForbiddenToDeleteException;
import com.rgnrk.rgnrk_ti.exceptions.UserStoryNotFoundException;
import com.rgnrk.rgnrk_ti.exceptions.VotingIsClosedException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { SessionNotFoundException.class, UserStoryNotFoundException.class })
    protected ResponseEntity<Object> handleNotFoundEntities(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = { UserStoryForbiddenToDeleteException.class})
    protected ResponseEntity<Object> handleForbiddenAccess(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.FORBIDDEN, request);
    }

    @ExceptionHandler(value = { VotingIsClosedException.class})
    protected ResponseEntity<Object> handleBadRequest(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
