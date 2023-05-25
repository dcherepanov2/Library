package com.example.MyBookShopApp.dto;

public class SearchBookDto implements AopDto{
    private String query;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

}
