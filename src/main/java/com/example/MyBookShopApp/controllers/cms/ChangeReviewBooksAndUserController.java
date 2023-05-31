package com.example.MyBookShopApp.controllers.cms;

import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.data.book.review.BookReview;
import com.example.MyBookShopApp.dto.CommentDtoInput;
import com.example.MyBookShopApp.dto.ReviewEditListDto;
import com.example.MyBookShopApp.dto.ReviewListDto;
import com.example.MyBookShopApp.enums.ErrorMessageResponse;
import com.example.MyBookShopApp.exception.BookException;
import com.example.MyBookShopApp.exception.BookReviewException;
import com.example.MyBookShopApp.service.bookServices.BookReviewService;
import com.example.MyBookShopApp.service.bookServices.BookService;
import com.example.MyBookShopApp.service.cms.ChangeReviewBooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/cms/book-review")
@Validated
public class ChangeReviewBooksAndUserController {

    private final ChangeReviewBooksService changeReviewBooksService;

    private final BookReviewService bookReviewService;

    private final BookService bookService;

    @Autowired
    public ChangeReviewBooksAndUserController(@Valid ChangeReviewBooksService changeReviewBooksService, BookReviewService bookReviewService, BookService bookService) {
        this.changeReviewBooksService = changeReviewBooksService;
        this.bookReviewService = bookReviewService;
        this.bookService = bookService;
    }

    @GetMapping("/find-review-by-title-book/{title}")
    public ReviewListDto getReviewList(@PathVariable("title") String title) throws BookException {
        Book bookByTitle = bookService.findBookByTitle(title);
        if(bookByTitle == null)
            throw new BookException(ErrorMessageResponse.NOT_FOUND_BOOK.getName());
        return new ReviewListDto(bookByTitle);
    }

    @PostMapping("/edit/{id}")
    public void editReviewBook(@Valid CommentDtoInput commentDtoInput, @PathVariable("id") Integer id) throws BookReviewException {
        BookReview bySlug = bookReviewService.getBySlug(id);
        if (bySlug == null)
            throw new BookReviewException(ErrorMessageResponse.NOT_FOUND_BOOK.getName());
        changeReviewBooksService.editBookReview(bySlug, commentDtoInput);
    }

    @PostMapping("/editListComment/{slug}")
    public void editReviewListBook(@RequestBody @Valid ReviewEditListDto reviews, @PathVariable("slug") String slug) throws BookException {
        Book bookBySlug = bookService.getBookBySlug(slug);
        if (bookBySlug == null)
            throw new BookException(ErrorMessageResponse.NOT_FOUND_BOOK.getName());
        changeReviewBooksService.editListBook(reviews, bookBySlug);
    }
}
