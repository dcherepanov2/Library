package com.example.MyBookShopApp.integration;

import com.example.MyBookShopApp.data.user.Role;
import com.example.MyBookShopApp.data.user.User;
import com.example.MyBookShopApp.data.user.UserContactEntity;
import com.example.MyBookShopApp.dto.ApproveContactDto;
import com.example.MyBookShopApp.dto.ContactRequestDtoV2;
import com.example.MyBookShopApp.repo.userrepos.UserContactRepo;
import com.example.MyBookShopApp.repo.userrepos.UserRepo;
import com.example.MyBookShopApp.security.jwt.JwtTokenProvider;
import com.google.gson.Gson;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application_test.properties")
public class RegisterNewUserAndLogin {
    private final MockMvc mockMvc;

    private final Gson gson;

    private final UserContactRepo userContactRepo;

    private final UserRepo userRepo;
    private String bearerToken;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public RegisterNewUserAndLogin(MockMvc mockMvc, UserContactRepo userContactRepo, UserRepo userRepo, JwtTokenProvider jwtTokenProvider) {
        this.mockMvc = mockMvc;
        this.gson = new Gson();
        this.userContactRepo = userContactRepo;
        this.userRepo = userRepo;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @BeforeEach
    public void loginBefore(){
        User byUsername = userRepo.findByUsername("danilcherep7@gmail.com");
        if(byUsername == null)
            throw new AssertionFailedError("Не найден пользователь");
        Role role = new Role("USER_CLIENT");
        bearerToken ="Bearer_" + jwtTokenProvider.createToken(byUsername.getUsername(), Collections.singletonList(role));
    }

    @Test
    @SneakyThrows
    public void login(){
        ContactRequestDtoV2 contactRequestDtoV2 = new ContactRequestDtoV2();
        contactRequestDtoV2.setContact("danilcherep7@gmail.com");
        String json = gson.toJson(contactRequestDtoV2);
        MvcResult mvcResult = mockMvc.perform(post("/requestContactConfirmation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("\"result\":true"));
    }

    @Test
    @SneakyThrows
    public void approveContact(){
        UserContactEntity contactEntity = userContactRepo.findByContactOrderByCodeTimeDesc("danilcherep7@gmail.com");
        ApproveContactDto approveContactDto = new ApproveContactDto();
        approveContactDto.setContact("danilcherep7@gmail.com");
        approveContactDto.setCode(contactEntity.getContact());
        String json = gson.toJson(approveContactDto);
        MvcResult mvcResult = mockMvc.perform(post("/requestContactConfirmation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("\"result\":true"));

    }

    @Test
    @SneakyThrows
    public void logout(){
        MvcResult authorization = mockMvc.perform(post("/user/logout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", bearerToken))
                .andDo(print())
                .andExpect(status().is2xxSuccessful()).andReturn();
        assertTrue(authorization.getResponse().getContentAsString().contains("\"result\":true"));
    }

    @AfterEach
    public void checkUserRecordInDataBase(){
        User user = userRepo.findByUsername("danilcherep7@gmail.com");
        assertNotNull(user);
    }
}
