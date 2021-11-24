package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.dto.RecommendedBooksDto;
import com.example.MyBookShopApp.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Controller
public class MainPageController {

    private final BookService bookService;

    @Autowired
    public MainPageController(BookService bookService) {
        this.bookService = bookService;
    }

    @ModelAttribute("recommendedBooks")
    public List<Book> recommended(){
        return bookService.getPageOfRecommendedBooks(0,6).getContent();
    }

    @ModelAttribute("popularBooks")
    public List<Book> popularBooks(){
        return bookService.getPopularBooksData();
    }

    @ModelAttribute("newBooks")
    public List<Book> newBooks() throws ParseException {

        SimpleDateFormat fromUser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS");
        SimpleDateFormat myFormat = new SimpleDateFormat("dd-MM-yyyy");
        LocalDateTime beforeDate = LocalDateTime.now().minusMonths(1);
        LocalDateTime afterDate = beforeDate.plusMonths(1);
        Date reformattedAfterDate = myFormat.parse(myFormat.format(fromUser.parse(afterDate.toString())));
        Date reformattedBeforeDate = myFormat.parse(myFormat.format(fromUser.parse(beforeDate.toString())));
        return bookService.getFilterBooksByDate(reformattedAfterDate,reformattedBeforeDate);
    }

    @GetMapping("/")
    public String mainPage(){
        return "index";
    }

    @GetMapping("/books/recommended")
    @ResponseBody
    public RecommendedBooksDto recommendedBookPage(
            @RequestParam("offset") Integer offset,
            @RequestParam("limit") Integer limit
    ){
        return new RecommendedBooksDto(bookService.getPageOfRecommendedBooks(offset,limit).getContent());
    }
}
