package com.example.MyBookShopApp.configuration.service;

import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.data.tags.Tag;
import com.example.MyBookShopApp.repo.bookrepos.BookRepo;
import com.example.MyBookShopApp.repo.tagrepos.TagRepo;
import com.example.MyBookShopApp.service.tagServices.TagService;
import com.example.MyBookShopApp.utils.BooksTestBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Collections;

public class TagServiceConfiguration {
    private TagService tagService;
    private ArrayList<Book> booksList;
    private Tag tag;

    public TagService initialize(){
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
        return tagService = new TagService(tagRepo,bookRepo);
    }

}
