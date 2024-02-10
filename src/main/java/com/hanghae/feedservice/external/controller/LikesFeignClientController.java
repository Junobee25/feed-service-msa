package com.hanghae.feedservice.external.controller;

import com.hanghae.feedservice.external.service.LikesTargetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/feed-service")
public class LikesFeignClientController {

    private final LikesTargetService likesTargetService;

    @GetMapping("/{likeType}/{targetId}")
    public Boolean hasTarget(@PathVariable String likeType, @PathVariable Long targetId) {
        return likesTargetService.hasTarget(likeType, targetId);
    }
}
