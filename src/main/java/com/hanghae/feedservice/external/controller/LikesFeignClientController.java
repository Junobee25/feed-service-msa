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

    @GetMapping("/{domainType}/{targetId}")
    public Boolean hasTarget(@PathVariable String domainType, @PathVariable Long targetId, @RequestHeader HttpHeaders headers) {
        return likesTargetService.hasTarget(domainType, targetId);
    }
}
