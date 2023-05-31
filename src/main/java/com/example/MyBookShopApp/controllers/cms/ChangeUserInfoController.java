package com.example.MyBookShopApp.controllers.cms;

import com.example.MyBookShopApp.dto.AddBook2UserDto;
import com.example.MyBookShopApp.dto.EditUserInfo;
import com.example.MyBookShopApp.dto.ResultTrue;
import com.example.MyBookShopApp.dto.UserInfo;
import com.example.MyBookShopApp.exception.BookException;
import com.example.MyBookShopApp.service.cms.ChangeUserService;
import org.springframework.beans.factory.annotation.Autowired;
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
