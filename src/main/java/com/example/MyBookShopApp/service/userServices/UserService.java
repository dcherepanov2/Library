package com.example.MyBookShopApp.service.userServices;


import com.example.MyBookShopApp.data.user.JwtLogoutToken;
import com.example.MyBookShopApp.data.user.User;
import com.example.MyBookShopApp.dto.ResponseVerificationDto;
import com.example.MyBookShopApp.exception.VerificationException;

public interface UserService {

    User register(User user);

    User setNewEmail(User user);

    User findByUsername(String username);

    User findByEmail(String email);

    JwtLogoutToken logoutToken(String token);

    String generateCode(User user);

    ResponseVerificationDto verification(String username, String code) throws VerificationException;

    void setLastAuth(User user);
}