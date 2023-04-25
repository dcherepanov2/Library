package com.example.MyBookShopApp.service.bookServices;

import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.data.book.links.Book2UserEntity;
import com.example.MyBookShopApp.exception.AddArchiveException;
import com.example.MyBookShopApp.exception.BookException;
import com.example.MyBookShopApp.repo.bookrepos.Book2UserRepo;
import com.example.MyBookShopApp.security.jwt.JwtUser;
import com.example.MyBookShopApp.service.userServices.UserServiceImpl;
import com.example.MyBookShopApp.utils.CookieUtils;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.StringJoiner;

@Service
public class BooksChangeStatusService {

    private final BookService bookService;

    private final Book2UserRepo book2UserRepo;

    private final Book2UserService book2UserService;

    private final UserServiceImpl userService;

    @Autowired
    public BooksChangeStatusService(BookService bookService, Book2UserRepo book2UserRepo, Book2UserService book2UserService, UserServiceImpl userService) {
        this.bookService = bookService;
        this.book2UserRepo = book2UserRepo;
        this.book2UserService = book2UserService;
        this.userService = userService;
    }

    @Deprecated
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
        } else if (!cartContents.contains(slug)) {
            StringJoiner stringJoiner = new StringJoiner("/");
            stringJoiner.add(cartContents).add(slug);
            Cookie cookie = new Cookie("cartContents", stringJoiner.toString());
            cookie.setPath("/");
            response.addCookie(cookie);
            model.addAttribute("isCartEmpty", false);
        }
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ,
            rollbackFor = {Exception.class, RuntimeException.class})
    public void addBook2Cart(JwtUser jwtUser, String slug) {
        book2UserService.save(jwtUser, slug, 3);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ,
            rollbackFor = {Exception.class, RuntimeException.class})
    public void addBook2Kept(JwtUser jwtUser, String slug) {
        book2UserService.save(jwtUser, slug, 4);
    }

    @Deprecated
    public void addCookieToKept(String keptContents, String cartContents, String slug, HttpServletResponse response, Model model) {
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
        } else if (!keptContents.contains(slug)) {
            StringJoiner stringJoiner = new StringJoiner("/");
            stringJoiner.add(keptContents).add(slug);
            Cookie cookie = new Cookie("keptContents", stringJoiner.toString());
            cookie.setPath("/");
            response.addCookie(cookie);
            model.addAttribute("isCartEmpty", false);
        }
    }

    @Deprecated
    public void deleteSlugCookie(HttpServletRequest request, String slug, HttpServletResponse response, Model model) {
        Cookie cartContents = CookieUtils.deleteSlugFromCookies(request, slug, "cartContents");
        Cookie keptContents = CookieUtils.deleteSlugFromCookies(request, slug, "keptContents");
        if (cartContents != null)
            response.addCookie(cartContents);
        if (keptContents != null)
            response.addCookie(keptContents);
        model.addAttribute("isCartEmpty", false);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ,
            rollbackFor = {Exception.class, RuntimeException.class})
    public void deleteBook2UserBySlug(JwtUser jwtUser, String slug) throws Exception {
        Book book = bookService.getBookBySlug(slug);
        if (book == null)
            throw new BookException("Книга не найдена.");
        List<Book2UserEntity> book2UserEntityByBookIdAndUserId = book2UserRepo.findBook2UserEntityByBookIdAndUserIdAndTypeIdEquals3And4(book.getId(), Math.toIntExact(jwtUser.getId()));
        book2UserRepo.deleteAll(book2UserEntityByBookIdAndUserId);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ,
            rollbackFor = {Exception.class, RuntimeException.class})
    @SneakyThrows
    public void addBookToArchive(HttpServletRequest request,
                                 HttpServletResponse response,
                                 JwtUser jwtUser,
                                 String slug) {
        Book bookBySlug = bookService.getBookBySlug(slug);
        if (bookBySlug == null)
            throw new BookException("Книги с таким slug не найдено.");
        Book2UserEntity book2User = book2UserRepo.findBook2UserEntityByBookIdAndUserId(bookBySlug.getId(), Math.toIntExact(jwtUser.getId()))
                .stream()
                .filter(x -> x.getBookId().equals(bookBySlug.getId()) && !(x.getTypeId().equals(3) || x.getTypeId().equals(4) || x.getTypeId().equals(5)))
                .findFirst().orElse(null);
        if (book2User != null) {
            book2User.setTypeId(2);
            book2UserRepo.save(book2User);
            Cookie cartContents = CookieUtils.deleteSlugFromCookies(request, slug, "cartContents");
            Cookie keptContents = CookieUtils.deleteSlugFromCookies(request, slug, "keptContents");
            if (cartContents != null)
                response.addCookie(cartContents);
            if (keptContents != null)
                response.addCookie(keptContents);
            return;
        }

        throw new AddArchiveException("В архив невозможно добавить книгу, которая не была куплена");
    }
}
