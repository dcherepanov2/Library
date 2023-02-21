package com.example.MyBookShopApp.exception;

import com.example.MyBookShopApp.security.jwt.JwtAuthenticationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class SecurityExceptionResolver implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
            throws IOException{
        Map<String, String> map = new LinkedHashMap<>();
        map.put("result: ", "false");
        map.put("state: ", "401");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        if(e instanceof JwtAuthenticationException){
            map.put("state: ", "403");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        if(e instanceof BadCredentialsException){
            map.put("state: ", "400");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        if(e instanceof UsernameNotFoundException){
            map.put("state: ", "404");
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        map.put("error", e.getMessage());
        response.setCharacterEncoding("utf-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ObjectMapper objectMapper = new ObjectMapper();
        String resBody = objectMapper.writeValueAsString(map);
        PrintWriter printWriter = response.getWriter();
        printWriter.print(resBody);
        printWriter.flush();
        printWriter.close();
    }

}
