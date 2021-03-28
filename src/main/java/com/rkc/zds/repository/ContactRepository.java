package com.rkc.zds.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.rkc.zds.entity.ContactEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

public interface ContactRepository extends JpaRepository<ContactEntity, Integer>, JpaSpecificationExecutor<ContactEntity> {
  
	Page<ContactEntity> findByLastNameIgnoreCaseLike(Pageable pageable, String lastName);
	 
}
