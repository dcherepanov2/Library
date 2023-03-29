package com.example.MyBookShopApp.data.book.review;

import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.data.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "book_review")
public class BookReview {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id", columnDefinition = "INT NOT NULL")
    private Book bookId;

    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @OneToOne
    private User userId;

    @Column(name = "time", columnDefinition = "TIMESTAMP NOT NULL")
    private LocalDateTime time;

    @Column(name = "text", columnDefinition = "TEXT NOT NULL")
    private String text;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Book getBookId() {
        return bookId;
    }

    public void setBookId(Book bookId) {
        this.bookId = bookId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
