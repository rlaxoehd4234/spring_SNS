package com.sns.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Column;
import java.sql.Timestamp;

@AllArgsConstructor
@Getter
public class User {
    private Integer id;
    private String userName;
    private String password;
    private UserRole userRole;
    private Timestamp registeredAt;
    private Timestamp updatedAt;
    private Timestamp deletedAt;

    public static User fromEntity(UserEntity userEntity){
        return new User(
                userEntity.getId(),
                userEntity.getUserName(),
                userEntity.getPassword(),
                userEntity.getRole(),
                userEntity.getRegisteredAt(),
                userEntity.getUpdatedAt(),
                userEntity.getDeletedAt()
        );


    }
}
