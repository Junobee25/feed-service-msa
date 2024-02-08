package com.hanghae.feedservice.external.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name="user-service")
public interface UserServiceClient {

    @GetMapping("/user-service/users/email")
    String getMyEmail(@RequestHeader HttpHeaders headers);
}
