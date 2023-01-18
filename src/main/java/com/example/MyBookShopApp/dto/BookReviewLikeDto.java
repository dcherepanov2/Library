package com.example.MyBookShopApp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BookReviewLikeDto implements AopDto{

    @JsonProperty("value")
    private Short value;

    @JsonProperty("reviewid")
    private Integer reviewid;

    public Short getValue() {
        return value;
    }

    public void setValue(Short value) {
        this.value = value;
    }

    public Integer getReviewid() {
        return reviewid;
    }

    public void setReviewid(Integer reviewid) {
        this.reviewid = reviewid;
    }
}
