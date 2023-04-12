package com.rgnrk.rgnrk_ti.controller;

import com.rgnrk.rgnrk_ti.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = { SessionNotFoundException.class, UserStoryNotFoundException.class })
    public ErrorResult handleNotFoundException(Exception ex, WebRequest request) {
        return new ErrorResult(ex.getMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = { UserStoryForbiddenToDeleteException.class})
    protected ErrorResult handleForbiddenAccess(Exception ex, WebRequest request) {
        return new ErrorResult(ex.getMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = { VotingIsClosedException.class})
    protected ErrorResult handleBadRequest(Exception ex, WebRequest request) {
        return new ErrorResult(ex.getMessage());
    }
}
