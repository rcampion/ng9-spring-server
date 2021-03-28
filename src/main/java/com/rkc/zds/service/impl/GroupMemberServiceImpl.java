package com.rkc.zds.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.rkc.zds.entity.ContactEntity;
import com.rkc.zds.entity.GroupMemberEntity;
import com.rkc.zds.entity.UserContactEntity;
import com.rkc.zds.repository.ContactRepository;
import com.rkc.zds.repository.GroupMemberRepository;
import com.rkc.zds.service.GroupMemberService;

@Service
public class GroupMemberServiceImpl implements GroupMemberService {

	@Autowired
	private ContactRepository contactRepo;
	
	@Autowired
	private GroupMemberRepository groupMemberRepo;

	@Override
	public Page<GroupMemberEntity> findGroupMembers(Pageable pageable, int id) {

		Page<GroupMemberEntity> page = groupMemberRepo.findByGroupId(pageable, id);

		return page;
	}
	
	@Override
	public Page<ContactEntity> findFilteredContacts(Pageable pageable, int groupId) {

		List<ContactEntity> contacts = contactRepo.findAll();

		List<GroupMemberEntity> groupMemberList = groupMemberRepo.findByGroupId(groupId);
		
		List<ContactEntity> testList = new ArrayList<ContactEntity>();

		List<ContactEntity> filteredList = new ArrayList<ContactEntity>();

		// build member list of Contacts
		Optional<ContactEntity> contact;
		for (GroupMemberEntity element : groupMemberList) {
			contact= contactRepo.findById(element.getContactId());
			testList.add(contact.get());
		}

		// check member list of Contacts
		for (ContactEntity element : contacts) {
			// if the contact is in the members list, ignore it
			if (!testList.contains(element)) {
				filteredList.add(element);
			}
		}

		int size = filteredList.size();
		if(size == 0) {
			size = 1;
		}
		
		PageRequest pageRequest = PageRequest.of(0, size);

		PageImpl<ContactEntity> page = new PageImpl<ContactEntity>(filteredList, pageRequest, size);

		return page;
	}
	
	@Override
	public List<GroupMemberEntity> findAllMembers(int groupId) {

		List<GroupMemberEntity> list = groupMemberRepo.findByGroupId(groupId);

		return list;
	}

	private Sort sortByIdASC() {
		return Sort.by(Sort.Direction.ASC, "groupId");
	}

	@Override
	public void saveGroupMember(GroupMemberEntity groupMember) {
		// checking for duplicates
		List<GroupMemberEntity> list = groupMemberRepo.findByGroupId(groupMember.getGroupId());

		// return if duplicate found
		for (GroupMemberEntity element : list) {
			if (element.getContactId() == groupMember.getContactId()) {
				return;
			}
		}

		groupMemberRepo.save(groupMember);
	}

	@Override
	public void deleteGroupMember(int id) {

		groupMemberRepo.deleteById(id);

	}
}
