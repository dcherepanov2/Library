package com.example.MyBookShopApp.service.userServices;


import com.example.MyBookShopApp.data.user.JwtLogoutToken;
import com.example.MyBookShopApp.data.user.Role;
import com.example.MyBookShopApp.data.user.User;
import com.example.MyBookShopApp.repo.userrepos.JwtBlacklistRepo;
import com.example.MyBookShopApp.repo.userrepos.RoleRepository;
import com.example.MyBookShopApp.repo.userrepos.UserRepo;
import com.example.MyBookShopApp.service.userServices.helpers.UserHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;


@Service
@Slf4j
public class UserServiceImpl{

    private final UserRepo userRepository;
    private final RoleRepository roleRepository;

    private final UserHelper userHelper;
    private final JwtBlacklistRepo jwtBlacklistRepo;

    @Autowired
    public UserServiceImpl(UserRepo userRepository, RoleRepository roleRepository, UserHelper userHelper,  JwtBlacklistRepo jwtBlacklistRepo) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userHelper = userHelper;
        this.jwtBlacklistRepo = jwtBlacklistRepo;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ,
            rollbackFor = {Exception.class, RuntimeException.class})
    public User findByUsername(String username) {
        User result = userRepository.findByUsername(username);
        log.info("IN findByUsername - user: {} found by username: {}", result, username);
        return result;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ,
            rollbackFor = {Exception.class, RuntimeException.class})
    public User findByEmail(String email) {
        return userRepository.findByContact(email);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ,
            rollbackFor = {Exception.class, RuntimeException.class})
    public User createNewUserWithUserClientRole(String contact) {
        User user = userRepository.findByContact(contact);
        if(user == null){
            user = new User();
            Role role = roleRepository.findByName("USER_CLIENT");
            user.setHash(userHelper.generateHash(user));
            user.setRoles(Collections.singletonList(role));
            user.setUsername(contact);
            user.setDateRegistration(LocalDate.now());
            user.setBalance(0.00);
        }
        return user;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ,
            rollbackFor = {Exception.class, RuntimeException.class})
    public JwtLogoutToken logoutToken(String token) {
        JwtLogoutToken jwtLogoutToken = jwtBlacklistRepo.findByName(token);
        if (jwtLogoutToken == null) {
            jwtLogoutToken = new JwtLogoutToken();
            jwtLogoutToken.setName(token);
            return jwtBlacklistRepo.save(jwtLogoutToken);
        }
        return jwtLogoutToken;
    }
}
