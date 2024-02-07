package com.hanghae.feedservice.service;

import com.hanghae.feedservice.domain.constant.ErrorCode;
import com.hanghae.feedservice.domain.entity.Article;
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
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final RestTemplate restTemplate;

    @Transactional
    public void create(String title, String content, HttpHeaders headers) {
        Article article = Article.of(getUserAccountEmail(headers).getBody(), title, content);
        articleRepository.save(article);
    }

    @Transactional
    public void modify(Long feedId, String title, String content, HttpHeaders headers) {
        Article article = articleRepository.findById(feedId)
                .orElseThrow(() -> new FeedServiceApplicationException(ErrorCode.ARTICLE_NOT_FOUND));

        if (!Objects.equals(article.getUserEmail(), getUserAccountEmail(headers).getBody())) {
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

        if (!Objects.equals(article.getUserEmail(), getUserAccountEmail(headers).getBody())) {
            throw new FeedServiceApplicationException(ErrorCode.INVALID_PERMISSION);
        }

        articleRepository.delete(article);
    }

    private ResponseEntity<String> getUserAccountEmail(HttpHeaders headers) {
        String userAccountUrl = "http://127.0.0.1:8000/user-service/users/email";
        HttpEntity<String> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(userAccountUrl, HttpMethod.GET, entity,
                String.class);
    }
}
