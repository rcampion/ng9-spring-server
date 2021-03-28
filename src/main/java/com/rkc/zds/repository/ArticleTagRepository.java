package com.rkc.zds.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.rkc.zds.entity.ArticleEntity;
import com.rkc.zds.entity.ArticleTagEntity;

public interface ArticleTagRepository extends JpaRepository<ArticleTagEntity, Integer>, JpaSpecificationExecutor<ArticleTagEntity> {
	
	ArticleTagEntity findByName(String tag);


}
