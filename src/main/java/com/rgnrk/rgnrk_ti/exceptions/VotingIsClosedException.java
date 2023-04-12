package com.rgnrk.rgnrk_ti.exceptions;

public class VotingIsClosedException extends RuntimeException {
    private static final String ERROR_MSG = "Voting for the user story %s is closed";
    private String idUserStory;

    public VotingIsClosedException(String idUserStory) {
        super(String.format(ERROR_MSG, idUserStory));
        this.idUserStory = idUserStory;
    }
}
