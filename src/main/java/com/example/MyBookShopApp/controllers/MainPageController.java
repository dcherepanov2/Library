package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.tags.Tag;
import com.example.MyBookShopApp.dto.RecommendedBooksDto;
import com.example.MyBookShopApp.dto.SearchBookDto;
import com.example.MyBookShopApp.service.BookService;
import com.example.MyBookShopApp.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Controller
public class MainPageController {

    private final BookService bookService;
    private final TagService tagService;

    @Autowired
    public MainPageController(BookService bookService, TagService tagService) {
        this.bookService = bookService;
        this.tagService = tagService;
    }

    @ModelAttribute("searchBookDto")
    public SearchBookDto searchBookDto(){
        return new SearchBookDto();
    }

    @ModelAttribute("allTags")
    public List<Tag> allTags(){
        return tagService.findAll();
    }

    @ModelAttribute("recommendedBooks")
    public RecommendedBooksDto recommendedBook(){
        return new RecommendedBooksDto(bookService.getPageOfRecommendedBooks(0,6).getContent());
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
    public String mainPage(){
        return "index";
    }

//    @GetMapping("/books/recommended")
//    @ResponseBody
//    public RecommendedBooksDto recommendedBookPage(
//            @RequestParam("offset") Integer offset,
//            @RequestParam("limit") Integer limit
//    ){
//        return new RecommendedBooksDto(bookService.getPageOfRecommendedBooks(offset,limit));
//    }
}
