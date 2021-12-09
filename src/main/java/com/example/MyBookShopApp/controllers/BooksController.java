package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.data.book.review.BookReview;
import com.example.MyBookShopApp.data.book.review.BookReviewLikeEntity;
import com.example.MyBookShopApp.dto.BookSlugDto;
import com.example.MyBookShopApp.dto.CommentDto;
import com.example.MyBookShopApp.dto.CommentDtoInput;
import com.example.MyBookShopApp.dto.RecommendedBooksDto;
import com.example.MyBookShopApp.service.AuthorService;
import com.example.MyBookShopApp.service.BookReviewService;
import com.example.MyBookShopApp.service.BookService;
import com.example.MyBookShopApp.service.ResourceStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BookService bookService;
    private final AuthorService authorService;
    private final ResourceStorage storage;
    private final BookReviewService bookReviewService;

    @Autowired
    public BooksController(BookService bookService, AuthorService authorService, ResourceStorage storage, BookReviewService bookReviewService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.storage = storage;
        this.bookReviewService = bookReviewService;
    }

    @ModelAttribute("comment")
    public CommentDtoInput commentModel(){
        return new CommentDtoInput();
    }

    @GetMapping("/recent")
    public String getFilterBooksRecent(Model model){
        LocalDateTime ldt =
                LocalDateTime.ofInstant(new Date().toInstant(), ZoneId.systemDefault()).minusMonths(1);
        Date from = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
        ldt = LocalDateTime.ofInstant(new Date().toInstant(), ZoneId.systemDefault());
        Date to = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
        model.addAttribute("bookByFilterDatePublic",new RecommendedBooksDto(bookService.getFilterBooksByDate(from,to,0,20)));
        return "books/recent";
    }


    @GetMapping("/popular")
    public String getBooksPopular(Model model) {
        model.addAttribute("bookMostPopular",new RecommendedBooksDto(bookService.getPopularBooksData(0, 20)));
        return "books/popular";
    }

    @GetMapping("/author/{slug}")
    public String getAuthorBooks(@PathVariable("slug") String slug, Model model){
        model.addAttribute("bookBySelect",authorService.findBooksByAuthor(slug));
        model.addAttribute("bookBySelectForAuthorPage",
                new RecommendedBooksDto(authorService.findBooksByAuthor(slug)));
        return "/books/author";
    }

    @GetMapping("/{slug}")
    public String getBook(@PathVariable("slug") String slug, Model model) {
        Map<Integer , Integer> ratingTable = bookService.getBookRateTableBySlug(slug);
        model.addAttribute("ratingTable", ratingTable);
        int sum = ratingTable.get(1)+ratingTable.get(2)+ratingTable.get(3)+ratingTable.get(4)+ratingTable.get(5);
        model.addAttribute("ratingTableSum", sum);
        model.addAttribute("ratingMedium", bookReviewService.getRatingMedium(ratingTable));
        Book book = bookService.getBookBySlug(slug);
        model.addAttribute("bookBySelect", new BookSlugDto(book));
        List<BookReview> bookReviews = bookReviewService.reviewEntitiesBySlugBook(slug);
        List<CommentDto> commentDtos = new ArrayList<>();

        for(BookReview bookReview:bookReviews){ //перебор всех комментариев книги
            CommentDto commentDtoLocal = new CommentDto();
            List<BookReviewLikeEntity> reviewLikeEntities = bookReviewService
                    .getLikeOrDislikeCommentBySlug(bookReview.getId());
            for(BookReviewLikeEntity bookReviewLikeEntity: reviewLikeEntities){ //перебор всех лайков одного комментария
                short localDislike = bookReviewLikeEntity.getValue();
                short localLike = bookReviewLikeEntity.getValue();
                switch (bookReviewLikeEntity.getValue()){
                    case 1:
                        commentDtoLocal.setLike(commentDtoLocal.getLike()+localLike);
                        break;
                    case -1:
                        commentDtoLocal.setDislike(commentDtoLocal.getDislike()+Math.abs(localDislike));
                        break;
                }
            }
            if(bookReview.getText().length()>345){
                commentDtoLocal.setDescription(bookReview.getText().substring(0,345));
                commentDtoLocal.setDescriptionPartTwo(bookReview.getText().substring(345));
            }
            else {
                commentDtoLocal.setDescription(bookReview.getText());
            }
            commentDtoLocal.setSlug(bookReview.getId());
            commentDtoLocal.setPubDate(LocalDateTime.now());
            commentDtos.add(commentDtoLocal);
        }
        model.addAttribute("comments", commentDtos);
        model.addAttribute("ratingTable", bookService.getBookRateTableBySlug(slug));
        model.addAttribute("unAuthUserName","Анонимный Анонимус");
        return "/books/slug";
    }

    @GetMapping("/by-first-name-author/{name}")
    @ResponseBody
    public List<Book> booksByFirstNameAuthor(@PathVariable("name") String name) {
        return bookService.findBooksByFirstName(name);
    }

    @GetMapping("/by-title/{title}")
    @ResponseBody
    public List<Book> booksByTitle(@PathVariable("title") String title) {
        return bookService.findBooksByTitle(title);
    }

    @GetMapping("/by-max-discount/")
    @ResponseBody
    public List<Book> booksByMaxDiscount() {
        return bookService.findBooksByMaxDiscount();
    }

    @GetMapping("/is-bestseller/")
    @ResponseBody
    public List<Book> findBooksByIsBestseller() {
        return bookService.findBooksByIsBestseller();
    }

    @GetMapping("/by-price-book/")
    @ResponseBody
    public List<Book> findBooksByPriceBetween(@RequestParam("min") Integer min, @RequestParam("max") Integer max) {
        return bookService.findBooksByPriceBetween(min, max);
    }

    @GetMapping("/recommended")
    @ResponseBody
    public RecommendedBooksDto getBooksPage(@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit) {
        return new RecommendedBooksDto(bookService.getPageOfRecommendedBooks(offset, limit));
    }


    @PostMapping("/upload-file/{slug}")
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    public String uploadFile(@RequestBody MultipartFile file, @PathVariable("slug")String slug) throws IOException {
        storage.saveNewBookImage(file, slug);
        return "redirect:/books/"+slug;
    }
}
