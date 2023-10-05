package com.sns.controller.response;

import com.sns.model.User;
import com.sns.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserJoinResponse {
    private Integer id;
    private String userName;
    private UserRole role;


    public static UserJoinResponse fromUser(User user){
        return new UserJoinResponse(
                user.getId(),
                user.getUserName(),
                user.getUserRole()
        );
    }
}
