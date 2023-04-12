package com.rgnrk.rgnrk_ti.exceptions;

public class UserStoryNotFoundException extends RuntimeException {
    private static final String ERROR_MSG = "UserStory %s is missing";
    private final String idUserStory;

    public UserStoryNotFoundException(String idUserStory) {
        super(String.format(ERROR_MSG, idUserStory));
        this.idUserStory = idUserStory;
    }
}
