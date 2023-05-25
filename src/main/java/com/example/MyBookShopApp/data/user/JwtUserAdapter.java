package com.example.MyBookShopApp.data.user;

import com.example.MyBookShopApp.security.jwt.JwtUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

public class JwtUserAdapter {

    public static JwtUser from(User user) {
        List<GrantedAuthority> authorities = user.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

        return new JwtUser(
                user.getId(),
                user.getUsername(),
                user.getBalance(),
                user.getDateRegistration(),
                user.getHash(),
                authorities,
                true
        );
    }
}