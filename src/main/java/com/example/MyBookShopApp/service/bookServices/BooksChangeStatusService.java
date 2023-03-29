package com.example.MyBookShopApp.service.bookServices;

import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.data.book.links.Book2UserEntity;
import com.example.MyBookShopApp.data.user.User;
import com.example.MyBookShopApp.exception.AddArchiveException;
import com.example.MyBookShopApp.exception.BookException;
import com.example.MyBookShopApp.repo.bookrepos.Book2UserRepo;
import com.example.MyBookShopApp.security.jwt.JwtUser;
import com.example.MyBookShopApp.service.userServices.UserService;
import com.example.MyBookShopApp.service.userServices.UserServiceImpl;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.StringJoiner;

@Service
public class BooksChangeStatusService {

    private final BookService bookService;

    private final Book2UserRepo book2UserRepo;

    private final UserServiceImpl userService;

    @Autowired
    public BooksChangeStatusService(BookService bookService, Book2UserRepo book2UserRepo, UserServiceImpl userService) {
        this.bookService = bookService;
        this.book2UserRepo = book2UserRepo;
        this.userService = userService;
    }

    public void addCookieToCart(String keptContents, String cartContents, String slug, HttpServletResponse response, Model model) {
        if (keptContents != null && keptContents.contains(slug)) {
            StringBuilder stringBuilder = new StringBuilder(keptContents);
            stringBuilder.delete(keptContents.indexOf(slug), keptContents.indexOf(slug) + slug.length() + 1);
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

    public void addCookieToKept(String keptContents, String cartContents, String slug, HttpServletResponse response, Model model){
        if (cartContents != null && cartContents.contains(slug) && !cartContents.equals("")) {
            StringBuilder stringBuilder = new StringBuilder(cartContents);
            stringBuilder.delete(cartContents.indexOf(slug), cartContents.indexOf(slug) + slug.length() + 1);
            Cookie cookie = new Cookie("cartContents", stringBuilder.toString());
            cookie.setPath("/");
            response.addCookie(cookie);
            model.addAttribute("isCartEmpty", false);
        }
        if (keptContents == null || keptContents.equals("")) {
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

    public void deleteSlugCookie(String keptContents, String cartContents,String slug, HttpServletResponse response, Model model){
        if(cartContents != null && !cartContents.equals("")){
            StringBuilder stringBuilder = new StringBuilder(cartContents);
            stringBuilder.delete(stringBuilder.toString().indexOf(slug),stringBuilder.toString().indexOf(slug)+slug.length()+1);
            Cookie cookie = new Cookie("cartContents", stringBuilder.toString());
            cookie.setPath("/");
            response.addCookie(cookie);
        }
        if (keptContents != null && !keptContents.equals("")) {
            StringBuilder stringBuilder1 = new StringBuilder(keptContents);
            stringBuilder1.delete(stringBuilder1.toString().indexOf(slug), stringBuilder1.toString().indexOf(slug) + slug.length() + 1);
            Cookie cookie1 = new Cookie("keptContents", stringBuilder1.toString());
            cookie1.setPath("/");
            response.addCookie(cookie1);
        }
        model.addAttribute("isCartEmpty", false);
    }

    @SneakyThrows
    public void addBookToArchive(String keptContents, String cartContents, HttpServletResponse response, JwtUser jwtUser, String slug) {
        if (cartContents != null && !cartContents.equals("")) {
            StringBuilder stringBuilder = new StringBuilder(cartContents);
            stringBuilder.delete(stringBuilder.toString().indexOf(slug), stringBuilder.toString().indexOf(slug) + slug.length() + 1);
            Cookie cookie = new Cookie("cartContents", stringBuilder.toString());
            cookie.setPath("/");
            response.addCookie(cookie);
        }
        if (keptContents != null && !keptContents.equals("")) {
            StringBuilder stringBuilder1 = new StringBuilder(keptContents);
            stringBuilder1.delete(stringBuilder1.toString().indexOf(slug), stringBuilder1.toString().indexOf(slug) + slug.length() + 1);
            Cookie cookie1 = new Cookie("keptContents", stringBuilder1.toString());
            cookie1.setPath("/");
            response.addCookie(cookie1);
        }
        Book bookBySlug = bookService.getBookBySlug(slug);
        if (bookBySlug == null)
            throw new BookException("Книги с таким slug не найдено.");
        Book2UserEntity book2User = book2UserRepo.findBook2UserEntityByBookIdAndUserId(bookBySlug.getId(), Math.toIntExact(jwtUser.getId()))
                .stream()
                .filter(x -> x.getBookId().equals(bookBySlug.getId()))
                .findFirst().orElse(null);
        if (book2User != null && book2User.getTypeId() != 2) {
            book2User.setTypeId(2);
            book2UserRepo.save(book2User);
            return;
        }
        throw new AddArchiveException("В архив невозможно добавить книгу, которая не была куплена");
    }
}
