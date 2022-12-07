package com.example.MyBookShopApp.utils.selenium;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


public class BookNewsPage implements SeleniumPage{

    private final String url = "http://localhost:8080/books/recent";

    private final ChromeDriver chromeDriver;

    public BookNewsPage(ChromeDriver chromeDriver) {
        this.chromeDriver = chromeDriver;
    }

    public BookNewsPage clickToSlug(){
        WebElement elementByXPath = chromeDriver.findElementByXPath("/html/body/div[1]/div/main/div/div[2]/div[1]/div[1]/a/img");
        elementByXPath.click();
        return this;
    }

    @Override
    public BookNewsPage callPage() {
        chromeDriver.get(url);
        return this;
    }

    @Override
    public BookNewsPage backToPage() {
        chromeDriver.navigate().to(url);
        return this;
    }

    @SneakyThrows
    @Override
    public BookNewsPage pause() {
        Thread.sleep(3000);
        return this;
    }

    @Override
    public void quit() {
        chromeDriver.quit();
    }
}
