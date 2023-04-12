package com.rgnrk.rgnrk_ti.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "vote")
@IdClass(VoteId.class)
public class VoteEntity {

    @Id
    @Column(name = "member_id")
    private String memberId;

    @Id
    @Column(name = "user_story_id")
    private String userStoryId;

    @Column(name = "vote_value")
    private String value;
}
