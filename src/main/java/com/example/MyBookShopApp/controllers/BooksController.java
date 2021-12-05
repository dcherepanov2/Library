package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.dto.BookSlugDto;
import com.example.MyBookShopApp.dto.RecommendedBooksDto;
import com.example.MyBookShopApp.service.AuthorService;
import com.example.MyBookShopApp.service.BookService;
import com.example.MyBookShopApp.service.ResourceStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.StringJoiner;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BookService bookService;
    private final AuthorService authorService;
    private final ResourceStorage storage;

    @Autowired
    public BooksController(BookService bookService, AuthorService authorService, ResourceStorage storage) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.storage = storage;
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
        model.addAttribute("bookBySelect", new BookSlugDto(bookService.getBookBySlug(slug)));
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

    @PostMapping("/changeBookStatus/{slug}")
    public String handleChangeBookStatus(@PathVariable("slug")String slug, @CookieValue(name = "cartContents"
            , required = false) String cartContents, HttpServletResponse response, Model model){
        if(cartContents == null || cartContents.equals("")){
            Cookie cookie = new Cookie("cartContents", slug);
            cookie.setPath("/books");
            response.addCookie(cookie);
            model.addAttribute("isCartEmpty", false);
        }else if(!cartContents.contains(slug)) {
            StringJoiner stringJoiner = new StringJoiner("/");
            stringJoiner.add(cartContents).add(slug);
            Cookie cookie = new Cookie("cartContents", stringJoiner.toString());
            cookie.setPath("/books");
            response.addCookie(cookie);
            model.addAttribute("isCartEmpty", false);
        }
        return "redirect:/books/"+ slug;
    }

    @RequestMapping(
            value = "/upload-file/{slug}",
            method = RequestMethod.POST
    )
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    public String uploadFile(@RequestBody MultipartFile file, @PathVariable("slug")String slug) throws IOException {
        String path = storage.saveNewBookImage(file, slug);
        return "redirect:/books/"+slug;
    }
}
