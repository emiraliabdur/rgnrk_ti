package com.rgnrk.rgnrk_ti.exceptions;

public class UserStoryForbiddenToDeleteException extends RuntimeException {
    private static final String ERROR_MSG = "User story %s is forbidden to remove";
    private String idUserStory;

    public UserStoryForbiddenToDeleteException(String idUserStory) {
        super(String.format(ERROR_MSG, idUserStory));
        this.idUserStory = idUserStory;
    }
}
