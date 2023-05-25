package com.example.MyBookShopApp.controllers.userController;

import com.example.MyBookShopApp.data.user.User;
import com.example.MyBookShopApp.dto.CartPostponedCounterDto;
import com.example.MyBookShopApp.dto.ContactRequestDtoV2;
import com.example.MyBookShopApp.security.jwt.JwtUser;
import com.example.MyBookShopApp.service.userServices.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
public class SignInSignUpController {
    private final UserServiceImpl userService;



    @Autowired
    public SignInSignUpController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @ModelAttribute("countPostponed")
    public CartPostponedCounterDto cartPostponedCounterDto(@CookieValue(name = "cartContents", required = false) String cartContents,
                                                           @CookieValue(name = "keptContents", required = false) String keptContents) {
        CartPostponedCounterDto cartPostponedCounterDto = new CartPostponedCounterDto();
        if (cartContents != null && !cartContents.equals(""))
            cartPostponedCounterDto.setCountCart(cartContents.split("/").length);
        if (keptContents != null && !keptContents.equals(""))
            cartPostponedCounterDto.setCountPostponed(keptContents.split("/").length);
        return cartPostponedCounterDto;
    }

    @PostMapping("/signin")
    public String autorization(ContactRequestDtoV2 contact, Model model, HttpServletResponse response) {
        User user = userService.findUserByContact(contact.getContact());

        if (user != null) {
            Cookie token = userService.createToken(user);
            model.addAttribute("userInfo", userService.getUserInfoForProfile(token.getValue()));
            response.addCookie(token);
            return "redirect:/profile";
        }
        throw new UsernameNotFoundException("");
    }

    @GetMapping("/signin")
    public String getSignIn(JwtUser jwtUser) {
        if (jwtUser != null && !jwtUser.getUsername().equals("ANONYMOUS"))
            return "redirect:/profile";
        return "signin";
    }

    @GetMapping("/signup")
    public String getSignUp(){
        return "signup";
    }
}