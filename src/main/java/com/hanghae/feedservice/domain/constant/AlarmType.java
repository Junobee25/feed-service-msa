package com.hanghae.feedservice.domain.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AlarmType {

    NEW_POST("new post"),
    NEW_COMMENT_ON_POST("new comment");

    private final String alarmType;
}
