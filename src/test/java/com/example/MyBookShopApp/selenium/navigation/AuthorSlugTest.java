package com.example.MyBookShopApp.selenium.navigation;

import com.example.MyBookShopApp.utils.selenium.AuthorPage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AuthorSlugTest extends NavigationMainClass{

    private static ChromeDriver chromeDriver;

    public AuthorSlugTest() {
        AuthorSlugTest.chromeDriver = NavigationMainClass.getChromeDriver();
    }

    @Test
    public void clickToAllBook(){
        AuthorPage authorPage = new AuthorPage(chromeDriver);
        authorPage.callPage().getAllBooksAuthor().pause();
        authorPage.backToPage().pause();
    }

    @AfterEach
    public void clickToSignIn(){
        AuthorPage authorPage = new AuthorPage(chromeDriver);
        authorPage.clickToSignIn(chromeDriver);
        authorPage.pause();
        authorPage.backToPage().pause();
    }

    @AfterEach
    public void clickToPostopented(){
        AuthorPage authorPage = new AuthorPage(chromeDriver);
        authorPage.clickToPostoponed(chromeDriver);
        authorPage.pause();
        authorPage.backToPage().pause();
    }

    @AfterEach
    public void clickToCart(){
        AuthorPage authorPage = new AuthorPage(chromeDriver);
        authorPage.clickToCart(chromeDriver);
        authorPage.pause();
        authorPage.backToPage().pause();
    }

    @AfterAll
    public static void quit(){
        AuthorPage authorPage = new AuthorPage(chromeDriver);
        authorPage.quit();
    }
}
