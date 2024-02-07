package com.hanghae.feedservice.domain.repository;

import com.hanghae.feedservice.domain.entity.ArticleComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleCommentRepository extends JpaRepository<ArticleComment, Long> {
}
