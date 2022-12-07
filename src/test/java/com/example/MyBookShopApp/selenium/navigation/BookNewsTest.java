package com.example.MyBookShopApp.selenium.navigation;

import com.example.MyBookShopApp.utils.selenium.AuthorPage;
import com.example.MyBookShopApp.utils.selenium.BookNewsPage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class BookNewsTest {
    private static ChromeDriver chromeDriver;
    private final BookNewsPage bookNewsPage;

    public BookNewsTest() {
        chromeDriver = NavigationMainClass.getChromeDriver();
        this.bookNewsPage = new BookNewsPage(chromeDriver);
    }

    @Test
    public void clickToSlug(){
        bookNewsPage.callPage().pause().clickToSlug().pause();
        assertTrue(chromeDriver.getPageSource().contains("Автор:"));
    }

    @AfterEach
    public void clickToPostoponted(){
        bookNewsPage.clickToPostoponed(chromeDriver);
        bookNewsPage.pause();
        assertTrue(chromeDriver.getPageSource().contains("Basket"));
        bookNewsPage.backToPage().pause();
    }

    @AfterEach
    public void clickToCart(){
        bookNewsPage.clickToCart(chromeDriver);
        bookNewsPage.pause();
        assertTrue(chromeDriver.getPageSource().contains("Basket"));
        bookNewsPage.backToPage().pause();
    }

    @AfterEach
    public void clickToSignIn(){
        bookNewsPage.clickToSignIn(chromeDriver);
        bookNewsPage.pause();
        assertTrue(chromeDriver.getPageSource().contains("Вход"));
        bookNewsPage.backToPage().pause();
    }

    @AfterAll
    public static void quit(){
        BookNewsPage bookNewsPageLocal = new BookNewsPage(chromeDriver);
        bookNewsPageLocal.quit();
    }
}
