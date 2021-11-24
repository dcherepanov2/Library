package com.example.MyBookShopApp.service;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.repo.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
@Scope("application")
public class BookService {
    private final BookRepo books;

    @Autowired
    public BookService(BookRepo books) {
        this.books = books;
    }

    public List<Book> getPopularBooksData() {
        return books.findByMostPopular();
    }

    public List<Book> getSearchQuery(String name) {
        List<Book> booksSearch;
        booksSearch = books.findByTitleContaining(name);
        return booksSearch;
    }

    public Book findBookBySelect(Integer id) {
        return books.getById(id);
    }

    public List<Book> getFilterBooksByDate(Date after, Date before) {
        return books.findByDatePublic(after,before);
    }

    public Page<Book> getPageOfRecommendedBooks(Integer offset, Integer limit){
        Pageable nextPage = PageRequest.of(offset,limit);
        return books.findAll(nextPage);
    }
}
