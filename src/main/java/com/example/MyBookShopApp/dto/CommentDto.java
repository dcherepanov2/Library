package com.example.MyBookShopApp.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CommentDto {

    private String descriptionPartTwo = "";
    private String description;
    private Integer rate = 5;
    private LocalDateTime pubDate = LocalDateTime.now();
    private Integer slug;
    private Integer like = 0;
    private Integer dislike = 0;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public String getDescriptionPartTwo() {
        return descriptionPartTwo;
    }

    public void setDescriptionPartTwo(String descriptionPartTwo) {
        this.descriptionPartTwo = descriptionPartTwo;
    }

    public String getPubDate() {
        LocalDateTime localDateTime = this.pubDate;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        return localDateTime.format(formatter);
    }

    public void setPubDate(LocalDateTime pubDate) {
        this.pubDate = pubDate;
    }

    public Integer getSlug() {
        return slug;
    }

    public void setSlug(Integer slug) {
        this.slug = slug;
    }

    public Integer getLike() {
        return like;
    }

    public void setLike(Integer like) {
        this.like = like;
    }

    public Integer getDislike() {
        return dislike;
    }

    public void setDislike(Integer dislike) {
        this.dislike = dislike;
    }
}
