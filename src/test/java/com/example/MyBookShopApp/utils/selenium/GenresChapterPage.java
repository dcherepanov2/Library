package com.example.MyBookShopApp.utils.selenium;

import lombok.SneakyThrows;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class GenresChapterPage implements SeleniumPage{
    private final String url = "http://localhost:8080/genres";

    private final ChromeDriver chromeDriver;

    public GenresChapterPage(ChromeDriver chromeDriver) {
        this.chromeDriver = chromeDriver;
    }

    @Override
    public GenresChapterPage callPage() {
        chromeDriver.get(url);
        return this;
    }

    public GenresChapterPage clickToSlugGenre(){
        WebElement elementByXPath = chromeDriver.findElementByXPath("/html/body/div/div/main/div/div/div/nobr[54]/a");
        elementByXPath.click();
        return this;
    }

    @Override
    public GenresChapterPage backToPage() {
        chromeDriver.navigate().to(url);
        return this;
    }

    @Override
    @SneakyThrows
    public GenresChapterPage pause() {
        Thread.sleep(3000);
        return this;
    }

    @Override
    public void quit() {
        chromeDriver.quit();
    }
}
