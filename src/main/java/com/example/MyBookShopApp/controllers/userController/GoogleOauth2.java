package com.example.MyBookShopApp.controllers.userController;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationCodeGrantFilter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GoogleOauth2 {

    @GetMapping("/login")
    public String oauth2(OAuth2AuthenticationToken auth2AuthorizationCodeGrantFilter){
        System.out.println(auth2AuthorizationCodeGrantFilter);
        return "redirect:/";
    }
}
