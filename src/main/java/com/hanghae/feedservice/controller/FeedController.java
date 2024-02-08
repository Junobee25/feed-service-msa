package com.hanghae.feedservice.controller;

import com.hanghae.feedservice.dto.request.ArticleCommentWriteRequest;
import com.hanghae.feedservice.dto.request.ArticleModifyRequest;
import com.hanghae.feedservice.dto.request.ArticleWriteRequest;
import com.hanghae.feedservice.dto.response.Response;
import com.hanghae.feedservice.service.ArticleCommentService;
import com.hanghae.feedservice.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/feed-service")
public class FeedController {

    private final ArticleService articleService;
    private final ArticleCommentService articleCommentService;

    @PostMapping("/posts")
    public Response<Void> createFeed(ArticleWriteRequest request, @RequestHeader HttpHeaders headers) {
        articleService.create(request.title(), request.content(), headers);
        return Response.success();
    }

    @PostMapping("/comments/{feedId}")
    public Response<Void> createComment(@PathVariable Long feedId, ArticleCommentWriteRequest request, @RequestHeader HttpHeaders headers) {
        articleCommentService.create(feedId, request.content(), headers);
        return Response.success();
    }

    @PutMapping("/posts/{feedId}")
    public Response<Void> modifyFeed(@PathVariable Long feedId, ArticleModifyRequest request, @RequestHeader HttpHeaders headers) {
        articleService.modify(feedId, request.title(), request.content(), headers);
        return Response.success();
    }

    @PutMapping("/comments/{commentId}")
    public Response<Void> modifyComment(@PathVariable Long commentId, ArticleCommentWriteRequest request, @RequestHeader HttpHeaders headers) {
        articleCommentService.modify(commentId, request.content(), headers);
        return Response.success();
    }

    @DeleteMapping("/posts/{feedId}")
    public Response<Void> deleteFeed(@PathVariable Long feedId, @RequestHeader HttpHeaders headers) {
        articleService.delete(feedId, headers);
        return Response.success();
    }

    @DeleteMapping("/comments/{commentId}")
    public Response<Void> deleteComment(@PathVariable Long commentId, @RequestHeader HttpHeaders headers) {
        articleCommentService.delete(commentId, headers);
        return Response.success();
    }
}
