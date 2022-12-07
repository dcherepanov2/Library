package com.example.MyBookShopApp.selenium.navigation;

import com.example.MyBookShopApp.utils.selenium.AuthorChapterPage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class AuthorChapterTest {
    private final static ChromeDriver chromeDriver;
    private final static AuthorChapterPage authorChapterPage;

    static  {
        chromeDriver = NavigationMainClass.getChromeDriver();
        authorChapterPage = new AuthorChapterPage(chromeDriver);
    }

    @Test
    public void clickToLetter(){
        authorChapterPage.callPage()
                         .pause()
                         .clickToLetter()
                         .pause();
        assertTrue(chromeDriver.getPageSource().contains("Augustina Dagg"));
        authorChapterPage.backToPage();
    }

    @Test
    public void clickToSlug(){
        authorChapterPage.callPage()
                .pause()
                .clickToLetter()
                .pause()
                .clickToSlug()
                .pause();
        assertTrue(chromeDriver.getPageSource().contains("Biography"));
        authorChapterPage.backToPage();
    }

    @AfterEach
    public void clickToPostoponted(){
        authorChapterPage.clickToPostoponed(chromeDriver);
        authorChapterPage.pause();
        assertTrue(chromeDriver.getPageSource().contains("Basket"));
        authorChapterPage.backToPage().pause();
    }

    @AfterEach
    public void clickToCart(){
        authorChapterPage.clickToCart(chromeDriver);
        authorChapterPage.pause();
        assertTrue(chromeDriver.getPageSource().contains("Basket"));
        authorChapterPage.backToPage().pause();
    }

    @AfterEach
    public void clickToSignIn(){
        authorChapterPage.clickToSignIn(chromeDriver);
        authorChapterPage.pause();
        assertTrue(chromeDriver.getPageSource().contains("Вход"));
        authorChapterPage.backToPage().pause();
    }

    @AfterAll
    public static void tearDown(){
        authorChapterPage.quit();
    }
}
