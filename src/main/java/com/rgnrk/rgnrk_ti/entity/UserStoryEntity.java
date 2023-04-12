package com.rgnrk.rgnrk_ti.entity;

import com.rgnrk.rgnrk_ti.model.UserStoryDto.StatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "user_story")
public class UserStoryEntity {

    @Id
    private String id;

    @Column(name = "session_id")
    private String sessionId;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusEnum status;
}
