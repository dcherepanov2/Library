package com.example.MyBookShopApp.service.builders;

import com.example.MyBookShopApp.data.author.Author;
import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.data.book.file.BookFile;
import com.example.MyBookShopApp.data.book.review.BookReview;
import com.example.MyBookShopApp.data.tags.Tag;
import com.example.MyBookShopApp.data.user.Role;
import com.example.MyBookShopApp.data.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.Random;

@Service
public class CreateObjectsUtil {
    private static String photo;

    private static SaveObjectUtil saveObjectUtil;

    @Autowired
    public void setSaveObjectUtil(SaveObjectUtil saveObjectUtil){
        CreateObjectsUtil.saveObjectUtil = saveObjectUtil;
    }


    public static BookFile createTestBookFileUtil(){
        BookFile bookFile = new BookFile();
        bookFile.setBalance(100);
        bookFile.setHash(bookFile.getHash());
        bookFile.setPath("src/test/resources/uploads/bookfiles/file.epub");
        bookFile.setTypeId(1);
        return bookFile;
    }

    public static Book createBookTest(){
        Book book = new Book();
        book.setTitle("test");
        book.setDatePublic(new Date());
        book.setImage(photo);
        book.setPrice(100);
        book.setDiscount((short) 30);
        book.setIsBestseller((short) 1);
        book.setDescription("test");
        return book;
    }

    public static Author createAuthorTest(){
        Author author = new Author();
        author.setPhoto(photo);
        author.setSlug("test");
        author.setDescription("test");
        author.setName("test");
        saveObjectUtil.saveAuthor(author);
        return author;
    }

    public static User createUserTest(){
        User user = new User();
        user.setBalance((double) 100L);
        user.setHash(user.getHash());
        user.setDateRegistration(LocalDate.from(LocalDateTime.now()));
        user.setRoles(Collections.singletonList(CreateObjectsUtil.createRoleTest()));
        user.setUsername("test");
        user.setHash(user.getHash());
        saveObjectUtil.saveUser(user);
        return user;
    }

    public static Role createRoleTest(){
        Role role = new Role();
        role.setName("USER_CLIENT");
        return role;
    }

    public static Tag createTagTest(){
        Tag tag = new Tag();
        tag.setTagClicks(new Random().nextInt(10));
        tag.setSlug("test");
        tag.setSize(4);
        tag.setName("test");
        return tag;
    }

    public static BookReview createBookReview(User user, Book book){
        BookReview bookReview = new BookReview();
        bookReview.setText("123");
        bookReview.setTime(LocalDateTime.now());
        bookReview.setUserId(Math.toIntExact(user.getId()));
        bookReview.setBookId(book.getId());
        return bookReview;
    }

}
