package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.service.FaqQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/faq")
public class HelpController {

    private final FaqQuestionService faqService;

    @Autowired
    public HelpController(FaqQuestionService faqService) {
        this.faqService = faqService;
    }

    @GetMapping
    public String getHelp(Model model) {
        model.addAttribute("allQuestions", faqService.getAllQuestions());
        return "faq";
    }
    
}
