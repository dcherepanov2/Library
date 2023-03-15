package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.tags.Tag;
import com.example.MyBookShopApp.dto.RecommendedBooksDto;
import com.example.MyBookShopApp.dto.SearchBookDto;
import com.example.MyBookShopApp.service.bookServices.BookService;
import com.example.MyBookShopApp.service.tagServices.TagService;
import com.example.MyBookShopApp.service.userServices.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Controller
public class MainPageController {

    private final BookService bookService;
    private final TagService tagService;

    private final ProfileService profileService;


    @Autowired
    public MainPageController(BookService bookService, TagService tagService, ProfileService profileService) {
        this.bookService = bookService;
        this.tagService = tagService;
        this.profileService = profileService;
    }

    @ModelAttribute("searchBookDto")
    public SearchBookDto searchBookDto(){
        return new SearchBookDto();
    }

    @ModelAttribute("allTags")
    public List<Tag> allTags() {
        List<Tag> list = new ArrayList<>(tagService.findAll());
        Collections.shuffle(list);
        return list;
    }

    @ModelAttribute("recommendedBooks")
    public RecommendedBooksDto recommendedBook(){
        return new RecommendedBooksDto(bookService.getPageOfRecommendedBooks(0,6));
    }

    @ModelAttribute("popularBooks")
    public RecommendedBooksDto popularBooks(){
        return new RecommendedBooksDto(bookService.getPopularBooksData(0,6));
    }


    @ModelAttribute("newBooks")
    public RecommendedBooksDto newBooks() {
        LocalDateTime ldt =
                LocalDateTime.ofInstant(new Date().toInstant(), ZoneId.systemDefault()).minusMonths(1);
        Date from = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
        ldt = LocalDateTime.ofInstant(new Date().toInstant(), ZoneId.systemDefault());
        Date to = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
        return new RecommendedBooksDto(bookService.getFilterBooksByDate(from,to,0,6 ));
    }
    @GetMapping("/")
    public String mainPage(@CookieValue(name = "token", required = false) String token, Model model)
    {
        if(token != null)
            model.addAttribute("infoUserForMainPage",profileService.makeProfileResponseToMainPage(token));
        else
            model.addAttribute("infoUserForMainPage",null);
        return "index";
    }
}
