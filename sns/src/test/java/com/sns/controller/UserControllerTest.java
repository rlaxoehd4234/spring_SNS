package com.sns.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sns.controller.request.UserLoginRequest;
import com.sns.exception.ErrorCode;
import com.sns.exception.SnsApplicationException;
import com.sns.model.User;
import com.sns.controller.request.UserJoinRequest;
import com.sns.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UserService userService;

    @Test
    public void 회원가입() throws Exception {
        String username ="username";
        String password = "password";


        when(userService.join(username, password)).thenReturn(mock(User.class));


        mockMvc.perform(post("/api/v1/users/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(new UserJoinRequest(username, password))))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    public void 회원가입시_이미_회원된_userName으로_회원가입을_하는경우_에러반환() throws Exception {
        String username = "username";
        String password = "password";

        when(userService.join(username, password)).thenThrow(new SnsApplicationException(ErrorCode.DUPLICATED_USER_NAME, String.format("duplicate username %s", username)));

        mockMvc.perform(post("/api/v1/users/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(new UserJoinRequest(username, password))))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void 로그인() throws Exception {
        String username ="username";
        String password = "password";


        when(userService.login(username, password)).thenReturn("test_token");


        mockMvc.perform(post("/api/v1/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new UserLoginRequest(username, password))))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    public void 로그인_실패_회원가입이_안된_유저로_로그인() throws Exception{
        String username = "username";
        String password = "password";

        mockMvc.perform(post("/api/v1/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(new UserLoginRequest(username, password))))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
