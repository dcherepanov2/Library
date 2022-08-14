package com.example.MyBookShopApp.dto;

import com.example.MyBookShopApp.data.author.Author;
import com.example.MyBookShopApp.data.book.Book;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonPropertyOrder(
        { "id", "slug", "title", "image", "authors",
          "discount", "isBestseller", "rating", "status", "price", "discountPrice"
        }
)
public class BookForMainPageDto {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("slug")
    private String slug;

    @JsonProperty("title")
    private String title;

    @JsonProperty("image")
    private String image;

    @JsonProperty("authors")
    private String authors;

    @JsonProperty("discount")
    private Short discount;

    @JsonProperty("isBestseller")
    private Boolean isBestseller;

    @JsonProperty("rating")
    private Byte rating;

    @JsonProperty("status")
    private String status;

    @JsonProperty("price")
    private Integer price;

    @JsonProperty("discountPrice")
    private Integer discountPrice;

    public BookForMainPageDto(Book book) {
        this.id = book.getId();
        this.slug = book.getSlug();
        this.title = book.getTitle();
        this.image = book.getImage();
        this.authors = this.setAuthors(book.getAuthors());
        this.authors = this.authors.substring(0,this.authors.length()-2);
        this.discount = book.getDiscount();
        this.isBestseller = book.getIsBestseller();
        this.rating = 5;
        this.status = "KEPT";
        this.price = book.getPrice();
        if(book.getDiscount() != 0)
            this.discountPrice = book.getPrice() * (100 / book.getDiscount());
        else
            this.discountPrice = book.getPrice();
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(Integer discountPrice) {
        this.discountPrice = discountPrice;
    }

    public Byte getRating() {
        return rating;
    }

    public void setRating(Byte rating) {
        this.rating = rating;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getBestseller() {
        return isBestseller;
    }

    public void setBestseller(Boolean bestseller) {
        isBestseller = bestseller;
    }

    public String getSlug() {
        return slug;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getAuthors() {
        return authors;
    }

    public String setAuthors(List<Author> authors) {
        StringBuilder authorStringContainList = new StringBuilder();
        for(Author author:authors){
            authorStringContainList.append(author.getName()).append(", ");
        }
        return this.authors = authorStringContainList.toString();
    }
}
