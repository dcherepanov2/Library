package com.example.MyBookShopApp.controllers.booksController;

import com.example.MyBookShopApp.dto.BookReviewLikeDto;
import com.example.MyBookShopApp.dto.CommentDtoInput;
import com.example.MyBookShopApp.dto.RateBookDto;
import com.example.MyBookShopApp.dto.ResultTrue;
import com.example.MyBookShopApp.security.jwt.JwtUser;
import com.example.MyBookShopApp.service.bookServices.BookReviewService;
import com.example.MyBookShopApp.service.bookServices.BookService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import java.security.Principal;

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
    @SneakyThrows
    public String putComment(@PathVariable("slug") String slug, @RequestBody CommentDtoInput commentDto, JwtUser jwtUser){
        bookService.putComment(jwtUser,commentDto, slug);
        return "redirect:/books/" + slug;
    }

    @PostMapping("/rateBook")
    @ResponseBody
    @Consumes({MediaType.APPLICATION_JSON})
    public ResultTrue rateBook(@RequestBody RateBookDto rateBookDto, Model model, @CookieValue(name = "token") String token){
        bookService.changeRateBookBySlug(token,rateBookDto.getBookid(), rateBookDto.getValue());
        model.addAttribute("ratingTable", bookService.getBookRateTableBySlug(rateBookDto.getBookid()));
        ResultTrue resultTrue = new ResultTrue();
        resultTrue.setResult(true);
        return resultTrue;
    }

    @PostMapping("/rateBookReview")
    @ResponseBody
    @Consumes({MediaType.APPLICATION_JSON})
    public ResultTrue likeOrDislikeComment(@RequestBody BookReviewLikeDto bookReviewLikeDto,@CookieValue(name = "token") String token){
        ResultTrue resultTrue = new ResultTrue();
        resultTrue.setResult(bookReviewService.likeOrDislikeCommentBySlug(token,bookReviewLikeDto));
        return resultTrue;
    }
}
