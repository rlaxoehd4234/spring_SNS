package com.sns.service;

import com.sns.exception.ErrorCode;
import com.sns.exception.SnsApplicationException;
import com.sns.model.User;
import com.sns.model.UserEntity;
import com.sns.repository.UserEntityRepository;
import com.sns.util.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserEntityRepository userEntityRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.token.expired-time-ms}")
    private Long expiredTimeMs;

    @Transactional
    public User join(String username, String password){
        userEntityRepository.findByUserName(username).ifPresent(it ->{
            throw new SnsApplicationException(ErrorCode.DUPLICATED_USER_NAME, String.format("duplicated user name : %s" , username));
        });
        UserEntity userEntity = userEntityRepository.save(UserEntity.of(username, bCryptPasswordEncoder.encode(password)));

        return User.fromEntity(userEntity);
    }

    public String login(String username, String password){
        //회원 가입 한 유저인지
        UserEntity userEntity = userEntityRepository.findByUserName(username).orElseThrow(() -> new SnsApplicationException(ErrorCode.USER_NOT_FOUND ,"not found error"));
        //비밀번호 체크

        if(!bCryptPasswordEncoder.matches(password , userEntity.getPassword())){
//        if(!Objects.equals(userEntity.getPassword(), password)){
            throw new SnsApplicationException(ErrorCode.INVALID_USER_PASSWORD ," password error");
        }
        //토큰 생성
        String token = JwtTokenUtils.generateToken(username, secretKey , expiredTimeMs);

        return token;
    }

}
