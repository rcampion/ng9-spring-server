package com.rkc.zds.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.rkc.zds.entity.GroupEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public interface GroupRepository extends JpaRepository<GroupEntity, Integer>, JpaSpecificationExecutor<GroupEntity> {
  
	Page<GroupEntity> findByGroupNameLike(Pageable pageable, String groupName);
    
}
