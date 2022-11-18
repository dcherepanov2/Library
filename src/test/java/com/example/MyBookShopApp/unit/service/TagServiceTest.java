package com.example.MyBookShopApp.unit.service;

import com.example.MyBookShopApp.configuration.MockitoApplicationContext;
import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.data.tags.Tag;
import com.example.MyBookShopApp.repo.bookrepos.BookRepo;
import com.example.MyBookShopApp.repo.tagrepos.TagRepo;
import com.example.MyBookShopApp.service.tagServices.TagService;
import com.example.MyBookShopApp.utils.BooksTestBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TagServiceTest {
    @Spy
    private MockitoApplicationContext mockitoApplicationContext;
    private TagService tagService;
    private ArrayList<Book> booksList;
    private Tag tag;

    @BeforeEach
    public void initialize(){
        tagService = (TagService) mockitoApplicationContext.getBean(TagService.class);
        booksList = (ArrayList<Book>) mockitoApplicationContext.getFieldTestClassByName("booksList", TagService.class);
        tag = (Tag) mockitoApplicationContext.getFieldTestClassByName("tag", TagService.class);
    }

    @Test
    public void findBooksBySlug(){
        List<Book> booksBySlug = tagService.findBooksBySlug(tag.getSlug(), 0, 6);
        assertEquals(booksBySlug,Collections.singletonList(booksList.get(0)));
    }

    @Test
    public void findTagBySlug(){
        Tag tagBySlug = tagService.findTagBySlug(tag.getSlug());
        assertEquals(tagBySlug, tag);
    }
}
