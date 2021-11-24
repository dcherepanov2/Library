package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

@Controller
@RequestMapping("/books")
public class BooksController {

    @Autowired
    private final BookService bookService;


    public BooksController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/recent")
    public String getBooksRecent(Model model) throws ParseException {
        model.addAttribute("errorValidateDateFilter","");

        //парсинг из одного формата даты в другой
        SimpleDateFormat fromUser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS");
        SimpleDateFormat myFormat = new SimpleDateFormat("dd-MM-yyyy");
        LocalDateTime beforeDate = LocalDateTime.now().minusMonths(1);
        LocalDateTime afterDate = beforeDate.plusMonths(1);
        Date reformattedAfterDate = myFormat.parse(myFormat.format(fromUser.parse(afterDate.toString())));
        Date reformattedBeforeDate = myFormat.parse(myFormat.format(fromUser.parse(beforeDate.toString())));
        model.addAttribute("bookByFilterDatePublic",bookService.getFilterBooksByDate(reformattedAfterDate,reformattedBeforeDate));

        return "books/recent";
    }

    @GetMapping("/recent/filter")
    public String getFilterBooksRecent(
            Model model,
            @RequestParam("afterDate") String after,
            @RequestParam("beforeDate") String before
    )
    {
        Date afterDate;
        Date beforeDate;
        model.addAttribute("errorValidateDateFilter","");
        after = after.replace(".","-");
        before = before.replace(".","-");
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        try {
            afterDate = formatter.parse(after);
            beforeDate = formatter.parse(before);
        }catch (ParseException e){
            model.addAttribute("errorValidateDateFilter","Некорректно указана дата фильтрации");
            return "books/recent";
        }
        model.addAttribute("bookByFilterDatePublic",bookService.getFilterBooksByDate(afterDate,beforeDate));
        return "books/recent";
    }


    @GetMapping("/popular")
    public String getBooksPopular(Model model){
        model.addAttribute("bookMostPopular",bookService.getPopularBooksData());
        return "books/popular";
    }

    @GetMapping("/author/{id}")
    public String getAuthorBooks(@PathVariable("id") Integer id, Model model){
        model.addAttribute("bookBySelect",bookService.getPopularBooksData());
        return "/books/author";
    }

    @GetMapping("/{id}")
    public String getBook(@PathVariable("id") Integer id, Model model){
        model.addAttribute("bookBySelect",bookService.findBookBySelect(id));
        return "/books/slug";
    }

}
