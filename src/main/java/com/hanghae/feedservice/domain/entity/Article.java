package com.hanghae.feedservice.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@ToString(callSuper = true)
@Entity
public class Article extends AuditingField {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(name = "user_email")
    private String userEmail;

    @Setter
    @Column(nullable = false)
    private String title;
    @Setter
    @Column(nullable = false, length = 10000)
    private String content;

    @OrderBy("createdAt DESC")
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    @ToString.Exclude
    private final Set<ArticleComment> articleComments = new LinkedHashSet<>();

    protected Article() {

    }

    private Article(String userEmail, String title, String content) {
        this.userEmail = userEmail;
        this.title = title;
        this.content = content;
    }

    public static Article of(String userEmail, String title, String content) {
        return new Article(userEmail, title, content);
    }
}
