package com.example.MyBookShopApp.selenium.navigation;

import com.example.MyBookShopApp.utils.selenium.MainPage;
import lombok.Data;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Data
public class NavigationMainClass {

    public static ChromeDriver getChromeDriver() {
        System.setProperty("webdriver.chrome.driver","D:\\project\\MyBookShopApp\\src\\test\\resources\\uploads\\drivers\\chromedriver.exe");
        chromeDriver = new ChromeDriver();
        chromeDriver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
        return chromeDriver;
    }

    private static ChromeDriver chromeDriver;

    public void testSignIn(){
        MainPage mainPage = new MainPage(chromeDriver);
        mainPage.callPage()
                .pause()
                .clickToMain()
                .pause()
                .clickToSignIn()
                .pause();
        assertTrue(chromeDriver.getPageSource().contains("Вход"));
    }

    public void clickToCart(){
        MainPage mainPage = new MainPage(chromeDriver);
        mainPage.callPage()
                .pause()
                .clickToMain()
                .pause()
                .clickToCart()
                .pause();
        assertTrue(chromeDriver.getPageSource().contains("Basket"));
    }

    public void checkHeaders(){
        assertTrue(chromeDriver.getPageSource().contains("Main"));
        assertTrue(chromeDriver.getPageSource().contains("Genres"));
        assertTrue(chromeDriver.getPageSource().contains("Popular"));
        assertTrue(chromeDriver.getPageSource().contains("Authors"));
        assertTrue(chromeDriver.getPageSource().contains("News"));
    }

    public void clickToPostoponted(){
        MainPage mainPage = new MainPage(chromeDriver);
        mainPage.callPage()
                .pause()
                .clickToMain()
                .pause()
                .clickToPostoponed()
                .pause();
        assertTrue(chromeDriver.getPageSource().contains("Basket"));
    }

    public static void tearDown(){
        chromeDriver.quit();
    }
}
