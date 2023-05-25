package com.example.MyBookShopApp.enums;

public enum ErrorMessageResponse {
    NOT_FOUND_BOOK("Указанная книга не найдена"),
    COMMENT_INPUT_NOT_ADDED("Вы не указали комментарий при его добавлении"),

    CHANGE_BOOK_RATE("Оценка должна быть в пределах от 1 до 5"),

    FILTER_PARAM_INCORRECT("Некорректные параметры фильтрации"),

    FILE_NOT_FOUND_EXCEPTION("Не найден скачиваемый файл"),

    JWT_TOKEN_IS_NOT_FOUND("Указанный jwt token не найден"),

    ILLEGAL_MOVE_CHANGE_BOOK("Действие, производимое над книгой не найдено"),

    NOT_FOUND_BOOK_WITH_ID_EXCEPTION("Книга с данным id не была найдена.");
    private final String name;

    ErrorMessageResponse(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
