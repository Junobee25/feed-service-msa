package com.hanghae.feedservice.service;

import com.hanghae.feedservice.domain.constant.AlarmType;
import com.hanghae.feedservice.external.client.AlarmServiceClient;
import com.hanghae.feedservice.external.client.UserServiceClient;
import com.hanghae.feedservice.domain.constant.ErrorCode;
import com.hanghae.feedservice.domain.entity.Article;
import com.hanghae.feedservice.domain.entity.ArticleComment;
import com.hanghae.feedservice.domain.repository.ArticleCommentRepository;
import com.hanghae.feedservice.domain.repository.ArticleRepository;
import com.hanghae.feedservice.exception.FeedServiceApplicationException;
import com.hanghae.feedservice.external.dto.request.AlarmRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleCommentService {

    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;
    private final UserServiceClient userServiceClient;
    private final AlarmServiceClient alarmServiceClient;

    @Transactional
    public void create(Long feedId, String content, HttpHeaders headers) {
        Article article = articleRepository.findById(feedId)
                .orElseThrow(() -> new FeedServiceApplicationException(ErrorCode.ARTICLE_NOT_FOUND));

        ArticleComment articleComment = ArticleComment.of(userServiceClient.getMyEmail(headers), article, content);
        articleCommentRepository.save(articleComment);

        Optional<Long> fromUserId = userServiceClient.getUserId(articleComment.getUserEmail());
        Optional<Long> toUserId = userServiceClient.getUserId(article.getUserEmail());

        if (fromUserId.isPresent() && toUserId.isPresent()) {
            alarmServiceClient.saveAlarm(AlarmRequest.of(toUserId.get(), fromUserId.get(), feedId, AlarmType.NEW_COMMENT_ON_POST));
        }
    }

    @Transactional
    public void modify(Long commentId, String content, HttpHeaders headers) {
        ArticleComment articleComment = articleCommentRepository.findById(commentId)
                .orElseThrow(() -> new FeedServiceApplicationException(ErrorCode.COMMENT_NOT_FOUND));

        if (!Objects.equals(articleComment.getUserEmail(), userServiceClient.getMyEmail(headers))) {
            throw new FeedServiceApplicationException(ErrorCode.INVALID_PERMISSION);
        }

        articleComment.setContent(content);
        articleCommentRepository.save(articleComment);
    }

    @Transactional
    public void delete(Long commentId, HttpHeaders headers) {
        ArticleComment articleComment = articleCommentRepository.findById(commentId)
                .orElseThrow(() -> new FeedServiceApplicationException(ErrorCode.COMMENT_NOT_FOUND));

        if (!Objects.equals(articleComment.getUserEmail(), userServiceClient.getMyEmail(headers))) {
            throw new FeedServiceApplicationException(ErrorCode.INVALID_PERMISSION);
        }

        articleCommentRepository.delete(articleComment);
    }
}
