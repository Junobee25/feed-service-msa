package com.hanghae.feedservice.external.dto.request;

import com.hanghae.feedservice.domain.constant.AlarmType;

public record AlarmRequest(
        Long toUserId,
        Long fromUserId,
        Long targetId,
        AlarmType alarmType
) {
    public static AlarmRequest of(Long toUserId,
                                  Long fromUserId,
                                  Long targetId,
                                  AlarmType alarmType) {
        return new AlarmRequest(toUserId, fromUserId, targetId, alarmType);
    }
}
