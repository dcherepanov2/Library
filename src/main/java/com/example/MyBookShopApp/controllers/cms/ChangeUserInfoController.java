package com.example.MyBookShopApp.controllers.cms;

import com.example.MyBookShopApp.data.user.User;
import com.example.MyBookShopApp.data.user.UserContactEntity;
import com.example.MyBookShopApp.dto.*;
import com.example.MyBookShopApp.exception.BookException;
import com.example.MyBookShopApp.security.jwt.JwtUser;
import com.example.MyBookShopApp.service.bookServices.Book2UserService;
import com.example.MyBookShopApp.service.cms.ChangeUserService;
import com.example.MyBookShopApp.service.userServices.ContactService;
import com.example.MyBookShopApp.service.userServices.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/cms/user")
@Validated
public class ChangeUserInfoController {
    private final ChangeUserService changeUserService;

    @Autowired
    public ChangeUserInfoController(ChangeUserService changeUserService) {
        this.changeUserService = changeUserService;
    }

    @GetMapping("/find-user-by/{contact}")
    public UserInfo getUser(@PathVariable("contact") String contact){
        return changeUserService.findUserById(contact);
    }

    @PostMapping("/edit/{id}")
    public ResultTrue editUserInfo(@RequestBody @Valid EditUserInfo userInfo, @PathVariable("id") Long id) {
        changeUserService.editUserInfo(id, userInfo);
        ResultTrue resultTrue = new ResultTrue();
        resultTrue.setResult(true);
        return resultTrue;
    }

    @PostMapping("/add/book")
    public ResultTrue editBookshelf(@RequestBody @Valid AddBook2UserDto addBook2UserDto) throws BookException {
        changeUserService.editBookShelf(addBook2UserDto);
        ResultTrue resultTrue = new ResultTrue();
        resultTrue.setResult(true);
        return resultTrue;
    }
}
