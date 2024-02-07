package com.hanghae.feedservice.dto.request;

public record ArticleWriteRequest(
        String title,
        String content
) {
}
