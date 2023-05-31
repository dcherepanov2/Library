package com.example.MyBookShopApp.dto;

import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.data.tags.Tag;
import lombok.Data;

import java.util.List;

@Data
public class TagDto {

    public TagDto(Tag tag) {
        this.name = tag.getName();
        this.slug = tag.getSlug();
        this.size = tag.getSize();
        this.tagClicks = tag.getTagClicks();
    }

    private String name;

    private String slug;

    private Integer size;

    private Integer tagClicks;

}
