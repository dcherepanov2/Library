package com.example.MyBookShopApp.selenium.navigation;

import com.example.MyBookShopApp.utils.selenium.AuthorPage;
import com.example.MyBookShopApp.utils.selenium.GenresChapterPage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class GenreChapterTest {
    private final GenresChapterPage genresChapterPage;

    private static ChromeDriver chromeDriver;

    public GenreChapterTest() {
        chromeDriver = NavigationMainClass.getChromeDriver();
        this.genresChapterPage = new GenresChapterPage(chromeDriver);
    }

    @Test
    public void clickToSlug(){
        genresChapterPage.callPage()
                .pause()
                .clickToSlugGenre()
                .pause();
        assertTrue(chromeDriver.getPageSource().contains("Slug genres"));
    }

    @AfterEach
    public void clickToPostoponted(){
        genresChapterPage.clickToPostoponed(chromeDriver);
        genresChapterPage.pause();
        assertTrue(chromeDriver.getPageSource().contains("Basket"));
        genresChapterPage.backToPage().pause();
    }

    @AfterEach
    public void clickToCart(){
        genresChapterPage.clickToCart(chromeDriver);
        genresChapterPage.pause();
        assertTrue(chromeDriver.getPageSource().contains("Basket"));
        genresChapterPage.backToPage().pause();
    }

    @AfterEach
    public void clickToSignIn(){
        genresChapterPage.clickToSignIn(chromeDriver);
        genresChapterPage.pause();
        assertTrue(chromeDriver.getPageSource().contains("Вход"));
        genresChapterPage.backToPage().pause();
    }

    @AfterAll
    public static void quit(){
        GenresChapterPage genresChapterPage1 = new GenresChapterPage(chromeDriver);
        genresChapterPage1.quit();
    }
}
