package com.example.MyBookShopApp.data.enums;

public enum BookToUserType {
    KEPT(1, "kept"),
    CART(2, "cart"),
    PAID(3, "paid"),
    ARCHIVED(4, "archived"),
    FALSE(5, "false");

    private final int code;

    private final String name;

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return name;
    }
//
//    public Set<BookToUserType> getByCode(int code){
//        BookToUserType[] bookToUserTypes = BookToUserType.values();
//        Set<BookToUserType> bookToUserTypeList = new HashSet<>();
//        for(BookToUserType contactType:bookToUserTypes){
//            if(contactType.getCode()==code){
//                bookToUserTypeList.add(contactType);
//            }
//        }
//        return bookToUserTypeList;
//    }


    BookToUserType(int code, String name) {
        this.code = code;
        this.name = name;
    }
}
