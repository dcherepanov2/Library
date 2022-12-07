package com.example.MyBookShopApp.utils.selenium;

import lombok.SneakyThrows;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class MainPage implements SeleniumPage {
    private final String url = "http://localhost:8080/books/book-wtr-148";
    private final ChromeDriver chromeDriver ;

    public MainPage(ChromeDriver chromeDriver) {
        this.chromeDriver = chromeDriver;
    }


    @Override
    public MainPage callPage() {
        chromeDriver.get(url);
        return this;
    }

    @Override
    public MainPage backToPage() {
        chromeDriver.navigate().back();
        return this;
    }

    public MainPage clickToMain(){
        WebElement webElement = chromeDriver.findElementById("menu-item-main");
        webElement.click();
        return this;
    }

    public MainPage clickToSlider(){
        WebElement webElement = chromeDriver.findElementByXPath("/html/body/div/div/main/div/div[1]/div[2]/div[2]/div/button[2]");
        webElement.click();
        return this;
    }

    public MainPage clickToTag(){
        WebElement webElement = chromeDriver.findElementByXPath("/html/body/div/div/main/nobr[1]/a");
        webElement.click();
        return this;
    }

    public MainPage clickToSignIn(){
        WebElement webElement = chromeDriver.findElementByXPath("/html/body/header/div[1]/div/div/div[3]/div/a[3]");
        webElement.click();
        return this;
    }

    public MainPage clickToCart(){
        WebElement webElement = chromeDriver.findElementByXPath("/html/body/header/div[1]/div/div/div[3]/div/a[2]");
        webElement.click();
        return this;
    }

    public MainPage clickToPostoponed(){
        WebElement webElement = chromeDriver.findElementByXPath("/html/body/header/div[1]/div/div/div[3]/div/a[1]");
        webElement.click();
        return this;
    }

    @SneakyThrows
    public MainPage pause() {
        Thread.sleep(2000);
        return this;
    }

    @Override
    public void quit() {
        chromeDriver.quit();
    }
}
