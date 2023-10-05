package com.sns.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
public class SnsApplicationException extends RuntimeException{

    private ErrorCode errorCode;
    private String message;

    @Override
    public String getMessage() {
        if(message == null){
            return errorCode.getErrorMessage();
        }
        return String.format("%s. %s",errorCode.getStatus(),message);
    }
}
