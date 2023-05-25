package com.example.MyBookShopApp.controllers.otherController;

import com.example.MyBookShopApp.dto.CartPostponedCounterDto;
import com.example.MyBookShopApp.service.otherServices.FaqQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/faq")
public class HelpController {

    private final FaqQuestionService faqService;

    @Autowired
    public HelpController(FaqQuestionService faqService) {
        this.faqService = faqService;
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
    public String getHelp(Model model) {
        model.addAttribute("allQuestions", faqService.getAllQuestions());
        return "faq";
    }

}
