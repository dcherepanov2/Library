package com.example.MyBookShopApp.controllers.otherController;

import com.example.MyBookShopApp.dto.CartPostponedCounterDto;
import com.example.MyBookShopApp.dto.ContactMessageDto;
import com.example.MyBookShopApp.dto.ResultTrue;
import com.example.MyBookShopApp.security.jwt.JwtUser;
import com.example.MyBookShopApp.service.otherServices.ContactsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/contacts")
public class ContactsController {

    private final ContactsService contactsService;

    @Autowired
    public ContactsController(ContactsService contactsService) {
        this.contactsService = contactsService;
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

    @GetMapping
    public String getContacts() {
        return "contacts";
    }

    @PostMapping("/sendMessage")
    @ResponseBody
    public ResultTrue sendMessage(@RequestBody ContactMessageDto contactMessageDto, JwtUser jwtUser) {
        contactsService.saveMessage(contactMessageDto, jwtUser);
        ResultTrue resultTrue = new ResultTrue();
        resultTrue.setResult(true);
        return resultTrue;
    }
}
