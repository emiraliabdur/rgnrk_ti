package com.rgnrk.rgnrk_ti.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class VoteId implements Serializable {
    private String memberId;
    private String userStoryId;
}
