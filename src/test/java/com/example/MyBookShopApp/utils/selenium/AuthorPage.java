package com.example.MyBookShopApp.utils.selenium;

import lombok.SneakyThrows;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class AuthorPage implements SeleniumPage{

    private final String url = "http://localhost:8080/authors/book-jbx-004";

    private final ChromeDriver chromeDriver;

    public AuthorPage(ChromeDriver chromeDriver) {
        this.chromeDriver = chromeDriver;
    }

    @Override
    public AuthorPage callPage() {
        chromeDriver.get(url);
        return this;
    }

    @Override
    public AuthorPage backToPage() {
        chromeDriver.navigate().back();
        return this;
    }

    public AuthorPage getAllBooksAuthor(){
        WebElement elementByXPath = chromeDriver.findElementByXPath("/html/body/div/div/main/footer/a");
        elementByXPath.click();
        return this;
    }

    @Override
    @SneakyThrows
    public Object pause() {
        Thread.sleep(3000);
        return this;
    }

    @Override
    public void quit() {
        chromeDriver.quit();
    }
}
