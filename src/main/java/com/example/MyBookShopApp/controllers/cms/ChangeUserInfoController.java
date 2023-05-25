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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cms/user")
public class ChangeUserInfoController {
    private final UserServiceImpl userService;
    private final ContactService contactService;
    private final Book2UserService book2UserService;
    private final ChangeUserService changeUserService;

    @Autowired
    public ChangeUserInfoController(UserServiceImpl userService, ContactService contactService, Book2UserService book2UserService, ChangeUserService changeUserService) {
        this.userService = userService;
        this.contactService = contactService;
        this.book2UserService = book2UserService;
        this.changeUserService = changeUserService;
    }

    @GetMapping("/find-user-by/{contact}")
    public UserInfo getUser(@PathVariable("contact") String contact){
        JwtUser userFind = null;
        UserContactEntity userContactApprovedByContactName = contactService.findUserContactApprovedByContactName(contact);
        if(userContactApprovedByContactName != null)
            userFind = userService.covertJwtUserByUsername(userContactApprovedByContactName.getUserId().getId());
        if(userFind == null)
            throw new UsernameNotFoundException("Пользователь с таким контактом не найден.");
        UserInfoCms userInfoForProfile = new UserInfoCms(userService.getUserInfoForProfile(userFind));
        userInfoForProfile.setId(userFind.getId());
        return userInfoForProfile;
    }

    @PostMapping("/edit/{id}")
    public ResultTrue editUserInfo(@RequestBody EditUserInfo userInfo, @PathVariable("id") Long id){
        User userById = userService.getUserById(id);
        if(userById == null)
            throw new UsernameNotFoundException("Пользователь с таким id не найден.");
        changeUserService.editUserInfo(userById,userInfo);
        ResultTrue resultTrue = new ResultTrue();
        resultTrue.setResult(true);
        return resultTrue;
    }

    @PostMapping("/add/book")
    public String editBookshelf(@RequestBody AddBook2UserDto addBook2UserDto) throws BookException {
        JwtUser user = userService.covertJwtUserByUsername(addBook2UserDto.getUserSlug());
        if(user == null)
            throw new UsernameNotFoundException("Пользователь с таким id не найден.");
        boolean checkBook = book2UserService.getBooksUser(user, 3).stream()
                                            .noneMatch(x -> x.getSlug().equals(addBook2UserDto.getBookSlug()));
        if(checkBook)
            book2UserService.save(user,addBook2UserDto.getBookSlug(), 3);
        else
            throw new BookException("Книга уже добавлена к пользователю.");
        return "index";
    }
}
