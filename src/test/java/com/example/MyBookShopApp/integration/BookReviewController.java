package com.example.MyBookShopApp.integration;

import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.data.user.Role;
import com.example.MyBookShopApp.data.user.User;
import com.example.MyBookShopApp.dto.BookReviewLikeDto;
import com.example.MyBookShopApp.dto.CommentDtoInput;
import com.example.MyBookShopApp.dto.RateBookDto;
import com.example.MyBookShopApp.repo.bookrepos.BookRepo;
import com.example.MyBookShopApp.security.jwt.JwtTokenProvider;
import com.example.MyBookShopApp.service.bookServices.BookReviewService;
import com.example.MyBookShopApp.service.userServices.UserServiceImpl;
import com.google.gson.Gson;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application_test.properties")
public class BookReviewController {

    private final MockMvc mockMvc;

    private final BookReviewService bookReviewService;

    private final BookRepo bookRepo;

    private final Gson gson;

    private String bearerToken;


    private final UserServiceImpl userService;
    private final JwtTokenProvider jwtTokenProvider;
    private Book bookBySlug;
    private User byUsername;

    @Autowired
    public BookReviewController(MockMvc mockMvc, BookReviewService bookReviewService, BookRepo bookRepo, UserServiceImpl userService, JwtTokenProvider jwtTokenProvider) {
        this.mockMvc = mockMvc;
        this.bookReviewService = bookReviewService;
        this.bookRepo = bookRepo;
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
        gson = new Gson();
    }

    @BeforeEach
    public void findBook(){
        bookBySlug = bookRepo.findBookBySlug("book-kzx-285");
        if(bookBySlug == null)
            throw new AssertionFailedError("Не найдена книга");
    }

    @BeforeEach
    public void login(){
        byUsername = userService.findByUsername("danilcherep7@gmail.com");
        if(byUsername == null)
            throw new AssertionFailedError("Не найден пользователь");
        Role role = new Role("USER_CLIENT");
        bearerToken ="Bearer_" + jwtTokenProvider.createToken(byUsername.getUsername(), Collections.singletonList(role));
    }

    @Test
    @SneakyThrows
    public void putComment(){
        CommentDtoInput commentDtoInput = new CommentDtoInput();
        commentDtoInput.setDescription("Какой-то текст");
        commentDtoInput.setSlug("Какой-то slug");
        String json = gson.toJson(commentDtoInput);
        mockMvc.perform(post("/books/comment/"+ bookBySlug.getSlug())
                               .contentType(MediaType.APPLICATION_JSON)
                               .accept(MediaType.APPLICATION_JSON)
                               .content(json)
                               .header("Authorization",bearerToken))
               .andDo(print())
               .andExpect(status().is3xxRedirection());
        mockMvc.perform(get("/books/"+ bookBySlug.getSlug())
                           .header("Authorization",bearerToken))// проверяем, что коммент ушел на страничку книг //TODO: не понятен момент почему без токена вылетает 401, хотя все гет запросы permitAll() - спросить у куратора
                .andExpect(content().string(containsString(commentDtoInput.getDescription())))
                .andExpect(content().string(containsString("danilcherep7@gmail.com")))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    public void rateBook(){
        RateBookDto rateBookDto = new RateBookDto();
        rateBookDto.setBookid(bookBySlug.getSlug());
        rateBookDto.setValue(2);
        String json = gson.toJson(rateBookDto);
        mockMvc.perform(post("/rateBook/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(json)
                        .header("Authorization",bearerToken))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @SneakyThrows
    public void rateBookReview(){
        BookReviewLikeDto bookReviewLikeDto = new BookReviewLikeDto();
        bookReviewLikeDto.setReviewid(Math.toIntExact(byUsername.getId()));
        bookReviewLikeDto.setValue((short) 1);
        String json = gson.toJson(bookReviewLikeDto);
        mockMvc.perform(post("/rateBookReview")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(json)
                        .header("Authorization",bearerToken))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }
}
