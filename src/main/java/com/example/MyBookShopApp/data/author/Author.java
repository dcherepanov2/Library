package com.example.MyBookShopApp.data.author;

import com.example.MyBookShopApp.data.book.Book;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "author")
public class Author {

    public Author(String name){
        this.name = name;
    }

    public Author() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_seq_gen")
    @NotNull
    @Column(name = "id", columnDefinition = "INT NOT NULL AUTO_INCREMENT")
    @SequenceGenerator(name = "author_seq_gen", sequenceName = "author_sequence", initialValue = 1001, allocationSize = 1)
    private Integer id;

    @Column(name = "photo", columnDefinition = "VARCHAR(255)")
    private String photo;

    @Column(name = "slug", columnDefinition = "VARCHAR(255) NOT NULL")
    @NotNull
    private String slug = this.hashCode() + UUID.randomUUID().toString();

    @Column(name = "name", columnDefinition = "VARCHAR(255) NOT NULL")
    @NotNull
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;



    @ManyToMany
    @JoinTable(
            name = "book2author",
            joinColumns = @JoinColumn(name = "authors_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    private List<Book> books;

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public char firstLetter(){
        return this.name.charAt(0);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}