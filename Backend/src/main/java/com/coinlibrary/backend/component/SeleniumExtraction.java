package com.coinlibrary.backend.component;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Value;

import java.net.MalformedURLException;
import java.net.URL;

@Slf4j
public abstract class SeleniumExtraction {

    @Getter
    private WebDriver webDriver;
    @Value("${app.selenium.url}")
    private String seleniumUrl;
    @Value("${app.selenium.port}")
    private String seleniumPort;

    public void setWebDriver(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    void init() {
        try {
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--headless=new");
            setWebDriver(new RemoteWebDriver(new URL(seleniumUrl + ":" + seleniumPort), chromeOptions));
        } catch (MalformedURLException ex) {
            log.info(ex.getMessage(), ex);
        }
    }

    void quit() {
        webDriver.quit();
    }

    public abstract void run();

}
