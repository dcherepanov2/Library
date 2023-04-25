package com.example.MyBookShopApp.service.userServices;


import com.example.MyBookShopApp.data.enums.ContactType;
import com.example.MyBookShopApp.data.user.JwtLogoutToken;
import com.example.MyBookShopApp.data.user.Role;
import com.example.MyBookShopApp.data.user.User;
import com.example.MyBookShopApp.data.user.UserContactEntity;
import com.example.MyBookShopApp.dto.RegistrationForm;
import com.example.MyBookShopApp.dto.UserInfo;
import com.example.MyBookShopApp.exception.ResponseApproveContactException;
import com.example.MyBookShopApp.repo.userrepos.JwtBlacklistRepo;
import com.example.MyBookShopApp.repo.userrepos.RoleRepository;
import com.example.MyBookShopApp.repo.userrepos.UserContactRepo;
import com.example.MyBookShopApp.repo.userrepos.UserRepo;
import com.example.MyBookShopApp.security.jwt.JwtTokenProvider;
import com.example.MyBookShopApp.security.jwt.JwtUser;
import com.example.MyBookShopApp.service.userServices.helpers.UserHelper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import java.time.LocalDate;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Service
@ComponentScan(basePackages = {"com.example.MyBookShopApp.repo.userrepos"})
@Slf4j
public class UserServiceImpl {

    private final UserRepo userRepository;
    private final RoleRepository roleRepository;

    private final UserHelper userHelper;
    private final JwtBlacklistRepo jwtBlacklistRepo;

    private final UserContactRepo userContactRepo;

    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public UserServiceImpl(UserRepo userRepository, RoleRepository roleRepository, UserHelper userHelper, JwtBlacklistRepo jwtBlacklistRepo, UserContactRepo userContactRepo, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userHelper = userHelper;
        this.jwtBlacklistRepo = jwtBlacklistRepo;
        this.userContactRepo = userContactRepo;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ,
            rollbackFor = {Exception.class, RuntimeException.class})
    public User findByUsername(String username) {
        User result = userRepository.findByUsername(username);
        log.info("IN findByUsername - user: {} found by username: {}", result, username);
        return result;
    }

    public User findByHash(String username) {
        User result = userRepository.findByHash(username);
        log.info("IN findByUsername - user: {} found by username: {}", result, username);
        return result;
    }

    public User findUserByContact(String email) {
        return userRepository.findByContact(email);
    }

    public User findByPhone(String phone) {
        return userRepository.findByContact(phone);
    }
//    @Transactional(isolation = Isolation.REPEATABLE_READ,
//            rollbackFor = {Exception.class, RuntimeException.class})
//    public User createNewUserWithUserClientRole(String contact) {
//        User user = userRepository.findByContact(contact);
//        if(user == null){
//            user = new User();
//            Role role = roleRepository.findByName("ROLE_USER");
//            user.setHash(userHelper.generateHash(user));
//            user.setRoles(Collections.singletonList(role));
//            user.setUsername("Default name");
//            user.setDateRegistration(LocalDate.now());
//            user.setBalance(0.00);
//            userRepository.save(user);
//        }
//        return user;
//    }

    @SneakyThrows
    @Transactional(isolation = Isolation.READ_COMMITTED,
            rollbackFor = {Exception.class, RuntimeException.class})
    public User createNewUserWithUserClientRole(RegistrationForm registrationForm) {
        User user = null;
        List<UserContactEntity> allApprovedMessage = new ArrayList<UserContactEntity>() {{
            addAll(userContactRepo.findUserContactEntitiesByContactAndCodeTime(registrationForm.getPhone(), ContactType.PHONE, Pageable.ofSize(1)).getContent());
            addAll(userContactRepo.findUserContactEntitiesByContactAndCodeTime(registrationForm.getEmail(), ContactType.EMAIL, Pageable.ofSize(1)).getContent());
        }};
        allApprovedMessage = allApprovedMessage.stream().filter(x -> x.getApproved() == 1).collect(Collectors.toList());
        if (allApprovedMessage.size() == 2) {
            user = userRepository.findByContact(allApprovedMessage.get(0).getContact());
            if (user == null) {
                user = new User();
                Role role = roleRepository.findByName("ROLE_USER");
                user.setHash(userHelper.generateHash(user));
                user.setRoles(Collections.singletonList(role));
                user.setUsername(registrationForm.getName());
                user.setDateRegistration(LocalDate.now());
                user.setBalance(0.00);
                user = userRepository.save(user);
            }
        } else
            throw new ResponseApproveContactException("Введенные данные не были до конца подтверждены.");
        return user;
    }

    public JwtLogoutToken logoutToken(String token) {
        JwtLogoutToken jwtLogoutToken = jwtBlacklistRepo.findByName(token);
        if (jwtLogoutToken == null) {
            jwtLogoutToken = new JwtLogoutToken();
            jwtLogoutToken.setName(token);
            return jwtBlacklistRepo.save(jwtLogoutToken);
        }
        return jwtLogoutToken;
    }

    public Cookie createToken(User user) {
        Role role = roleRepository.findByName("ROLE_USER");
        String token = jwtTokenProvider.createToken(user.getHash(), Collections.singletonList(role));
        return new Cookie("token", "Bearer_" + token);
    }

    public User findUserByToken(String token) {
        String userName = jwtTokenProvider.getUsername(token.substring(7));
        return userRepository.findByUsername(userName);
    }

    public UserInfo getUserInfoForProfile(String token) {
        UserInfo userInfo = new UserInfo();
        String hash = jwtTokenProvider.getUsername(token.substring(7));
        Pageable page = PageRequest.of(0, 1);
        User user = userRepository.findByHash(hash);
        UserContactEntity email = userContactRepo.findUserContactEntitiesByUserIdAndCodeTime(user.getId(), (short) 2, page)
                .getContent().stream().findFirst().orElse(new UserContactEntity());
        UserContactEntity phone = userContactRepo.findUserContactEntitiesByUserIdAndCodeTime(user.getId(), (short) 1, page)
                .getContent().stream().findFirst().orElse(new UserContactEntity());
        userInfo.setEmailAndApprove(new AbstractMap.SimpleEntry<>(email.getContact(), email.getApproved()));
        userInfo.setPhoneAndApprove(new AbstractMap.SimpleEntry<>(phone.getContact(), phone.getApproved()));
        userInfo.setName(user.getUsername());
        userInfo.setBalance(user.getBalance());
        return userInfo;
    }

    public UserInfo getUserInfoForProfile(JwtUser jwtUser) {
        UserInfo userInfo = new UserInfo();
        Pageable page = PageRequest.of(0, 1);
        User user = userRepository.findByHash(jwtUser.getHash());
        UserContactEntity email = userContactRepo.findUserContactEntitiesByUserIdAndCodeTime(user.getId(), (short) 2, page)
                .getContent().stream().findFirst().orElse(new UserContactEntity());
        UserContactEntity phone = userContactRepo.findUserContactEntitiesByUserIdAndCodeTime(user.getId(), (short) 1, page)
                .getContent().stream().findFirst().orElse(new UserContactEntity());
        userInfo.setEmailAndApprove(new AbstractMap.SimpleEntry<>(email.getContact(), email.getApproved()));
        userInfo.setPhoneAndApprove(new AbstractMap.SimpleEntry<>(phone.getContact(), phone.getApproved()));
        userInfo.setName(user.getUsername());
        userInfo.setBalance(user.getBalance());
        return userInfo;
    }

    public void setBalance(JwtUser jwtUser, Double sum) {
        User user = userRepository.findByHash(jwtUser.getHash());
        user.setBalance(user.getBalance() + sum);
        userRepository.save(user);
    }

    public User createNewAnonymousUser() {
        Role anonymous = roleRepository.findByName("ANONYMOUS");
        User user = new User();
        user.setBalance(0.00);
        user.setRoles(Collections.singletonList(anonymous));
        user.setUsername("ANONYMOUS");
        user.setHash(userHelper.generateHash(user));
        user.setDateRegistration(LocalDate.now());
        return userRepository.save(user);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }
}
