package com.hanghae.feedservice.domain.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    ARTICLE_NOT_FOUND(HttpStatus.NOT_FOUND, "Article not founded"),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "Comment not founded"),
    INVALID_PERMISSION(HttpStatus.UNAUTHORIZED, "User has invalid permission");



    private final HttpStatus status;
    private final String message;
}
