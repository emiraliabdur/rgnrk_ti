package com.rgnrk.rgnrk_ti.exceptions;

public class VotingIsNotAcceptedException extends RuntimeException {
    private static final String ERROR_MSG = "Voting for the user story %s is not accepted";
    private String idUserStory;

    public VotingIsNotAcceptedException() {
        super();
    }

    public VotingIsNotAcceptedException(String idUserStory, Throwable cause) {
        super(String.format(ERROR_MSG, idUserStory), cause);
        this.idUserStory = idUserStory;
    }
    public VotingIsNotAcceptedException(String idUserStory) {
        super(String.format(ERROR_MSG, idUserStory));
        this.idUserStory = idUserStory;
    }

    public VotingIsNotAcceptedException(Throwable cause) {
        super(cause);
    }
}
