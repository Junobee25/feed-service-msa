package com.hanghae.feedservice.service;

import com.hanghae.feedservice.domain.constant.ErrorCode;
import com.hanghae.feedservice.domain.entity.Article;
import com.hanghae.feedservice.domain.entity.ArticleComment;
import com.hanghae.feedservice.domain.repository.ArticleCommentRepository;
import com.hanghae.feedservice.domain.repository.ArticleRepository;
import com.hanghae.feedservice.exception.FeedServiceApplicationException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ArticleCommentService {

    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;
    private final RestTemplate restTemplate;

    @Transactional
    public void create(Long feedId, String content, HttpHeaders headers) {
        Article article = articleRepository.findById(feedId)
                .orElseThrow(() -> new FeedServiceApplicationException(ErrorCode.ARTICLE_NOT_FOUND));

        ArticleComment articleComment = ArticleComment.of(getUserAccountEmail(headers).getBody(), article, content);
        articleCommentRepository.save(articleComment);
    }

    @Transactional
    public void modify(Long commentId, String content, HttpHeaders headers) {
        ArticleComment articleComment = articleCommentRepository.findById(commentId)
                .orElseThrow(() -> new FeedServiceApplicationException(ErrorCode.COMMENT_NOT_FOUND));

        if (!Objects.equals(articleComment.getUserEmail(), getUserAccountEmail(headers).getBody())) {
            throw new FeedServiceApplicationException(ErrorCode.INVALID_PERMISSION);
        }

        articleComment.setContent(content);
        articleCommentRepository.save(articleComment);
    }

    @Transactional
    public void delete(Long commentId, HttpHeaders headers) {
        ArticleComment articleComment = articleCommentRepository.findById(commentId)
                .orElseThrow(() -> new FeedServiceApplicationException(ErrorCode.COMMENT_NOT_FOUND));

        if (!Objects.equals(articleComment.getUserEmail(), getUserAccountEmail(headers).getBody())) {
            throw new FeedServiceApplicationException(ErrorCode.INVALID_PERMISSION);
        }

        articleCommentRepository.delete(articleComment);
    }

    private ResponseEntity<String> getUserAccountEmail(HttpHeaders headers) {
        String userAccountUrl = "http://127.0.0.1:8000/user-service/users/email";
        HttpEntity<String> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(userAccountUrl, HttpMethod.GET, entity,
                String.class);
    }
}
