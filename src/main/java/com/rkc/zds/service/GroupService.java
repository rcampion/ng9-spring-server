package com.rkc.zds.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import com.rkc.zds.entity.GroupEntity;
import com.rkc.zds.entity.GroupMemberEntity;

public interface GroupService {

    Page<GroupEntity> findGroups(Pageable pageable);

    Page<GroupEntity> searchGroups(String name);
    
	Page<GroupEntity> searchGroups(Pageable pageable, Specification<GroupEntity> spec);

    // @Transactional     
    GroupEntity getGroup(int id);    

    // @Transactional     
    Page<GroupMemberEntity> findGroupMembers(int id); 
    
    // @Transactional    
    public void saveGroup(GroupEntity group);

    // @Transactional    
    public void updateGroup(GroupEntity group);

    // @Transactional  
	void deleteGroup(int id);

}
