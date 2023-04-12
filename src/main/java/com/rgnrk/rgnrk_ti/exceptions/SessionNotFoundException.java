package com.rgnrk.rgnrk_ti.exceptions;

public class SessionNotFoundException extends RuntimeException {
    private static final String ERROR_MSG = "Session %s is missing";
    private final String idSession;

    public SessionNotFoundException(String idSession) {
        super(String.format(ERROR_MSG, idSession));
        this.idSession = idSession;
    }
}
