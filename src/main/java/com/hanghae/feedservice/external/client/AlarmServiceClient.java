package com.hanghae.feedservice.external.client;

import com.hanghae.feedservice.external.dto.request.AlarmRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("alarm-service")
public interface AlarmServiceClient {

    @PostMapping("/alarm-service/save-alarm")
    void saveAlarm(@RequestBody AlarmRequest alarmRequest);
}
