package com.hanghae.feedservice.exception;

import com.hanghae.feedservice.domain.constant.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FeedServiceApplicationException extends RuntimeException {

    private ErrorCode errorCode;
    private String message;

    public FeedServiceApplicationException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.message = null;
    }

    public FeedServiceApplicationException() {

    }

    @Override
    public String getMessage() {
        if (message == null) {
            return errorCode.getMessage();
        } else {
            return String.format("%s. %s", errorCode.getMessage(), message);
        }
    }
}
