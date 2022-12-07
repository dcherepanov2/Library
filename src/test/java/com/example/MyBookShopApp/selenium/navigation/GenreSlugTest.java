package com.example.MyBookShopApp.selenium.navigation;

import com.example.MyBookShopApp.utils.selenium.AuthorPage;
import com.example.MyBookShopApp.utils.selenium.GenrePage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class GenreSlugTest{
    private static ChromeDriver chromeDriver;

    private final GenrePage genrePage;

    public GenreSlugTest() {
        chromeDriver = NavigationMainClass.getChromeDriver();
        genrePage = new GenrePage(chromeDriver);
    }

    @Test
    public void clickToBook(){
        genrePage.callPage().pause().pause().clickToBook().pause();
        assertTrue(chromeDriver.getPageSource().contains("Автор"));
        genrePage.backToPage().pause();
    }

    @AfterEach
    public void clickToPostoponted(){
        genrePage.clickToPostoponed(chromeDriver);
        genrePage.pause();
        assertTrue(chromeDriver.getPageSource().contains("Basket"));
        genrePage.backToPage().pause();
    }

    @AfterEach
    public void clickToCart(){
        genrePage.clickToCart(chromeDriver);
        genrePage.pause();
        assertTrue(chromeDriver.getPageSource().contains("Basket"));
        genrePage.backToPage().pause();
    }

    @AfterEach
    public void clickToSignIn(){
        genrePage.clickToSignIn(chromeDriver);
        genrePage.pause();
        assertTrue(chromeDriver.getPageSource().contains("Вход"));
        genrePage.backToPage().pause();
    }

    @AfterAll
    public static void quit(){
        GenrePage genrePage = new GenrePage(chromeDriver);
        genrePage.quit();
    }
}
