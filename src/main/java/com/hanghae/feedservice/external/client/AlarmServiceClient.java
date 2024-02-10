package com.hanghae.feedservice.external.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("alarm-service")
public interface AlarmServiceClient {

    @PostMapping("/save-alarm")
    void saveAlarm(
            @RequestBody Long toUserId,
            @RequestBody Long fromUserId,
            @RequestBody Long targetId,
            @RequestBody String alarmType);
}
