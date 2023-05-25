package com.example.MyBookShopApp.data.book;

import com.example.MyBookShopApp.data.author.Author;
import com.example.MyBookShopApp.data.book.file.BookFile;
import com.example.MyBookShopApp.data.book.file.FileDownloadEntity;
import com.example.MyBookShopApp.data.book.links.BookRating;
import com.example.MyBookShopApp.data.book.review.BookReview;
import com.example.MyBookShopApp.data.genre.GenreEntity;
import com.example.MyBookShopApp.data.tags.Tag;
import com.example.MyBookShopApp.data.user.User;
import com.example.MyBookShopApp.dto.BookChangeRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "book")
public class Book {
    public Book(BookChangeRequest request) {
        this.isBestseller = request.getIsBestseller();
        this.datePublic = new Date();
        this.price = request.getPrice();
        this.description = request.getDescription();
        this.title = request.getTitle();
        this.discount = request.getDiscount();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_seq_gen")
    @SequenceGenerator(name = "book_seq_gen", sequenceName = "book_sequence", allocationSize = 1, initialValue = 1001)
    @NotNull
    @Column(name = "id",columnDefinition = "INT NOT NULL AUTO_INCREMENT")
    private Integer id;

    @Column(name = "is_bestseller", columnDefinition = "TINYINT NOT NULL")
    @NotNull
    private Boolean isBestseller;

    @Column(name = "slug",columnDefinition = "VARCHAR(255) NOT NULL")
    @NotNull
    private final String slug = this.hashCode() + UUID.randomUUID().toString().substring(0,10);

    @Column(name = "title",columnDefinition = "VARCHAR(255) NOT NULL")
    @NotNull
    private String title;

    @Column(name = "image", columnDefinition = "VARCHAR(255)")
    private String image;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "price", columnDefinition = "INT NOT NULL")
    @NotNull
    private Integer price;

    @Column(name = "discount", columnDefinition = "TINYINT NOT NULL DEFAULT 0")
    @NotNull
    private Short discount = 0;

    @Column(name = "pub_date", columnDefinition = "DATE NOT NULL")
    private Date datePublic;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "book2genre"
            , joinColumns = @JoinColumn(name = "book_id")
            , inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    @JsonIgnore
    private List<GenreEntity> genres;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "rating_book", joinColumns = @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name = "id"))
    private List<BookRating> bookRatings;

    @ManyToMany
    @JoinTable(name = "book2author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "authors_id"))
    private List<Author> authors;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "file_download"
            , joinColumns = @JoinColumn(name = "book_id")
            , inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<FileDownloadEntity> fileDownloadEntities;

    @OneToMany
    @JoinTable(
            name = "book2user",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> bookJoinUsers;


    @OneToMany
    @JoinTable(
            name = "book_review",
            joinColumns = @JoinColumn(name = "book_id")
            , inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<BookReview> booksReview;

    @ManyToMany
    @JoinTable(
            name = "book2tag",
            joinColumns = @JoinColumn(name = "book_id")
            , inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    List<Tag> tags;

    @OneToMany
    @JoinTable(
            name = "book2file",
            joinColumns = @JoinColumn(name = "book_id")
            , inverseJoinColumns = @JoinColumn(name = "file_id")
    )
    List<BookFile> bookFiles;

    public Book() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {

        this.authors = authors;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDatePublic() {
        return datePublic;
    }

    public void setDatePublic(Date datePublic) {
        this.datePublic = datePublic;
    }

    public List<GenreEntity> getGenres() {
        return genres;
    }

    public void setGenres(List<GenreEntity> genres) {
        this.genres = genres;
    }

    public String getSlug() {
        return slug;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Boolean getBestseller() {
        return isBestseller;
    }

    public void setBestseller(Boolean bestseller) {
        isBestseller = bestseller;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Short getDiscount() {
        return discount;
    }

    public void setDiscount(Short discount) {
        this.discount = discount;
    }

    public List<FileDownloadEntity> getFileDownloadEntities() {
        return fileDownloadEntities;
    }

    public void setFileDownloadEntities(List<FileDownloadEntity> fileDownloadEntities) {
        this.fileDownloadEntities = fileDownloadEntities;
    }

    public List<User> getBookJoinUsers() {
        return bookJoinUsers;
    }

    public void setBookJoinUsers(List<User> bookJoinUsers) {
        this.bookJoinUsers = bookJoinUsers;
    }

//    public List<BookReview> getBooksReview() {
//        return booksReview;
//    }
//
//    public void setBooksReview(List<BookReview> booksReview) {
//        this.booksReview = booksReview;
//    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<BookFile> getBookFiles() {
        return bookFiles;
    }

    public void setBookFiles(List<BookFile> bookFiles) {
        this.bookFiles = bookFiles;
    }

    public List<BookRating> getBookRatings() {
        return bookRatings;
    }

    public void setBookRatings(List<BookRating> bookRatings) {
        this.bookRatings = bookRatings;
    }

    public List<BookReview> getBooksReview() {
        return booksReview;
    }

    public void setBooksReview(List<BookReview> booksReview) {
        this.booksReview = booksReview;
    }
}
