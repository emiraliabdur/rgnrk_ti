package com.rgnrk.rgnrk_ti.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserStory {
    private String id;
    private String description;
    private Status status = Status.PENDING;
}
