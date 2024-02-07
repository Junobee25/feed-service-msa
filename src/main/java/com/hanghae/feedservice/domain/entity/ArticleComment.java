package com.hanghae.feedservice.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
@Entity
public class ArticleComment extends AuditingField {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne(optional = false)
    private Article article;

    @Setter
    @Column(name = "user_email")
    private String userEmail;

    @Setter
    @Column(nullable = false, length = 500)
    private String content;

    protected ArticleComment() {

    }

    private ArticleComment(String userEmail, Article article, String content) {
        this.userEmail = userEmail;
        this.article = article;
        this.content = content;
    }

    public static ArticleComment of(String userEmail, Article article, String content) {
        return new ArticleComment(userEmail, article, content);
    }
}
