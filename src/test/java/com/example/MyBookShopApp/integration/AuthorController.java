package com.example.MyBookShopApp.integration;

import com.example.MyBookShopApp.data.author.Author;
import com.example.MyBookShopApp.service.authorServices.AuthorService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application_test.properties")
public class AuthorController {
    private final MockMvc mockMvc;
    private final AuthorService authorService;

    @Autowired
    public AuthorController(MockMvc mockMvc, AuthorService authorService) {
        this.mockMvc = mockMvc;
        this.authorService = authorService;
    }

    @Test
    @SneakyThrows
    public void getAuthorsPage(){
        Map<Character, List<Author>> mapAuthorToFirstLetter = authorService.getSortAuthor();
        Author author = mapAuthorToFirstLetter.get("n".charAt(0)).get(0);
        mockMvc.perform(get("/authors"))
               .andDo(print())
               .andExpect(content().string(containsString(author.getName())))
               .andExpect(content().string(containsString("/authors/"+author.getSlug())))
               .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    public void getAuthor(){
        Map<Character, List<Author>> mapAuthorToFirstLetter = authorService.getSortAuthor();
        Author author = mapAuthorToFirstLetter.get("n".charAt(0)).get(0);
        mockMvc.perform(get("/authors/"+author.getSlug()))
                .andDo(print())
                .andExpect(content().string(containsString(author.getName())))
                .andExpect(content().string(containsString(author.getDescription())))
                .andExpect(content().string(containsString(author.getPhoto())))
                .andExpect(content().string(containsString(author.getSlug())))
                .andExpect(status().isOk());
    }
}
