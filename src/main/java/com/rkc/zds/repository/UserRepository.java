package com.rkc.zds.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.rkc.zds.core.user.FollowRelation;
import com.rkc.zds.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer>, JpaSpecificationExecutor<UserEntity>{
	//UserDto findByUserName(String userName);

	UserEntity findByLogin(String login);
	
    // void save(UserDto user);

    Optional<UserEntity> findById(String id);

    Optional<UserEntity> findByUserName(String userName);

    Optional<UserEntity> findByEmail(String email);

//    void saveRelation(FollowRelation followRelation);

//    Optional<FollowRelation> findRelation(Integer userId, Integer targetId);

//    void removeRelation(FollowRelation followRelation);
}
