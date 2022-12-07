package com.example.MyBookShopApp.selenium.navigation;

import com.example.MyBookShopApp.utils.selenium.BookPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
public class BookSlugTest extends NavigationMainClass{
    private static ChromeDriver chromeDriver;

    @BeforeAll
    public static void setup(){
        System.setProperty("webdriver.chrome.driver","D:\\project\\MyBookShopApp\\src\\test\\resources\\uploads\\drivers\\chromedriver.exe");
        chromeDriver = NavigationMainClass.getChromeDriver();
        chromeDriver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
    }

    @Test
    public void clickToAuthor(){
        BookPage bookPage = new BookPage(chromeDriver);
        bookPage.callPage().pause().clickToAuthorLink();
        assertTrue(chromeDriver.getPageSource().contains("Biography"));
        assertTrue(chromeDriver.getPageSource().contains("Книги автора"));
    }

    @AfterEach
    public void testSignIn(){
        BookPage bookPage = new BookPage(chromeDriver);
        bookPage.backToPage();
        super.testSignIn();
    }

    @AfterEach
    public void clickToCart(){
        BookPage bookPage = new BookPage(chromeDriver);
        bookPage.backToPage();
        super.clickToCart();
    }

    @AfterEach
    public void checkHeaders(){
        BookPage bookPage = new BookPage(chromeDriver);
        bookPage.backToPage();
        super.checkHeaders();
    }

    @Test
    public void clickToPostoponted(){
        BookPage bookPage = new BookPage(chromeDriver);
        bookPage.backToPage();
        super.clickToPostoponted();
    }

    @AfterAll
    public static void tearDown(){
        chromeDriver.quit();
    }
}
