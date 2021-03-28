package com.rkc.zds.core.service;

import com.rkc.zds.entity.ArticleCommentEntity;
import com.rkc.zds.entity.ArticleEntity;
import com.rkc.zds.entity.UserEntity;

public class AuthorizationService {
    public static boolean canWriteArticle(UserEntity user, ArticleEntity article) {
        return user.getId().equals(article.getUserId());
    }

    public static boolean canWriteComment(UserEntity user, ArticleEntity article, ArticleCommentEntity comment) {
        return user.getId().equals(article.getUserId()) || user.getId().equals(comment.getUserId());
    }
}
