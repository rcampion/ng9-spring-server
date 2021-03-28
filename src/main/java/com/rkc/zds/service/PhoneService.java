package com.rkc.zds.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.rkc.zds.entity.PhoneEntity;

public interface PhoneService {
    Page<PhoneEntity> findPhones(Pageable pageable, int contactId);
    
    // @Transactional     
    PhoneEntity getPhone(int id);  

    // @Transactional    
    public void savePhone(PhoneEntity phone);
    
    // @Transactional    
    public void updatePhone(PhoneEntity phone);

    // @Transactional  
	void deletePhone(int id);
}
