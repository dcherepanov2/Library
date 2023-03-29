package com.example.MyBookShopApp.aop;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.aspectj.lang.annotation.*;
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
public class AspectHistoryEndpoint {

    @Before(value = "within(com.example.MyBookShopApp.controllers..*)")
    public void addEndpointToCookieValue() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String requestURI = request.getRequestURI().equals("/") ? "/main" : request.getRequestURI();
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletResponse httpServletResponse = ((ServletRequestAttributes) Objects.requireNonNull(requestAttributes)).getResponse();
        if (request.getCookies() != null && request.getCookies().length != 0) {
            Cookie historyEndpoint = Arrays.stream(request.getCookies())
                    .filter(x -> x.getName()
                            .equals("history")).findFirst().orElse(null);
            if (historyEndpoint != null) {
                String endValue = Objects.requireNonNull(historyEndpoint).getValue() + requestURI;
                historyEndpoint.setValue(endValue);
                Objects.requireNonNull(httpServletResponse).addCookie(historyEndpoint);
                if (historyEndpoint.getValue().length() > 1000) {
                    String oldValue = historyEndpoint.getValue();
                    String newValue = oldValue.substring(oldValue.length() / 2);
                    historyEndpoint.setValue(newValue);
                }
            }
            return;
        }
        Cookie cookie = new Cookie("history", requestURI);
        cookie.setPath("/");
        Objects.requireNonNull(httpServletResponse).addCookie(cookie);
    }
}
