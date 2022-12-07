package com.example.MyBookShopApp.selenium.navigation;

import com.example.MyBookShopApp.utils.selenium.MainPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
public class MainPageSeleniumTest extends NavigationMainClass{
    private static ChromeDriver chromeDriver;

    @BeforeAll
    public static void setup(){
        System.setProperty("webdriver.chrome.driver","D:\\project\\MyBookShopApp\\src\\test\\resources\\uploads\\drivers\\chromedriver.exe");
        chromeDriver = NavigationMainClass.getChromeDriver();
        chromeDriver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
    }

    @Test
    @Order(1)
    public void testClickMain(){
        MainPage mainPage = new MainPage(chromeDriver);
        mainPage.callPage()
                .pause()
                .clickToMain()
                .pause();
        assertTrue(chromeDriver.getPageSource().contains("Books by tags"));
    }

    @Test
    @Order(2)
    public void testClickRecommended(){
        MainPage mainPage = new MainPage(chromeDriver);
        mainPage.callPage()
                .pause()
                .clickToMain()
                .pause()
                .clickToSlider()
                .pause()
                .clickToSlider();
        assertTrue(chromeDriver.getPageSource().contains("Бестселлер"));
    }

    @Test
    @Order(3)
    public void testTagClick(){
        MainPage mainPage = new MainPage(chromeDriver);
        mainPage.callPage()
                .pause()
                .clickToMain()
                .pause()
                .clickToTag()
                .pause();
        System.out.println(chromeDriver.getPageSource());
        assertTrue(chromeDriver.getPageSource().contains("тэги"));
    }

    @AfterEach
    public void testSignIn(){
        super.testSignIn();
    }

    @AfterEach
    public void clickToCart(){
        super.clickToCart();
    }

    @AfterEach
    public void checkHeaders(){
        super.checkHeaders();
    }

    @Test
    public void clickToPostoponted(){
        super.clickToPostoponted();
    }

    @AfterAll
    public static void tearDown(){
        chromeDriver.quit();
    }
}
