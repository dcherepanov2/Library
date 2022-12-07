package com.example.MyBookShopApp.selenium.navigation;

import com.example.MyBookShopApp.utils.selenium.AuthorPage;
import com.example.MyBookShopApp.utils.selenium.BookPopularChapterPage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class BookPopularChapter {

    private final BookPopularChapterPage bookPopularChapterPage;
    private static ChromeDriver chromeDriver;

    public BookPopularChapter() {
        chromeDriver = NavigationMainClass.getChromeDriver();
        this.bookPopularChapterPage = new BookPopularChapterPage(chromeDriver);
    }
    @Test
    public void clickToSlug(){
        bookPopularChapterPage.callPage().pause().clickToSlug().pause();
        assertTrue(chromeDriver.getPageSource().contains("Автор:"));
    }

    @AfterEach
    public void clickToPostoponted(){
        bookPopularChapterPage.clickToPostoponed(chromeDriver);
        bookPopularChapterPage.pause();
        assertTrue(chromeDriver.getPageSource().contains("Basket"));
        bookPopularChapterPage.backToPage().pause();
    }

    @AfterEach
    public void clickToCart(){
        bookPopularChapterPage.clickToCart(chromeDriver);
        bookPopularChapterPage.pause();
        assertTrue(chromeDriver.getPageSource().contains("Basket"));
        bookPopularChapterPage.backToPage().pause();
    }

    @AfterEach
    public void clickToSignIn(){
        bookPopularChapterPage.clickToSignIn(chromeDriver);
        bookPopularChapterPage.pause();
        assertTrue(chromeDriver.getPageSource().contains("Вход"));
        bookPopularChapterPage.backToPage().pause();
    }

    @AfterAll
    public static void quit(){
        BookPopularChapterPage bookPopularChapterPage1 = new BookPopularChapterPage(chromeDriver);
        bookPopularChapterPage1.quit();
    }
}
