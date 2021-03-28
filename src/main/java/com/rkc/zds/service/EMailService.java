package com.rkc.zds.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.rkc.zds.entity.ContactEntity;
import com.rkc.zds.entity.EMailEntity;
import com.rkc.zds.model.EMailSend;

public interface EMailService {
    Page<EMailEntity> findEMails(Pageable pageable, int contactId);
    
    // @Transactional     
    EMailEntity getEMail(int id);  

    // @Transactional    
    public void saveEMail(EMailEntity email);
    
    // @Transactional    
    public void updateEMail(EMailEntity email);

    // @Transactional  
	void deleteEMail(int id);

	void sendEMail(EMailSend emailSend);
}
