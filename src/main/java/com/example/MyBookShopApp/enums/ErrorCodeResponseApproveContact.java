package com.example.MyBookShopApp.enums;

public enum ErrorCodeResponseApproveContact {
    OLD_CODE("Код подтверждения устарел. Запросите новый."),
    SUCCESS("Авторизация прошла успешно!"),

    INCORRECT_ERROR_CODE("Некорректный код, проверьте правильность ввода."),

    NUMBER_OF_INPUT_ATTEMPTS("Количество попыток ввода привысило допустимое количество. Запросите новый код.");

    ErrorCodeResponseApproveContact(String message) {
        this.message = message;
    }

    private final String message;

    public String getMessage() {
        return message;
    }

}
