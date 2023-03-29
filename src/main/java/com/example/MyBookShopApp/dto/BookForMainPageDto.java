package com.example.MyBookShopApp.dto;

import com.example.MyBookShopApp.data.author.Author;
import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.data.book.links.Book2UserEntity;
import com.example.MyBookShopApp.data.enums.BookToUserType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Arrays;
import java.util.List;

@JsonPropertyOrder(
        {"id", "slug", "title", "image", "authors",
                "discount", "isBestseller", "rating", "status", "price", "discountPrice"
        }
)
public class BookForMainPageDto implements AopDto {

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

    @JsonIgnore
    private List<Author> authorList;

    public List<Author> getAuthorList() {
        return authorList;
    }

    public void setAuthorList(List<Author> authorList) {
        this.authorList = authorList;
    }

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
        this.authorList = book.getAuthors();
        if (this.authors != null)
            this.authors = this.authors.substring(0, this.authors.length() - 2);
        this.discount = book.getDiscount();
        book.setIsBestseller((short) 0);
        this.isBestseller = book.getIsBestseller();
        this.rating = 5;
        this.status = "false";
        this.price = book.getPrice();
        if (book.getDiscount() != 0)
            this.discountPrice = book.getDiscount() * (100 / book.getPrice());
        else
            this.discountPrice = book.getPrice();
    }

    public BookForMainPageDto(Book book, String keptContents, String cartContents, List<Book2UserEntity> book2UserEntities) {
        this.id = book.getId();
        this.slug = book.getSlug();
        this.title = book.getTitle();
        this.image = book.getImage();
        this.authors = this.setAuthors(book.getAuthors());
        this.authorList = book.getAuthors();
        if (this.authors != null)
            this.authors = this.authors.substring(0, this.authors.length() - 2);
        this.discount = book.getDiscount();
        book.setIsBestseller((short) 0);
        this.isBestseller = book.getIsBestseller();
        this.rating = 5;
        this.status = calculateStatus(cartContents, keptContents, book2UserEntities).toString();
        this.price = book.getPrice();
        if (book.getDiscount() != 0)
            this.discountPrice = book.getDiscount() * (100 / book.getPrice());
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

    public boolean getBestseller() {
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
        for (Author author : authors) {
            authorStringContainList.append(author.getName()).append(", ");
        }
        return this.authors = authorStringContainList.toString();
    }

    public BookToUserType calculateStatus(String cartContents, String keptContents, List<Book2UserEntity> books2Users) {
        boolean ifKept = Arrays.stream(keptContents.split("/")).anyMatch(x -> x.equals(this.slug));
        boolean ifPaid = books2Users.stream().anyMatch(x -> x.getBookId().equals(this.id) && x.getTypeId().equals(1));
        boolean ifArchived = books2Users.stream().anyMatch(x -> x.getBookId().equals(this.id) && x.getTypeId().equals(2));
        boolean ifCart = Arrays.stream(cartContents.split("/")).anyMatch(x -> x.equals(this.slug));
        if (ifKept)
            return BookToUserType.KEPT;
        else if (ifPaid)
            return BookToUserType.PAID;
        else if (ifArchived)
            return BookToUserType.ARCHIVED;
        else if (ifCart)
            return BookToUserType.CART;
        return BookToUserType.FALSE;
    }
}
