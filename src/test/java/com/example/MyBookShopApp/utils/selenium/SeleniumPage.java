package com.example.MyBookShopApp.utils.selenium;

import lombok.SneakyThrows;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public interface SeleniumPage {
    Object callPage();

    default Object clickToCart(ChromeDriver chromeDriver){
        WebElement webElement = chromeDriver.findElementByXPath("/html/body/header/div[1]/div/div/div[3]/div/a[2]");
        webElement.click();
        return this;
    }

    default Object clickToPostoponed(ChromeDriver chromeDriver){
        WebElement webElement = chromeDriver.findElementByXPath("/html/body/header/div[1]/div/div/div[3]/div/a[1]");
        webElement.click();
        return this;
    }


    default Object clickToSignIn(ChromeDriver chromeDriver){
        WebElement webElement = chromeDriver.findElementByXPath("/html/body/header/div[1]/div/div/div[3]/div/a[3]");
        webElement.click();
        return this;
    }

    Object backToPage();
    Object pause();

    void quit();
}
