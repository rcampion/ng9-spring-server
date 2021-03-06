package com.rkc.zds.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.rkc.zds.entity.ContactEntity;
import com.rkc.zds.entity.PhoneEntity;
import com.rkc.zds.repository.PhoneRepository;
import com.rkc.zds.service.PhoneService;

@Service
public class PhoneServiceImpl implements PhoneService {

	@Autowired
	private PhoneRepository phoneRepo;

	@Override
	public Page<PhoneEntity> findPhones(Pageable pageable, int contactId) {

		Page<PhoneEntity> page = phoneRepo.findByContactId(pageable, contactId);

		return page;
	}

	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void savePhone(PhoneEntity phone) {

		phoneRepo.save(phone);
	}
	
	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void updatePhone(PhoneEntity phone) {

		phoneRepo.saveAndFlush(phone);
		
	}
	
	@Override
	public void deletePhone(int id) {

		phoneRepo.deleteById(id);

	}

	@Override
	public PhoneEntity getPhone(int id) {
	
		Optional<PhoneEntity> phone = phoneRepo.findById(id);
		if(phone.isPresent())
			return phone.get();
		else
			return null;
	}

}
