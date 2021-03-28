package com.rkc.zds.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import com.rkc.zds.entity.ContactEntity;
import com.rkc.zds.util.SearchCriteria;

public interface ContactService {

    Page<ContactEntity> findContacts(Pageable pageable);

    Page<ContactEntity> searchContacts(String name);
    
    Page<ContactEntity> searchContacts(Pageable pageable, List<SearchCriteria> params);
    
	Page<ContactEntity> searchContacts(Pageable pageable, Specification<ContactEntity> spec);

    Page<ContactEntity> findFilteredContacts(Pageable pageable, int groupId);
    
    // @Transactional     
    ContactEntity getContact(int id);    

    // @Transactional    
    public void saveContact(ContactEntity contact);

    // @Transactional    
    public void updateContact(ContactEntity contact);

    // @Transactional  
	void deleteContact(int id);

}
