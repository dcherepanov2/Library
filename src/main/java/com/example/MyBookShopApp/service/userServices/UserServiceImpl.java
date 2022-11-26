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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;


@Service
@Slf4j
public class UserServiceImpl{

    private final UserRepo userRepository;
    private final RoleRepository roleRepository;

    private final UserHelper userHelper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtBlacklistRepo jwtBlacklistRepo;

    @Autowired
    public UserServiceImpl(UserRepo userRepository, RoleRepository roleRepository, UserHelper userHelper, BCryptPasswordEncoder passwordEncoder, JwtBlacklistRepo jwtBlacklistRepo) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userHelper = userHelper;
        this.passwordEncoder = passwordEncoder;
        this.jwtBlacklistRepo = jwtBlacklistRepo;
    }

//    @Transactional(isolation = Isolation.REPEATABLE_READ,
//            rollbackFor = {Exception.class, RuntimeException.class})
//    public User register(User user) {
//        if (userRepository.findByUsername(user.getUsername()) != null) {
//            throw new BadCredentialsException("User with name: " + user.getUsername() + " all ready exists");
//        }
//        if (userRepository.findByEmail(user.getEmail()) != null){//защита от перебора email
//            user.setEmail("123ы123@mail.ru");
//            return user;
//        }
//        Role roleUser = roleRepository.findByName("ROLE_USER");
//        List<Role> userRoles = new ArrayList<>();
//        userRoles.add(roleUser);
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        user.setRoles(userRoles);
//        user.setStatus(Status.NOT_ACTIVE);
//        user.setLastCode(UUID.randomUUID().toString());
//        user.setDateLastAuth(LocalDate.now());
//        user.setDateRegistration(LocalDate.now());
//        User registeredUser = null;
//        registeredUser = userRepository.save(user);
//
//        log.info("IN register - user: {} successfully registered", registeredUser);
//
//        return registeredUser;
//    }

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
        User user = userRepository.findByContact(email);
        return user;
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

//    @Transactional(isolation = Isolation.REPEATABLE_READ,
//            rollbackFor = {Exception.class})
//    public ResponseVerificationDto verification(String username, String code) throws VerificationException {
//        ResponseVerificationDto responseVerificationDto = new ResponseVerificationDto();
//        User user = userRepository.findByUsername(username);
//        if (user == null)
//            throw new VerificationException("User not found exception");
//        if (user.getLastCode().equals(code)) {
//            responseVerificationDto.setStatus("OK");
//            responseVerificationDto.setMessage("Verification has been successful");
//            user.setStatus(Status.ACTIVE);
//            userRepository.save(user);
//            return responseVerificationDto;
//        }
//        throw new VerificationException("Code uncorrectly");
//    }
//
//    @Transactional(isolation = Isolation.REPEATABLE_READ,
//            rollbackFor = {Exception.class, RuntimeException.class})
//    public String generateCode(User user) {
//        if (user != null) {
//            user.setLastCode(UUID.randomUUID().toString());
//            userRepository.save(user);
//            return user.getLastCode();
//        }
//        return null;
//    }
//
//    @Transactional(isolation = Isolation.REPEATABLE_READ,
//            rollbackFor = {Exception.class, RuntimeException.class})
//    public User setNewEmail(User user) {
//        User newUser = userRepository.findByUsername(user.getUsername());
//        if(newUser != null){
//            newUser.setEmail(user.getEmail());
//            userRepository.save(newUser);
//        }
//        return newUser;
//    }
//
//    @Transactional(isolation = Isolation.REPEATABLE_READ,
//            rollbackFor = {Exception.class, RuntimeException.class})
//    public void setLastAuth(User user){
//        User newUser = userRepository.findByUsername(user.getUsername());
//        if(newUser != null){
//            newUser.setDateLastAuth(LocalDate.now());
//            userRepository.save(newUser);
//        }
//    }
}
