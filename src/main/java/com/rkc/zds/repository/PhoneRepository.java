package com.rkc.zds.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.rkc.zds.entity.PhoneEntity;

public interface PhoneRepository extends JpaRepository<PhoneEntity, Integer> {
  
	Page<PhoneEntity> findByContactId(Pageable pageable, int contactId);

	List<PhoneEntity> findByContactId(int contactId);
	       
}
