package com.example.MyBookShopApp.data.book;

import com.example.MyBookShopApp.data.author.Author;
import com.example.MyBookShopApp.data.book.file.BookFile;
import com.example.MyBookShopApp.data.book.file.FileDownloadEntity;
import com.example.MyBookShopApp.data.book.review.BookReviewEntity;
import com.example.MyBookShopApp.data.genre.GenreEntity;
import com.example.MyBookShopApp.data.tags.Tag;
import com.example.MyBookShopApp.data.user.UserEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    @Column(name = "id",columnDefinition = "INT NOT NULL AUTO_INCREMENT")
    private Integer id;

    @Column(name ="is_bestseller", columnDefinition = "TINYINT NOT NULL")
    @NotNull
    private Short isBestseller;

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
            ,joinColumns = @JoinColumn(name = "book_id")
            ,inverseJoinColumns =  @JoinColumn(name = "genre_id")
    )
    private List<GenreEntity> genres;

    @ManyToMany
    @JoinTable(name = "book2author", joinColumns = @JoinColumn(name = "book_id"))
    private List<Author> authors = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "file_download"
            ,joinColumns = @JoinColumn(name = "book_id")
            ,inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<FileDownloadEntity> fileDownloadEntities= new ArrayList<>();

    @OneToMany
    @JoinTable(
            name = "book2user",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<UserEntity> bookJoinUsers = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "book2author",
            joinColumns = @JoinColumn(name = "authors_id")
            ,inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private List<Author> booksToAuthors = new ArrayList<>();

    @OneToMany
    @JoinTable(
            name = "book_review",
            joinColumns = @JoinColumn(name = "book_id")
            ,inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<BookReviewEntity> booksReview = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "book2tag",
            joinColumns = @JoinColumn(name = "book_id")
            ,inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    List<Tag> tags = new ArrayList<>();

    @OneToMany
    @JoinTable(
            name = "book2file",
            joinColumns = @JoinColumn(name = "book_id")
            ,inverseJoinColumns = @JoinColumn(name = "file_id")
    )
    List<BookFile> bookFiles = new ArrayList<>();

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

    public Boolean getIsBestseller() {
        return this.isBestseller == 1;
    }

    public void setIsBestseller(Short isBestseller) {
        this.isBestseller = isBestseller;
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

    public List<UserEntity> getBookJoinUsers() {
        return bookJoinUsers;
    }

    public void setBookJoinUsers(List<UserEntity> bookJoinUsers) {
        this.bookJoinUsers = bookJoinUsers;
    }

    public List<Author> getBooksToAuthors() {
        return booksToAuthors;
    }

    public void setBooksToAuthors(List<Author> booksToAuthors) {
        this.booksToAuthors = booksToAuthors;
    }

    public List<BookReviewEntity> getBooksReview() {
        return booksReview;
    }

    public void setBooksReview(List<BookReviewEntity> booksReview) {
        this.booksReview = booksReview;
    }

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
}
