package com.rkc.zds.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.rkc.zds.entity.EMailEntity;

public interface EMailRepository extends JpaRepository<EMailEntity, Integer> {
  
	Page<EMailEntity> findByContactId(Pageable pageable, int contactId);

	List<EMailEntity> findByContactId(int contactId);
	       
}
