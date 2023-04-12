package com.rgnrk.rgnrk_ti.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class SessionNotFoundException extends RuntimeException {
    private String idSession;

    public SessionNotFoundException() {
        super();
    }

    public SessionNotFoundException(String idSession, Throwable cause) {
        super("Session " + idSession + " is missing", cause);
        this.idSession = idSession;
    }
    public SessionNotFoundException(String idSession) {
        super("Session " + idSession + " is missing");
        this.idSession = idSession;
    }

    public SessionNotFoundException(Throwable cause) {
        super(cause);
    }
}
