package com.rkc.zds.service;

import com.rkc.zds.core.user.FollowRelation;
import com.rkc.zds.entity.UserEntity;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    void insert(@Param("user") UserEntity user);

    UserEntity findByUserName(@Param("userName") String userName);
    UserEntity findByEmail(@Param("email") String email);

    UserEntity findById(@Param("id") String id);

    void update(@Param("user") UserEntity user);

    FollowRelation findRelation(@Param("userId") Integer userId, @Param("targetId") Integer targetId);

    void saveRelation(@Param("followRelation") FollowRelation followRelation);

    void deleteRelation(@Param("followRelation") FollowRelation followRelation);
}
