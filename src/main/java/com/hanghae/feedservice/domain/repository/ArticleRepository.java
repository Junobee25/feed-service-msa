package com.hanghae.feedservice.domain.repository;

import com.hanghae.feedservice.domain.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {

}
