package com.example.MyBookShopApp.unit.service;

import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.data.book.links.BookRating;
import com.example.MyBookShopApp.data.book.review.BookReview;
import com.example.MyBookShopApp.dto.CommentDtoInput;
import com.example.MyBookShopApp.repo.bookrepos.BookRatingRepo;
import com.example.MyBookShopApp.repo.bookrepos.BookRepo;
import com.example.MyBookShopApp.repo.bookrepos.BookReviewRepo;
import com.example.MyBookShopApp.repo.userrepos.UserRepo;
import com.example.MyBookShopApp.service.bookServices.BookReviewService;
import com.example.MyBookShopApp.service.bookServices.BookService;
import com.example.MyBookShopApp.utils.BookRatingTestBuilder;
import com.example.MyBookShopApp.utils.BooksTestBuilder;
import com.example.MyBookShopApp.utils.PrincipalImplTest;
import com.example.MyBookShopApp.utils.UserTestBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class BookServiceTest {
    @MockBean
    private BookRepo books;

    @MockBean
    private BookRatingRepo bookRating;

    @MockBean
    private BookReviewRepo bookReviewRepo;

    @MockBean
    private BookReviewService bookReviewService;

    @MockBean
    private UserRepo userRepo;

    private BookService bookService;
    private ArrayList<Book> booksList;

    private final Date date = new Date();

    private final UserTestBuilder userTestBuilder = new UserTestBuilder();


    @BeforeEach
    public void init() {
        BookRating bookRating1 = new BookRatingTestBuilder().createBookRating()
                                                            .createBookRatingTestValue()
                                                            .build();
        Pageable pageable = PageRequest.of(0, 6);
        booksList = new ArrayList<>();
        BooksTestBuilder booksTestBuilder = new BooksTestBuilder();

        for (int i = 0; i < 6; i++) {
            Book booksLocal = booksTestBuilder.createBookReview()
                                              .createIdBook()
                                              .build();
            booksLocal.setDatePublic(date);
            booksList.add(booksLocal);
        }
        PageImpl<Book> page = new PageImpl<>(booksList, pageable, 6);
        Mockito.when(books.findByMostPopular(pageable)).thenReturn(booksList);
        Mockito.when(books.findByTitleContaining(booksList.get(0).getTitle(),pageable))
               .thenReturn(page);
        Mockito.when(books.findBooksByDatePublicBetween(date, date, pageable))
               .thenReturn(page);
        Mockito.when(books.findBookBySlug(booksList.get(0).getSlug()))
                .thenReturn(booksList.get(0));
        Mockito.when(books.findBooksByTag(booksList.get(0).getSlug(),pageable))
                .thenReturn(booksList);//
        Mockito.when(bookRating.getBookRatingBySlug(booksList.get(0).getSlug()))
                .thenReturn(Collections.singletonList(bookRating1));
        Mockito.when(userRepo.findByUsername("test"))
                .thenReturn(userTestBuilder.build());
        Mockito.when(bookRating.save(bookRating1))
                .thenReturn(bookRating1);
        Mockito.when(bookReviewService.reviewEntitiesBySlugBook(booksList.get(0).getSlug()))
                .thenReturn(booksList.get(0).getBooksReview());
        bookService = new BookService(books, bookRating, bookReviewRepo, bookReviewService, userRepo);
    }

    @Test
    public void getPopularBooksData() {
        List<Book> popularBooksData = bookService.getPopularBooksData(0, 6);
        assertEquals(popularBooksData.size(), 6);
    }

    @Test
    public void getSearchQuery() {
        List<Book> searchQuery = bookService.getSearchQuery(booksList.get(0).getTitle(), 0, 6);
        assertEquals(searchQuery, booksList);
    }

    @Test
    public void getFilterBooksByDate() {
        List<Book> searchQuery = bookService.getFilterBooksByDate(date,date,0,6);
        assertEquals(searchQuery, booksList);
    }

    @Test
    public void getBookBySlug(){
        Book book = bookService.getBookBySlug(booksList.get(0).getSlug());
        assertEquals(book,booksList.get(0));
    }

    @Test
    public void getAllBooksByTag(){
        List<Book> book = bookService.getAllBooksByTag(booksList.get(0).getSlug(),0,6);
        assertEquals(book,booksList);
    }

    @Test
    public void changeRateBookBySlug(){
        PrincipalImplTest principal = new PrincipalImplTest();
        bookService.changeRateBookBySlug(principal, booksList.get(0).getSlug(), 2);
        // проверка того, что в методе не возникают исключения
    }

    @Test
    public void getBookRateTableBySlug(){
        Map<Integer, Integer> bookRateTableBySlug = bookService.getBookRateTableBySlug(booksList.get(0).getSlug());
        assertEquals(bookRateTableBySlug.get(2), 1);
    }

    @Test
    public void putComment(){
        PrincipalImplTest principal = new PrincipalImplTest();
        CommentDtoInput commentDtoInput = new CommentDtoInput();
        commentDtoInput.setDescription("12231223");
        List<BookReview> bookReviews = bookService.putComment(principal, commentDtoInput, booksList.get(0).getSlug());
        assertEquals(bookReviews, booksList.get(0).getBooksReview());
    }
}
