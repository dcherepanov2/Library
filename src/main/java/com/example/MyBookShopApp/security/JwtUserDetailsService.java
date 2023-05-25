package com.example.MyBookShopApp.security;




import com.example.MyBookShopApp.data.user.User;
import com.example.MyBookShopApp.security.jwt.JwtUser;
import com.example.MyBookShopApp.security.jwt.JwtUserFactory;
import com.example.MyBookShopApp.service.userServices.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {

    private final UserServiceImpl userService;

    @Autowired
    public JwtUserDetailsService(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String hash) throws UsernameNotFoundException {
        User user = userService.findByHash(hash);
        if (user == null)
            return null;
        JwtUser jwtUser = JwtUserFactory.create(user);
        log.info("IN loadUserByUsername - user with username: {} successfully loaded", user.getUsername());
        return jwtUser;
    }
}
