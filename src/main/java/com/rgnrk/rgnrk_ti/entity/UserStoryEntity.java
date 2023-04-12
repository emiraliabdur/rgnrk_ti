package com.rgnrk.rgnrk_ti.entity;

import com.rgnrk.rgnrk_ti.model.UserStoryDto.StatusEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

    @Column(name = "status")
    private StatusEnum status;
}
