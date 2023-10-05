package com.sns.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
public class UserLoginResponse {
    private String token;
}
