package com.example.MyBookShopApp.data.user;

import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.data.book.review.BookReviewEntity;
import com.example.MyBookShopApp.data.book.review.MessageEntity;
import com.example.MyBookShopApp.data.payments.BalanceTransactionEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL")
    private final String hash = this.hashCode() + UUID.randomUUID().toString().substring(0,10);

    @Column(columnDefinition = "TIMESTAMP NOT NULL")
    private LocalDateTime regTime;

    @Column(columnDefinition = "INT NOT NULL")
    private int balance;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL")
    private String name;

    @OneToMany
    @JoinTable(name = "user_contact",
            joinColumns = @JoinColumn(name = "user_id")
            ,inverseJoinColumns = @JoinColumn(name  = "code_trials"))
    private List<UserContactEntity> books = new ArrayList<>();

    @OneToMany
    @JoinTable(name = "user_contact",
            joinColumns = @JoinColumn(name = "user_id")
            ,inverseJoinColumns = @JoinColumn(name  = "code_trials"))
    private List<UserContactEntity> contact = new ArrayList<>();

    @OneToMany
    @JoinTable(name = "book_review_like",
            joinColumns = @JoinColumn(name = "user_id")
            ,inverseJoinColumns = @JoinColumn(name = "review_id")
    )
    private List<BookReviewEntity> bookReviewLikes = new ArrayList<>();

    @OneToMany
    @JoinTable(name = "message",
            joinColumns = @JoinColumn(name = "user_id")
            ,inverseJoinColumns = @JoinColumn(name = "id"))
    private List<MessageEntity> messages = new ArrayList<>();

    @OneToMany
    @JoinTable(
            name = "book_review",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private List<Book> booksReview = new ArrayList<>();

    @OneToMany
    @JoinTable(
            name = "balance_transaction",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    private List<BalanceTransactionEntity> transactions = new ArrayList<>();

    @OneToMany
    @JoinTable(
            name = "file_download",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private List<Book> fileDownloads = new ArrayList<>();

    public List<BookReviewEntity> getBookReviewLikes() {
        return bookReviewLikes;
    }

    public void setBookReviewLikes(List<BookReviewEntity> bookReviewLikes) {
        this.bookReviewLikes = bookReviewLikes;
    }

    public List<MessageEntity> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageEntity> messages) {
        this.messages = messages;
    }

    public List<Book> getBooksReview() {
        return booksReview;
    }

    public void setBooksReview(List<Book> booksReview) {
        this.booksReview = booksReview;
    }

    public List<BalanceTransactionEntity> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<BalanceTransactionEntity> transactions) {
        this.transactions = transactions;
    }

    public List<Book> getFileDownloads() {
        return fileDownloads;
    }

    public void setFileDownloads(List<Book> fileDownloads) {
        this.fileDownloads = fileDownloads;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHash() {
        return hash;
    }

    public LocalDateTime getRegTime() {
        return regTime;
    }

    public void setRegTime(LocalDateTime regTime) {
        this.regTime = regTime;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UserContactEntity> getContact() {
        return contact;
    }

    public void setContact(List<UserContactEntity> contact) {
        this.contact = contact;
    }

}
