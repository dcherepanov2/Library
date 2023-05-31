package com.example.MyBookShopApp.service.cms;

import com.example.MyBookShopApp.data.user.User;
import com.example.MyBookShopApp.data.user.UserContactEntity;
import com.example.MyBookShopApp.dto.AddBook2UserDto;
import com.example.MyBookShopApp.dto.EditUserInfo;
import com.example.MyBookShopApp.dto.UserInfoCms;
import com.example.MyBookShopApp.exception.BookException;
import com.example.MyBookShopApp.repo.userrepos.UserContactRepo;
import com.example.MyBookShopApp.security.jwt.JwtUser;
import com.example.MyBookShopApp.service.bookServices.Book2UserService;
import com.example.MyBookShopApp.service.senders.ValidationService;
import com.example.MyBookShopApp.service.userServices.ContactService;
import com.example.MyBookShopApp.service.userServices.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChangeUserService {
    private final UserServiceImpl userService;

    private final UserContactRepo userContactRepo;

    private final ValidationService validationService;

    private final Book2UserService book2UserService;

    private final ContactService contactService;

    @Autowired
    public ChangeUserService(UserServiceImpl userService, UserContactRepo userContactRepo, ValidationService validationService, Book2UserService book2UserService, ContactService contactService) {
        this.userService = userService;
        this.userContactRepo = userContactRepo;
        this.validationService = validationService;
        this.book2UserService = book2UserService;
        this.contactService = contactService;
    }

    public UserInfoCms findUserById(String contact) {
        JwtUser userFind = null;
        UserContactEntity userContactApprovedByContactName = contactService.findUserContactApprovedByContactName(contact);
        if (userContactApprovedByContactName != null)
            userFind = userService.covertJwtUserByUsername(userContactApprovedByContactName.getUserId().getId());
        if (userFind == null)
            throw new UsernameNotFoundException("Пользователь с таким контактом не найден.");
        UserInfoCms userInfoForProfile = new UserInfoCms(userService.getUserInfoForProfile(userFind));
        userInfoForProfile.setId(userFind.getId());
        return userInfoForProfile;
    }

    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void editBookShelf(AddBook2UserDto addBook2UserDto) throws BookException {
        JwtUser user = userService.covertJwtUserByUsername(addBook2UserDto.getUserSlug());
        if (user == null)
            throw new UsernameNotFoundException("Пользователь с таким id не найден.");
        boolean checkBook = book2UserService.getBooksUser(user, 3).stream()
                .noneMatch(x -> x.getSlug().equals(addBook2UserDto.getBookSlug()));
        if (checkBook)
            book2UserService.save(user, addBook2UserDto.getBookSlug(), 3);
        else
            throw new BookException("Книга уже добавлена к пользователю.");
    }

    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void editUserInfo(Long id, EditUserInfo userInfo) {
        User userRequest = userService.getUserById(id);
        if (userRequest == null)
            throw new UsernameNotFoundException("Пользователь с таким id не найден.");
        Pageable page = PageRequest.of(0, 1);
        if (userInfo.getPhone() != null && validationService.isPhone(userInfo.getPhone())) {
            UserContactEntity phone = userContactRepo.findUserContactEntitiesByUserIdAndCodeTime(userRequest.getId(), (short) 1, page)
                    .getContent().stream().findFirst().orElse(new UserContactEntity());
            if (!phone.getContact().equals(userInfo.getPhone())) {
                phone.setContact(userInfo.getPhone());
                userContactRepo.save(phone);
            }
        }
        if (userInfo.getEmail() != null && validationService.isEmail(userInfo.getEmail())) {
            UserContactEntity email = userContactRepo.findUserContactEntitiesByUserIdAndCodeTime(userRequest.getId(), (short) 2, page)
                    .getContent().stream().findFirst().orElse(new UserContactEntity());
            if(!email.getContact().equals(userInfo.getPhone())) {
                email.setContact(userInfo.getEmail());
                userContactRepo.save(email);
            }
        }
        if(userInfo.getName() != null){
            userRequest.setUsername(userInfo.getName());
        }
        userService.saveUser(userRequest);
    }
}
