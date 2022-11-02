package com.coinlibrary.backend.service;

import com.coinlibrary.backend.model.Edition;
import com.coinlibrary.backend.repository.EditionRepository;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@Service
public class WikipediaService {

    private final static String WIKIPEDIA_EURO_COINS_URL = "https://de.wikipedia.org/wiki/Eurom%C3%BCnzen";
    @Autowired
    private EditionRepository editionRepository;
    private WebDriver webDriver;

    public void run() throws MalformedURLException {
        init();
        getEditions(getEuroCountryLinks());
        quit();
    }

    private void init() throws MalformedURLException {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setHeadless(true);
        webDriver = new RemoteWebDriver(new URL("http://coin_library_selenium:4444"), chromeOptions);
    }

    private List<String> getEuroCountryLinks() {
        webDriver.get(WIKIPEDIA_EURO_COINS_URL);

        WebElement column = webDriver.findElement(By.className("column-multiple"));
        List<WebElement> countryUrls = column.findElements(By.tagName("li"));

        return countryUrls.stream()
                          .map((elem) -> elem.findElement(By.tagName("a"))
                                             .getAttribute("href"))
                          .toList();
    }

    private String getCountryNameFromUrl(String url) {
        String[] urlParts = url.split("/");

        return urlParts[urlParts.length - 1].replace("_", " ")
                                            .replace("%C3%BC", "ü")
                                            .replace("%C3%A4", "ä")
                                            .replace("%C3%B6", "ö");
    }

    private void getEditions(List<String> urls) {
        urls.forEach(this::getCountryEditions);
    }

    private void getCountryEditions(String countryUrl) {
        webDriver.get(countryUrl);

        List<WebElement> editions = webDriver.findElements(By.partialLinkText("Prägeserie"));

        if (editions.isEmpty()) {
            Edition edition = new Edition();
            edition.setCountry(getCountryNameFromUrl(countryUrl));
            edition.setEdition(1);
            editionRepository.save(edition);
        } else {
            extractPrägeserieEdition(editions, getCountryNameFromUrl(countryUrl));
        }
    }

    private void extractPrägeserieEdition(List<WebElement> editions, String countryName) {
        for (WebElement edition : editions) {
            String[] valueParts = edition.getText()
                                         .split(" ");

            Edition edition1 = new Edition();
            edition1.setCountry(countryName);

            switch (valueParts[1]) {
                case "Erste", "erste" -> edition1.setEdition(1);
                case "Zweite", "zweite" -> edition1.setEdition(2);
                case "Dritte", "dritte" -> edition1.setEdition(3);
                case "Vierte", "vierte" -> edition1.setEdition(4);
                case "Fünfte", "fünfte" -> edition1.setEdition(5);
            }

            String[] years = valueParts[3].split("–");
            if (years.length > 1) {
                edition1.setYear_from(Integer.parseInt(years[0].substring(1)));
                edition1.setYear_to(Integer.parseInt(years[1].substring(0, years[0].length() - 1)));
            } else if (years[0].length() == 6) {
                edition1.setYear_from(Integer.parseInt(years[0].substring(1, years[0].length() - 1)));
                edition1.setYear_to(Integer.parseInt(years[0].substring(1, years[0].length() - 1)));
            } else {
                edition1.setYear_from(Integer.parseInt(valueParts[4].substring(0, valueParts[4].length() - 1)));
                editionRepository.save(edition1);
                break;
            }

            editionRepository.save(edition1);
        }
    }

    private void quit() {
        webDriver.quit();
    }
}
