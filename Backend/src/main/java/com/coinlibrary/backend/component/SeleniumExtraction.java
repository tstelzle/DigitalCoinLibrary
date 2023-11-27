package com.coinlibrary.backend.component;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Value;

import java.net.MalformedURLException;
import java.net.URL;

@Slf4j
public abstract class SeleniumExtraction {

    private WebDriver webDriver;
    @Value("${app.selenium.url}")
    private String seleniumUrl;
    @Value("${app.selenium.port}")
    private String seleniumPort;

    public WebDriver getWebDriver() {
        return webDriver;
    }

    public void setWebDriver(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    void init() {
        try {
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.setHeadless(true);
            setWebDriver(new RemoteWebDriver(new URL(seleniumUrl + ":" + seleniumPort + "/wd/hub"), chromeOptions));
        } catch (MalformedURLException ex) {
            log.info(ex.getMessage(), ex);
        }
    }

    void quit() {
        webDriver.quit();
    }

    public abstract void run();

}
