package com.example.MyBookShopApp.utils.selenium;

import lombok.SneakyThrows;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class GenrePage implements SeleniumPage{

    private String url = "http://localhost:8080/genres/genre-kQV-940";

    private final ChromeDriver chromeDriver;

    public GenrePage(ChromeDriver chromeDriver) {
        this.chromeDriver = chromeDriver;
    }

    @Override
    public GenrePage callPage() {
        chromeDriver.get(url);
        return this;
    }

    public GenrePage clickToBook(){
        WebElement elementByXPath = chromeDriver.findElementByXPath("/html/body/div/div/main/div/div/div[1]/div[1]/a/img");
        elementByXPath.click();
        return this;
    }

    @Override
    public GenrePage backToPage() {
        chromeDriver.navigate().to(url);
        return this;
    }

    @SneakyThrows
    @Override
    public GenrePage pause() {
        Thread.sleep(3000);
        return this;
    }

    @Override
    public void quit() {
        chromeDriver.quit();
    }
}
