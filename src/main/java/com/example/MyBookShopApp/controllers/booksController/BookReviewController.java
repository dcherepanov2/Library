package com.example.MyBookShopApp.controllers.booksController;

import com.example.MyBookShopApp.dto.*;
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

@Controller
public class BookReviewController {

    private final BookService bookService;
    private final BookReviewService bookReviewService;

    @Autowired
    public BookReviewController(BookService bookService, BookReviewService bookReviewService) {
        this.bookService = bookService;
        this.bookReviewService = bookReviewService;
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

    @PostMapping("/books/comment/{slug}")
    @SneakyThrows
    public String putComment(@PathVariable("slug") String slug, CommentDtoInput commentDto, JwtUser jwtUser) {
        bookService.putComment(jwtUser, commentDto, slug);
        return "redirect:/books/" + slug;
    }

    @PostMapping("/rateBook")
    @ResponseBody
    @Consumes({MediaType.APPLICATION_JSON})
    public ResultTrue rateBook(@RequestBody RateBookDto rateBookDto, Model model, JwtUser user) {
        bookService.changeRateBookBySlug(user, rateBookDto.getBookid(), rateBookDto.getValue());
        model.addAttribute("ratingTable", bookService.getBookRateTableBySlug(rateBookDto.getBookid()));
        ResultTrue resultTrue = new ResultTrue();
        resultTrue.setResult(true);
        return resultTrue;
    }

    @PostMapping("/rateBookReview")
    @ResponseBody
    @Consumes({MediaType.APPLICATION_JSON})
    public ResultTrue likeOrDislikeComment(@RequestBody BookReviewLikeDto bookReviewLikeDto, JwtUser jwtUser) {
        ResultTrue resultTrue = new ResultTrue();
        resultTrue.setResult(bookReviewService.likeOrDislikeCommentBySlug(jwtUser, bookReviewLikeDto));
        return resultTrue;
    }
}
