package com.rgnrk.rgnrk_ti.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Vote {
    private String memberId;
    private String userStoryId;
    private String value;
}
