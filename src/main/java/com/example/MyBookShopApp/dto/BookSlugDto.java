package com.example.MyBookShopApp.dto;

import com.example.MyBookShopApp.data.author.Author;
import com.example.MyBookShopApp.data.book.Book;

import java.util.List;

public class BookSlugDto extends BookForMainPageDto{

    private String description;


    private List<Author> authorsList;

    public BookSlugDto(Book book, List<Author> authors) {
        super(book);
        this.authorsList = authors;
        this.description = book.getDescription();
    }

    @Override
    public void setAuthors(String authors) {
        super.setAuthors(authors);
    }

    @Override
    public String getStatus() {
        return super.getStatus();
    }

    @Override
    public void setStatus(String status) {
        super.setStatus(status);
    }

    @Override
    public Integer getDiscountPrice() {
        return super.getDiscountPrice();
    }

    @Override
    public void setDiscountPrice(Integer discountPrice) {
        super.setDiscountPrice(discountPrice);
    }

    @Override
    public Byte getRating() {
        return super.getRating();
    }

    @Override
    public void setRating(Byte rating) {
        super.setRating(rating);
    }

    @Override
    public Integer getId() {
        return super.getId();
    }

    @Override
    public void setId(Integer id) {
        super.setId(id);
    }

    @Override
    public Boolean getBestseller() {
        return super.getBestseller();
    }

    @Override
    public void setBestseller(Boolean bestseller) {
        super.setBestseller(bestseller);
    }

    @Override
    public String getSlug() {
        return super.getSlug();
    }

    @Override
    public String getTitle() {
        return super.getTitle();
    }

    @Override
    public void setTitle(String title) {
        super.setTitle(title);
    }

    @Override
    public String getImage() {
        return super.getImage();
    }

    @Override
    public void setImage(String image) {
        super.setImage(image);
    }

    @Override
    public Integer getPrice() {
        return super.getPrice();
    }

    @Override
    public void setPrice(Integer price) {
        super.setPrice(price);
    }

    @Override
    public Short getDiscount() {
        return super.getDiscount();
    }

    @Override
    public void setDiscount(Short discount) {
        super.setDiscount(discount);
    }

    @Override
    public void setSlug(String slug) {
        super.setSlug(slug);
    }

    @Override
    public String getAuthors() {
        return super.getAuthors();
    }

    @Override
    public String setAuthors(List<Author> authors) {
        return super.setAuthors(authors);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Author> getAuthorsList() {
        return authorsList;
    }

    public void setAuthorsList(List<Author> authorsList) {
        this.authorsList = authorsList;
    }
}
