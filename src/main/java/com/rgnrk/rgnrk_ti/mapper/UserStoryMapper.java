package com.rgnrk.rgnrk_ti.mapper;

import com.rgnrk.rgnrk_ti.entity.MemberEntity;
import com.rgnrk.rgnrk_ti.model.Member;

public interface MemberMapper {

    Member toModel(MemberEntity entity);
    MemberEntity toEntity(Member session);
}
