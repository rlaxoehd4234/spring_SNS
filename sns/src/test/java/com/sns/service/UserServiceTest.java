package com.sns.service;

import com.sns.exception.SnsApplicationException;
import com.sns.model.UserEntity;
import com.sns.repository.UserEntityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    UserService userService;
    @Autowired
    private UserEntityRepository userEntityRepository;


    @Test
    public void 정상가입(){

        String username = "username";
        String password = "password";

        //mocking
        when(userEntityRepository.findByUserName(username)).thenReturn(Optional.empty());
        when(userEntityRepository.save(any())).thenReturn(Optional.of(mock(UserEntity.class)));

        Assertions.assertDoesNotThrow(() -> userService.join(username,password));
    }
    @Test
    public void 가입시_존재하는_이름일_경우(){

        String username = "username";
        String password = "password";



        //mocking
        when(userEntityRepository.findByUserName(username)).thenReturn(Optional.of(mock(UserEntity.class)));
        when(userEntityRepository.save(any())).thenReturn(Optional.of(mock(UserEntity.class)));



        Assertions.assertThrows(SnsApplicationException.class, () -> userService.join(username,password));
    }
}
