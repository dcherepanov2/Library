package com.example.MyBookShopApp.utils.selenium;

import lombok.SneakyThrows;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class AuthorChapterPage implements SeleniumPage{

    private final ChromeDriver chromeDriver;

    private final String url = "http://localhost:8080/authors";

    public AuthorChapterPage(ChromeDriver chromeDriver) {
        this.chromeDriver = chromeDriver;
    }

    @Override
    public AuthorChapterPage callPage() {
        chromeDriver.get(url);
        return this;
    }

    public AuthorChapterPage clickToLetter(){
        WebElement elementByXPath = chromeDriver.findElementByXPath("/html/body/div/div/main/div/nobr[2]/a");
        elementByXPath.click();
        return this;
    }

    public AuthorChapterPage clickToSlug(){
        WebElement elementByXPath = chromeDriver.findElementByXPath("/html/body/div/div/main/div/div[1]/div[1]/div/a");
        elementByXPath.click();
        return this;
    }

    @Override
    public AuthorChapterPage backToPage() {
        chromeDriver.navigate().to(url);
        return this;
    }

    @Override
    @SneakyThrows
    public AuthorChapterPage pause() {
        Thread.sleep(3000);
        return this;
    }

    @Override
    public void quit() {
        chromeDriver.quit();
    }
}
