package com.rkc.zds.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rkc.zds.entity.ArticleFollowEntity;

public interface ArticleFollowRepository extends JpaRepository<ArticleFollowEntity, Integer> {

	ArticleFollowEntity findByUserIdAndFollowId(Integer userId, Integer followId);
	
	List<ArticleFollowEntity> findByUserId(Integer userId);
	
	ArticleFollowEntity save(ArticleFollowEntity follow);
}
