package com.coinlibrary.backend.service;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class SeleniumExtraction {

    WebDriver webDriver;

    void init() throws MalformedURLException {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setHeadless(true);
        webDriver = new RemoteWebDriver(new URL("http://coin_library_selenium:4444"), chromeOptions);
    }

    void quit() {
        webDriver.quit();
    }

}
