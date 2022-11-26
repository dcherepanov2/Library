package com.example.MyBookShopApp.unit.service;

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
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TagServiceTest {
    private TagService tagService;
    private ArrayList<Book> booksList;
    private Tag tag;

    @BeforeEach
    public void initialize(){
        BookRepo bookRepo = Mockito.mock(BookRepo.class);
        TagRepo tagRepo = Mockito.mock(TagRepo.class);
        booksList = new ArrayList<>();
        BooksTestBuilder booksTestBuilder = new BooksTestBuilder();
        Pageable pageable = PageRequest.of(0,6);
        for (int i = 0; i < 6; i++) {
            Book booksLocal = booksTestBuilder.createBookReview()
                    .createIdBook()
                    .createTagBook()
                    .build();
            booksList.add(booksLocal);
        }
        tag = booksList.get(0).getTags().get(0);
        Mockito.lenient().when(bookRepo.findBooksByTag(tag.getSlug(),pageable))
                .thenReturn(Collections.singletonList(booksList.get(0)));
        Mockito.lenient().when(tagRepo.findBySlug(tag.getSlug()))
                .thenReturn(tag);
        Mockito.lenient().when(tagRepo.save(tag))
                .thenReturn(tag);
        tagService = new TagService(tagRepo,bookRepo);
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
