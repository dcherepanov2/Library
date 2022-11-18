package com.example.MyBookShopApp.utils;

import com.example.MyBookShopApp.data.tags.Tag;

public class TagTestBuilder {

    private final Tag tag;

    public TagTestBuilder() {
        this.tag = new Tag();
    }

    public TagTestBuilder setClick(){
        tag.setTagClicks(5);
        return this;
    }

    public Tag build(){
        return tag;
    }
}
