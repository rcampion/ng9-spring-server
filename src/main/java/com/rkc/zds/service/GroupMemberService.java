package com.rkc.zds.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.rkc.zds.entity.ContactEntity;
import com.rkc.zds.entity.GroupMemberEntity;

public interface GroupMemberService {
    Page<GroupMemberEntity> findGroupMembers(Pageable pageable, int groupId);
    
    List<GroupMemberEntity> findAllMembers(int groupId);

    Page<ContactEntity> findFilteredContacts(Pageable pageable, int groupId);  
    
    // @Transactional    
    public void saveGroupMember(GroupMemberEntity groupMember);    

    // @Transactional  
	void deleteGroupMember(int id);
}
