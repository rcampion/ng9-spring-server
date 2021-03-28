package com.rkc.zds.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.rkc.zds.entity.ContactEntity;
import com.rkc.zds.entity.UserContactEntity;

public interface UserContactsService {
    Page<UserContactEntity> findUserContacts(Pageable pageable, int userId);

    Page<ContactEntity> findFilteredContacts(Pageable pageable, int userId);    

    List<UserContactEntity> findAllUserContacts(int userId);
    
    List<UserContactEntity> getAllUserContacts();

    // @Transactional    
    public void addUserContact(UserContactEntity userContact);
    
    public void saveUserContact(UserContactEntity userContact); 
    
    // @Transactional  
	void deleteUserContact(int id);
}
