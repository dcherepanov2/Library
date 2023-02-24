package com.example.MyBookShopApp.utils;


import com.example.MyBookShopApp.dto.GenreEntitySort;

import java.util.Comparator;

public class GenreEntitySortComparator implements Comparator<GenreEntitySort> {

    @Override
    public int compare(GenreEntitySort o1, GenreEntitySort o2) {
        return o2.getBooksCount().compareTo(o1.getBooksCount());
    }
}
