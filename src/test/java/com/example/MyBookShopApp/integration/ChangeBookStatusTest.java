package com.example.MyBookShopApp.integration;

import com.example.MyBookShopApp.dto.BookChangeStatusDto;
import com.example.MyBookShopApp.enums.BookStatus;
import com.google.gson.Gson;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.Cookie;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application_test.properties")
public class ChangeBookStatusTest {
    private final MockMvc mockMvc;

    private final Gson gson;

    @Autowired
    public ChangeBookStatusTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
        this.gson = new Gson();
    }

    @Test
    @SneakyThrows
    public void addToKeptContent(){
        BookChangeStatusDto bookChangeStatusDto = new BookChangeStatusDto();
        bookChangeStatusDto.setStatus(BookStatus.KEPT);
        String json = gson.toJson(bookChangeStatusDto);
        mockMvc.perform(post("/books/changeBookStatus/book-kzx-286")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(cookie().exists("keptContents"));
    }

    @Test
    @SneakyThrows
    public void addToCartContent(){
        BookChangeStatusDto bookChangeStatusDto = new BookChangeStatusDto();
        bookChangeStatusDto.setStatus(BookStatus.CART);
        String json = gson.toJson(bookChangeStatusDto);
        mockMvc.perform(post("/books/changeBookStatus/book-kzx-286")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(cookie().exists("cartContents"));
    }

    @Test
    @SneakyThrows
    public void deleteBookInCookie(){
        Cookie cookie = new Cookie("cartContents", "book-kzx-286");
        BookChangeStatusDto bookChangeStatusDto = new BookChangeStatusDto();
        bookChangeStatusDto.setStatus(BookStatus.UNLINK);
        String json = gson.toJson(bookChangeStatusDto);
        mockMvc.perform(post("/books/changeBookStatus/book-kzx-286")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .cookie(cookie))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(cookie().value("cartContents",""));
    }
}
