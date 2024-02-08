package com.hanghae.feedservice.external.service;

import com.hanghae.feedservice.domain.constant.ErrorCode;
import com.hanghae.feedservice.domain.repository.ArticleCommentRepository;
import com.hanghae.feedservice.domain.repository.ArticleRepository;
import com.hanghae.feedservice.exception.FeedServiceApplicationException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikesTargetService {

    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;

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
}
