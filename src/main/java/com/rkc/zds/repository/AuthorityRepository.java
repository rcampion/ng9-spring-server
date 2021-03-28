package com.rkc.zds.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.rkc.zds.entity.AuthorityEntity;
import com.rkc.zds.entity.ContactEntity;

public interface AuthorityRepository extends JpaRepository<AuthorityEntity, Integer> {
	
	Page<AuthorityEntity> findByUserName(Pageable pageable, String userName);
	
}
