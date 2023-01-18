package com.example.MyBookShopApp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RateBookDto implements AopDto{
    @JsonProperty("bookId")
    private String bookid;

    @JsonProperty("value")
    private Integer value;

    public String getBookid() {
        return bookid;
    }

    public void setBookid(String bookid) {
        this.bookid = bookid;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
