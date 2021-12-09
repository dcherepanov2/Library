package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.dto.RecommendedBooksDto;
import com.example.MyBookShopApp.dto.SearchBookDto;
import com.example.MyBookShopApp.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/search")
public class SearchController {

    private final BookService bookService;

    @Autowired
    public SearchController(BookService bookService) {
        this.bookService = bookService;
    }

    @ModelAttribute("searchBookDto")
    public SearchBookDto searchBookDto(){
        return new SearchBookDto();
    }

    @ModelAttribute("booksBySearch")
    public List<Book> booksBySearch(){
        return new ArrayList<>();
    }

    @GetMapping({"/"})
    public String searchNulls(){
        return "/search/index";
    }

    @GetMapping({"/{name}"})
    public String searchBooks(@PathVariable(value = "name", required = false)String name, Model model){
        model.addAttribute("searchBookDto",name);
        model.addAttribute("booksBySearch",new RecommendedBooksDto(bookService.getSearchQuery(name, 0, 20)));
        return "/search/index";
    }
}
