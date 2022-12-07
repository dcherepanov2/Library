package com.example.MyBookShopApp.utils.selenium;

import lombok.SneakyThrows;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BookPopularChapterPage implements SeleniumPage{
    private final String url = "http://localhost:8080/books/popular";
    private final ChromeDriver chromeDriver;

    public BookPopularChapterPage(ChromeDriver chromeDriver) {
        this.chromeDriver = chromeDriver;
    }

    public BookPopularChapterPage clickToSlug(){
        WebElement elementByXPath = chromeDriver.findElementByXPath("/html/body/div/div/main/div/div[2]/div[1]/div[1]/a/img");
        elementByXPath.click();
        return this;
    }

    @Override
    public BookPopularChapterPage callPage() {
        chromeDriver.get(url);
        return this;
    }

    @Override
    public BookPopularChapterPage backToPage() {
        chromeDriver.navigate().to(url);
        return this;
    }

    @Override
    @SneakyThrows
    public BookPopularChapterPage pause() {
        Thread.sleep(3000);
        return this;
    }

    @Override
    public void quit() {
        chromeDriver.quit();
    }
}
