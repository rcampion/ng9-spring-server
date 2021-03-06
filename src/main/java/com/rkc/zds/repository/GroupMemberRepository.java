package com.rkc.zds.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.rkc.zds.entity.GroupEntity;
import com.rkc.zds.entity.GroupMemberEntity;

public interface GroupMemberRepository extends JpaRepository<GroupMemberEntity, Integer> {
  
	Page<GroupMemberEntity> findByGroupId(Pageable pageable, int groupId);

	List<GroupMemberEntity> findByGroupId(int groupId);
	       
}
