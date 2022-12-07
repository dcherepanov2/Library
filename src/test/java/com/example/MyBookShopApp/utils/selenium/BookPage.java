package com.example.MyBookShopApp.utils.selenium;

import lombok.SneakyThrows;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class BookPage implements SeleniumPage{

    private final String bookSlugUrls = "http://localhost:8080/books/book-wtr-148";
    private final ChromeDriver chromeDriver;

    public BookPage(ChromeDriver chromeDriver) {
        this.chromeDriver = chromeDriver;
    }

    @Override
    public BookPage callPage(){
        chromeDriver.get(bookSlugUrls);
        assertFalse(chromeDriver.getPageSource().contains("404"));
        return this;
    }

    @Override
    public BookPage backToPage() {
        chromeDriver.navigate().back();
        return this;
    }

    public BookPage clickToAuthorLink(){
        WebElement elementByXPath = chromeDriver.findElementByXPath("/html/body/div/div/main/div/div[1]/div[2]/div[1]/div[1]/a");
        elementByXPath.click();
        return this;
    }

    @SneakyThrows
    public BookPage pause(){
        Thread.sleep(3000);
        return this;
    }
    @Override
    public void quit() {
        chromeDriver.quit();
    }
}
