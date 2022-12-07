package com.example.MyBookShopApp.controllers.booksController;

import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.data.book.review.BookReview;
import com.example.MyBookShopApp.data.book.review.BookReviewLikeEntity;
import com.example.MyBookShopApp.dto.*;
import com.example.MyBookShopApp.enums.BookStatus;
import com.example.MyBookShopApp.service.authorServices.AuthorService;
import com.example.MyBookShopApp.service.bookServices.BookReviewService;
import com.example.MyBookShopApp.service.bookServices.BookService;
import com.example.MyBookShopApp.service.bookServices.ResourceStorage;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

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
    public CommentDtoInput commentModel() {
        return new CommentDtoInput();
    }

    @GetMapping("/recent")
    public String getFilterBooksRecent(Model model) {
        LocalDateTime ldt =
                LocalDateTime.ofInstant(new Date().toInstant(), ZoneId.systemDefault()).minusMonths(1);
        Date from = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
        ldt = LocalDateTime.ofInstant(new Date().toInstant(), ZoneId.systemDefault());
        Date to = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
        model.addAttribute("bookByFilterDatePublic", new RecommendedBooksDto(bookService.getFilterBooksByDate(from, to, 0, 20)));
        return "books/recent";
    }


    @GetMapping("/popular")
    public String getBooksPopular(Model model) {
        List<Book> booksPopular = bookService.getPopularBooksData(0, 20);
        Collections.shuffle(booksPopular);
        model.addAttribute("bookMostPopular", new RecommendedBooksDto(booksPopular));
        return "books/popular";
    }

    @GetMapping("/author/{slug}")
    public String getAuthorBooks(@PathVariable("slug") String slug, Model model) {
        model.addAttribute("bookBySelect", authorService.findBooksByAuthor(slug));
        model.addAttribute("bookBySelectForAuthorPage",
                new RecommendedBooksDto(authorService.findBooksByAuthor(slug)));
        return "/books/author";
    }

    @GetMapping("/{slug}")
    public String getBook(@PathVariable("slug") String slug, Model model) {
        Map<Integer, Integer> ratingTable = bookService.getBookRateTableBySlug(slug);
        model.addAttribute("ratingTable", ratingTable);
        int sum = ratingTable.get(1) + ratingTable.get(2) + ratingTable.get(3) + ratingTable.get(4) + ratingTable.get(5);
        model.addAttribute("ratingTableSum", sum);
        model.addAttribute("ratingMedium", bookReviewService.getRatingMedium(ratingTable));
        Book book = bookService.getBookBySlug(slug);
        model.addAttribute("slugBook", new BookSlugDto(book, book.getAuthors()));
        List<BookReview> bookReviews = bookReviewService.reviewEntitiesBySlugBook(slug);
        List<CommentDto> commentDtos = new ArrayList<>();

        for (BookReview bookReview : bookReviews) { //перебор всех комментариев книги
            CommentDto commentDtoLocal = new CommentDto();
            List<BookReviewLikeEntity> reviewLikeEntities = bookReviewService
                    .getLikeOrDislikeCommentBySlug(bookReview.getId());
            for (BookReviewLikeEntity bookReviewLikeEntity : reviewLikeEntities) { //перебор всех лайков одного комментария
                short localDislike = bookReviewLikeEntity.getValue();
                short localLike = bookReviewLikeEntity.getValue();
                switch (bookReviewLikeEntity.getValue()) {
                    case 1:
                        commentDtoLocal.setLike(commentDtoLocal.getLike() + localLike);
                        break;
                    case -1:
                        commentDtoLocal.setDislike(commentDtoLocal.getDislike() + Math.abs(localDislike));
                        break;
                }
            }
            if (bookReview.getText().length() > 345) {
                commentDtoLocal.setDescription(bookReview.getText().substring(0, 345));
                commentDtoLocal.setDescriptionPartTwo(bookReview.getText().substring(345));
            } else {
                commentDtoLocal.setDescription(bookReview.getText());
            }
            commentDtoLocal.setSlug(bookReview.getId());
            commentDtoLocal.setPubDate(LocalDateTime.now());
            commentDtoLocal.setUsername(bookReview.getUserId().getUsername());
            commentDtos.add(commentDtoLocal);
        }
        model.addAttribute("comments", commentDtos);
        model.addAttribute("ratingTable", bookService.getBookRateTableBySlug(slug));
        model.addAttribute("unAuthUserName", "Анонимный Анонимус");
        return "/books/slug";
    }

//    @GetMapping("/by-first-name-author/{name}")
//    @ResponseBody
//    public List<Book> booksByFirstNameAuthor(@PathVariable("name") String name) {
//        return bookService.findBooksByFirstName(name);
//    }

    //@GetMapping("/by-title/{title}")
//    @ResponseBody
//    public List<Book> booksByTitle(@PathVariable("title") String title) {
//        return bookService.findBooksByTitle(title);
//    }

//    @GetMapping("/by-max-discount/")
//    @ResponseBody
//    public List<Book> booksByMaxDiscount() {
//        return bookService.findBooksByMaxDiscount();
//    }

//    @GetMapping("/is-bestseller/")
//    @ResponseBody
//    public List<Book> findBooksByIsBestseller() {
//        return bookService.findBooksByIsBestseller();
//    }

//    @GetMapping("/by-price-book/")
//    @ResponseBody
//    public List<Book> findBooksByPriceBetween(@RequestParam("min") Integer min, @RequestParam("max") Integer max) {
//        return bookService.findBooksByPriceBetween(min, max);
//    }
    @PostMapping("/changeBookStatus/{slug}")
    @SneakyThrows
    @ResponseBody
    public ResultTrue handleChangeBookStatus(@PathVariable("slug")String slug, @CookieValue(name = "cartContents"
            , required = false) String cartContents,  @CookieValue(name = "keptContents"
            , required = false) String keptContents, HttpServletResponse response, Model model,@RequestBody BookChangeStatusDto bookChangeStatusDto){
        if(bookChangeStatusDto.getStatus().equals(BookStatus.CART)){
            if(keptContents != null && keptContents.contains(slug)){
                StringBuilder stringBuilder = new StringBuilder(keptContents);
                stringBuilder.delete(keptContents.indexOf(slug),keptContents.indexOf(slug)+slug.length()+1);
                Cookie cookie = new Cookie("keptContents", stringBuilder.toString());
                cookie.setPath("/");
                response.addCookie(cookie);
                model.addAttribute("isCartEmpty", false);
            }
            if(cartContents == null || cartContents.equals("") ){
                Cookie cookie = new Cookie("cartContents", slug);
                cookie.setPath("/");
                response.addCookie(cookie);
                model.addAttribute("isCartEmpty", false);
            }else if(!cartContents.contains(slug)) {
                StringJoiner stringJoiner = new StringJoiner("/");
                stringJoiner.add(cartContents).add(slug);
                Cookie cookie = new Cookie("cartContents", stringJoiner.toString());
                cookie.setPath("/");
                response.addCookie(cookie);
                model.addAttribute("isCartEmpty", false);
            }
        }
        else if(bookChangeStatusDto.getStatus().equals(BookStatus.UNLINK)){
            if(cartContents != null && !cartContents.equals("")){
                StringBuilder stringBuilder = new StringBuilder(cartContents);
                stringBuilder.delete(stringBuilder.toString().indexOf(slug),stringBuilder.toString().indexOf(slug)+slug.length()+1);
                Cookie cookie = new Cookie("cartContents", stringBuilder.toString());
                cookie.setPath("/");
                response.addCookie(cookie);
            }
            if(keptContents != null && !keptContents.equals("")){
                StringBuilder stringBuilder1 = new StringBuilder(keptContents);
                stringBuilder1.delete(stringBuilder1.toString().indexOf(slug),stringBuilder1.toString().indexOf(slug)+slug.length()+1);
                Cookie cookie1 = new Cookie("keptContents", stringBuilder1.toString());
                cookie1.setPath("/");
                response.addCookie(cookie1);
            }
            model.addAttribute("isCartEmpty", false);
        }
        else if(bookChangeStatusDto.getStatus().equals(BookStatus.KEPT)){
            if(cartContents.contains(slug)){
                StringBuilder stringBuilder = new StringBuilder(keptContents);
                stringBuilder.delete(keptContents.indexOf(slug),keptContents.indexOf(slug)+slug.length()+1);
                Cookie cookie = new Cookie("cartContents", stringBuilder.toString());
                cookie.setPath("/");
                response.addCookie(cookie);
                model.addAttribute("isCartEmpty", false);
            }
            if(keptContents == null || keptContents.equals("")){
                Cookie cookie = new Cookie("keptContents", slug);
                cookie.setPath("/");
                response.addCookie(cookie);
                model.addAttribute("isCartEmpty", false);
            }else if(!keptContents.contains(slug)) {
                StringJoiner stringJoiner = new StringJoiner("/");
                stringJoiner.add(keptContents).add(slug);
                Cookie cookie = new Cookie("keptContents", stringJoiner.toString());
                cookie.setPath("/");
                response.addCookie(cookie);
                model.addAttribute("isCartEmpty", false);
            }
        }
        ResultTrue resultTrue = new ResultTrue();
        resultTrue.setResult(true);
        return resultTrue;
    }


    @GetMapping("/recommended")
    @ResponseBody
    public RecommendedBooksDto getBooksPage(@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit) {
        List<Book> booksPopular = bookService.getPopularBooksData(offset, limit);
        Collections.shuffle(booksPopular);
        return new RecommendedBooksDto(bookService.getPageOfRecommendedBooks(offset, limit));
    }


    @PostMapping("/{slug}/img/save")
    @Consumes({javax.ws.rs.core.MediaType.MULTIPART_FORM_DATA})
    public String uploadFile(@RequestBody MultipartFile file, @PathVariable("slug") String slug) throws IOException {
        storage.saveNewBookImage(file, slug);
        return "redirect:/books/" + slug;
    }
}
