package com.rgnrk.rgnrk_ti.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@AllArgsConstructor
@Data
public class VoteId implements Serializable {
    private UUID memberId;
    private UUID userStoryId;
}
