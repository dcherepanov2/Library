package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.dto.BookReviewLikeDto;
import com.example.MyBookShopApp.dto.CommentDtoInput;
import com.example.MyBookShopApp.dto.ResultTrue;
import com.example.MyBookShopApp.service.BookReviewService;
import com.example.MyBookShopApp.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;

@Controller
public class BookReviewController {

    private final BookService bookService;
    private final BookReviewService bookReviewService;

    @Autowired
    public BookReviewController(BookService bookService, BookReviewService bookReviewService) {
        this.bookService = bookService;
        this.bookReviewService = bookReviewService;
    }

    @PostMapping("/books/comment/{slug}")
    public String putComment(@PathVariable("slug") String slug, CommentDtoInput commentDto) {
        bookService.putComment(commentDto, slug);
        return "redirect:/books/" + slug;
    }

    @PostMapping("/books/rateBook/{slug}")
    public String rateBook(@PathVariable("slug")String slug, @RequestParam("value") Integer value, Model model){
        bookService.changeRateBookBySlug(slug, Integer.valueOf(String.valueOf(value)));
        model.addAttribute("ratingTable", bookService.getBookRateTableBySlug(slug));
        return "redirect:/books/"+slug;
    }

    @PostMapping("/rateBookReview")
    @ResponseBody
    @Consumes({MediaType.APPLICATION_JSON})
    public ResultTrue likeOrDislikeComment(BookReviewLikeDto bookReviewLikeDto){
        ResultTrue resultTrue = new ResultTrue();
        resultTrue.setResult(bookReviewService.likeOrDislikeCommentBySlug(bookReviewLikeDto));
        return resultTrue;
    }
}
