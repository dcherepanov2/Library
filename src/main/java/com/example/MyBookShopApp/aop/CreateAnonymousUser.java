package com.example.MyBookShopApp.aop;

import com.example.MyBookShopApp.data.user.User;
import com.example.MyBookShopApp.security.jwt.JwtTokenProvider;
import com.example.MyBookShopApp.service.userServices.UserServiceImpl;
import com.example.MyBookShopApp.utils.StringUtils;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Objects;

@Aspect
@Component
public class CreateAnonymousUser {

    private final JwtTokenProvider jwtTokenProvider;

    private final UserServiceImpl userService;

    @Autowired
    public CreateAnonymousUser(JwtTokenProvider jwtTokenProvider, UserServiceImpl userService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }


    @Pointcut("within(com.example.MyBookShopApp.controllers..*)")
    public void pointcut() {
    }

    @Before("pointcut()")
    public Cookie applicationLogger() {
        Cookie cookieResponse = null;
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = ((ServletRequestAttributes) Objects.requireNonNull(requestAttributes)).getResponse();
        if (request.getCookies() != null && request.getCookies().length != 0 && response != null) {
            Cookie token = Arrays.stream(request.getCookies())
                    .filter(x -> x.getName()
                            .equals("token")).findFirst().orElse(null);
            if (token == null) {
                User anonymous = userService.createNewAnonymousUser();
                String tokenString = jwtTokenProvider.createToken(anonymous.getHash(), anonymous.getRoles());
                cookieResponse = new Cookie("token", tokenString);
                response.addCookie(cookieResponse);
            } else {
                cookieResponse = token;
                return cookieResponse;
            }
        } else if (request.getCookies() == null || request.getCookies().length != 0 && response != null) {
            User anonymous = userService.createNewAnonymousUser();
            String tokenString = jwtTokenProvider.createToken(anonymous.getHash(), anonymous.getRoles());
            cookieResponse = new Cookie("token", tokenString);
            Objects.requireNonNull(response).addCookie(cookieResponse);
        }
        return cookieResponse;
    }

    @Before("pointcut()")
    public void deleteCharacters() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = ((ServletRequestAttributes) Objects.requireNonNull(requestAttributes)).getResponse();
        if (request.getCookies() != null && request.getCookies().length == 0) {
            Cookie token = Arrays.stream(request.getCookies())
                    .filter(x -> x.getName()
                            .equals("token")).findFirst().orElse(null);
            if (token != null && response != null) {
                String tokenString = StringUtils.deleteStringIntoString(token.getValue(), "\\u0005�z��Ș[\\u0019Ȏ��\\u0014̍M��");
                Cookie cookie = new Cookie("token", tokenString);
                response.addCookie(cookie);
            }
        }
    }
}
