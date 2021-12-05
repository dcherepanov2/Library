package com.example.MyBookShopApp.data.enums;

import java.util.HashSet;
import java.util.Set;

public enum BookToUserType {
    KEPT(1),
    CART(2),
    PAID(3),
    ARCHIVED(4);

    private final int code;

    public int getCode() {
        return code;
    }

    public Set<BookToUserType> getByCode(int code){
        BookToUserType[] bookToUserTypes = BookToUserType.values();
        Set<BookToUserType> bookToUserTypeList = new HashSet<>();
        for(BookToUserType contactType:bookToUserTypes){
            if(contactType.getCode()==code){
                bookToUserTypeList.add(contactType);
            }
        }
        return bookToUserTypeList;
    }

    BookToUserType(int code) {
        this.code = code;
    }
}
