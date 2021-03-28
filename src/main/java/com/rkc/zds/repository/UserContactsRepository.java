package com.rkc.zds.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.rkc.zds.entity.UserContactEntity;

public interface UserContactsRepository extends JpaRepository<UserContactEntity, Integer> {
  
	Page<UserContactEntity> findByUserId(Pageable pageable, int userId);

	List<UserContactEntity> findByUserId(int userId);
	       
}