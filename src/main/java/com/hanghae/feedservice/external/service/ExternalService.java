package com.hanghae.feedservice.external.service;

import com.hanghae.feedservice.domain.constant.ErrorCode;
import com.hanghae.feedservice.domain.entity.Article;
import com.hanghae.feedservice.domain.entity.ArticleComment;
import com.hanghae.feedservice.domain.repository.ArticleCommentRepository;
import com.hanghae.feedservice.domain.repository.ArticleRepository;
import com.hanghae.feedservice.exception.FeedServiceApplicationException;
import com.hanghae.feedservice.external.client.UserServiceClient;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExternalService {

    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;
    private final UserServiceClient userServiceClient;

    @Transactional
    public Boolean hasTarget(String likeType, Long targetId) {

        if (likeType.equals("ARTICLE")) {
            articleRepository.findById(targetId)
                    .orElseThrow(() -> new FeedServiceApplicationException(ErrorCode.ARTICLE_NOT_FOUND));
        }

        if (likeType.equals("COMMENT")) {
            articleCommentRepository.findById(targetId)
                    .orElseThrow(() -> new FeedServiceApplicationException(ErrorCode.COMMENT_NOT_FOUND));
        }

        return true;
    }

    public Optional<Long> getPostUserId(Long targetId) {
        Article article = articleRepository.findById(targetId)
                .orElseThrow(() -> new FeedServiceApplicationException(ErrorCode.ARTICLE_NOT_FOUND));
        return userServiceClient.getUserId(article.getUserEmail());
    }

    public Optional<Long> getCommentUserId(Long targetId) {
        ArticleComment articleComment = articleCommentRepository.findById(targetId)
                .orElseThrow(() -> new FeedServiceApplicationException(ErrorCode.COMMENT_NOT_FOUND));
        return userServiceClient.getUserId(articleComment.getUserEmail());
    }
}
