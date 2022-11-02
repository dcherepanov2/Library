package com.example.MyBookShopApp.unit.service.book;

import com.example.MyBookShopApp.repo.bookrepos.BookRepo;
import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.service.bookServices.BookService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookServiceTest {
    private Book book;
    @MockBean
    private BookService bookService;
//    @MockBean
//    private BookBuilderForTest bookBuilderForTest;

//    @Test
//    public void test1(){
//        System.out.println(1);
//        bookService.
//    }
//
//    @BeforeEach
//    public void initBook(){
//        for(int i = 0;i<6;i++)
//            book = bookBuilderForTest.createTagForBook()
//                                     .createBookReview()
//                                     .createBook();
//    }
//
//    @Test
//    public void getPopularBooksData(){
//        List<Book> popularBooksData = bookService.getPopularBooksData(0, 6);
//        assertEquals(popularBooksData.size(), 6);
//    }
//
//    @Test
//    public void getSearchQuery(){
//        List<Book> searchBook =
//                bookService.getSearchQuery(book.getTitle().substring(1),0, 6);
//        assertEquals(searchBook.size(), 6);
//    }
//
//    @Test
//    public void getFilterBooksByDate(){
//        Date minusOneDay = Date.valueOf(LocalDate.now().minusDays(1));
//        Date plusOneDay = Date.valueOf(LocalDate.now().plusDays(1));
//        List<Book> searchBook = bookService.getFilterBooksByDate(minusOneDay, plusOneDay, 0, 6);
//        assertEquals(searchBook.size(), 6);
//    }
//
//    @Test
//    public void getBookBySlug(){
//        Book test = bookService.getBookBySlug(book.getSlug());
//        assertEquals(book.getSlug(), test.getSlug());
//    }
//
//    @Test
//    public void getAllBooksByTag(){
//        String tag = book.getTags().get(0).getSlug();
//        List<Book> allBooksByTag = bookService.getAllBooksByTag(tag, 0, 6);
//        assertEquals(allBooksByTag.size(), 6);
//    }
//
////    @Test
////    public void getBookRateTableBySlug(){
////        List
    }
//}
