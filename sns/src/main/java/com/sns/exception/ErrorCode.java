package com.sns.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    DUPLICATED_USER_NAME(HttpStatus.CONFLICT,"USERNAME_IS_DUPLICATED"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "user no found"),
    INVALID_USER_PASSWORD(HttpStatus.UNAUTHORIZED , "invalid user password"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR , "Internal server error");

    private HttpStatus status;
    private String errorMessage;

}
