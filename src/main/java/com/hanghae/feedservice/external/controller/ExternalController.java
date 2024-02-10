package com.hanghae.feedservice.external.controller;

import com.hanghae.feedservice.external.service.ExternalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/feed-service")
public class ExternalController {

    private final ExternalService externalService;

    @GetMapping("/{likeType}/{targetId}")
    public Boolean hasTarget(@PathVariable String likeType, @PathVariable Long targetId) {
        return externalService.hasTarget(likeType, targetId);
    }

    @GetMapping("/posts/find-user-id")
    public Optional<Long> getPostUserId(Long targetId) {
        return externalService.getPostUserId(targetId);
    }

    @GetMapping("/comment/find-user-id")
    public Optional<Long> getUserId(Long targetId) {
        return externalService.getCommentUserId(targetId);
    }
}
