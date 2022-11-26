package com.example.MyBookShopApp.integration;

import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.repo.bookrepos.BookRepo;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.ws.rs.core.MediaType;
import java.io.File;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application_test.properties")
public class BookApiController {
    private final MockMvc mockMvc;
    private final BookRepo bookRepo;

    @Autowired
    public BookApiController(MockMvc mockMvc, BookRepo bookRepo) {
        this.mockMvc = mockMvc;
        this.bookRepo = bookRepo;
    }

    @Test
    @SneakyThrows
    public void getFilterBooksRecent(){
        List<Book> all = bookRepo.findAll();
        LocalDateTime from = LocalDateTime.of(2011, Month.AUGUST, 11, 0 ,0);
        LocalDateTime to = LocalDateTime.of(2011, Month.NOVEMBER, 11, 0 ,0);
        MvcResult mvcResult = mockMvc.perform(get("/api/books/recent?" + "to=" + DateTimeFormatter.ofPattern("dd.MM.yyyy").format(to) +
                                            "&from=" + DateTimeFormatter.ofPattern("dd.MM.yyyy").format(from) + "&offset=0&limit=6"))
                                     .andExpect(status().isOk())
                                     .andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        all.forEach(x -> assertTrue(content.contains(x.getSlug())));
    }

    @Test
    @SneakyThrows
    public void getAllBooksByTag(){
        List<Book> all = bookRepo.findAll();
        MvcResult mvcResult = mockMvc.perform(get("/api/books/tag/tag-121?offset=0&limit=6"))
                .andExpect(status().isOk())
                .andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        all.forEach(x -> assertTrue(content.contains(x.getSlug())));
    }

    @Test
    @SneakyThrows
    public void downloadFileTypeEpub(){
        List<Book> all = bookRepo.findAll();
        File file = new File("src/main/resources/uploads/bookfiles/file.epub");
        MvcResult mvcResult = mockMvc.perform(get("/api/books/download/"+all.get(0).getSlug()+"?type="+"1")
                                              .contentType(MediaType.APPLICATION_OCTET_STREAM))
                                     .andExpect(status().isOk())
                                     .andReturn();
        assertEquals(file.length(), mvcResult.getResponse().getContentAsByteArray().length);
    }

    @Test
    @SneakyThrows
    public void downloadFileTypeFb2(){
        List<Book> all = bookRepo.findAll();
        File file = new File("src/main/resources/uploads/bookfiles/file.fb2");
        MvcResult mvcResult = mockMvc.perform(get("/api/books/download/"+all.get(0).getSlug()+"?type="+"2")
                        .contentType(MediaType.APPLICATION_OCTET_STREAM))
                .andExpect(status().isOk())
                .andReturn();
        assertEquals(file.length(), mvcResult.getResponse().getContentAsByteArray().length);
    }

    @SneakyThrows
    @Test
    public void downloadFileTypePdf(){
        List<Book> all = bookRepo.findAll();
        File file = new File("src/main/resources/uploads/bookfiles/file.pdf");
        MvcResult mvcResult = mockMvc.perform(get("/api/books/download/"+all.get(0).getSlug()+"?type="+"3")
                        .contentType(MediaType.APPLICATION_OCTET_STREAM))
                .andExpect(status().isOk())
                .andReturn();
        assertEquals(file.length(), mvcResult.getResponse().getContentAsByteArray().length);
    }
}
