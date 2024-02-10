package com.hanghae.feedservice.service;

import com.hanghae.feedservice.domain.constant.AlarmType;
import com.hanghae.feedservice.external.client.AlarmServiceClient;
import com.hanghae.feedservice.external.client.UserServiceClient;
import com.hanghae.feedservice.domain.constant.ErrorCode;
import com.hanghae.feedservice.domain.entity.Article;
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
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final UserServiceClient userServiceClient;
    private final AlarmServiceClient alarmServiceClient;

    @Transactional
    public void create(String title, String content, HttpHeaders headers) {
        String userEmail = userServiceClient.getMyEmail(headers);
        Article article = Article.of(userEmail, title, content);
        articleRepository.save(article);

        Optional<Long> fromUserId = userServiceClient.getUserId(userEmail);
        fromUserId.ifPresent(aLong -> alarmServiceClient.saveAlarm(AlarmRequest.of(aLong, aLong, article.getId(), AlarmType.NEW_POST)));
    }

    @Transactional
    public void modify(Long feedId, String title, String content, HttpHeaders headers) {
        Article article = articleRepository.findById(feedId)
                .orElseThrow(() -> new FeedServiceApplicationException(ErrorCode.ARTICLE_NOT_FOUND));

        if (!Objects.equals(article.getUserEmail(), userServiceClient.getMyEmail(headers))) {
            throw new FeedServiceApplicationException(ErrorCode.INVALID_PERMISSION);
        }

        article.setTitle(title);
        article.setContent(content);
        articleRepository.save(article);
    }

    @Transactional
    public void delete(Long feedId, HttpHeaders headers) {
        Article article = articleRepository.findById(feedId)
                .orElseThrow(() -> new FeedServiceApplicationException(ErrorCode.ARTICLE_NOT_FOUND));

        if (!Objects.equals(article.getUserEmail(), userServiceClient.getMyEmail(headers))) {
            throw new FeedServiceApplicationException(ErrorCode.INVALID_PERMISSION);
        }

        articleRepository.delete(article);
    }
}
