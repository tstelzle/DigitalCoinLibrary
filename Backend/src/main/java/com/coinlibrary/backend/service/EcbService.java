package com.coinlibrary.backend.service;

import com.coinlibrary.backend.model.Coin;
import com.coinlibrary.backend.model.Edition;
import com.coinlibrary.backend.repository.CoinDao;
import com.coinlibrary.backend.repository.EditionDao;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class EcbService extends SeleniumExtraction {

    private final String baseLink = "https://www.ecb.europa.eu/euro/coins/html/";
    private final String baseLinkEnd = ".en.html";
    private final String twoEuroCoins = "https://www.ecb.europa.eu/euro/coins/2euro/html/index.en.html";
    private final Map<String, Integer> coinValues = Map.ofEntries(
            Map.entry("1cent", 1),
            Map.entry("2cent", 2),
            Map.entry("5cent", 5),
            Map.entry("10cent", 10),
            Map.entry("20cent", 20),
            Map.entry("50cent", 50),
            Map.entry("1Euro", 100),
            Map.entry("1euro", 100),
            Map.entry("1e", 100),
            Map.entry("2Euro", 200),
            Map.entry("2euro", 200),
            Map.entry("2e", 200)
    );

    @Autowired
    private CoinDao coinDao;
    @Autowired
    private EditionService editionService;
    private final Map<String, String> countryAbbreviations = new HashMap<>();
    @Autowired
    private EditionDao editionDao;

    public void run() throws MalformedURLException {
        init();
        start();
        quit();
    }

    private void start() {
        extractCountryAbbreviations();
        updateCoinImages();
    }

    private void extractCountryAbbreviations() {
        webDriver.get(twoEuroCoins);

        List<WebElement> contentBoxList = webDriver.findElement(By.className("boxes"))
                                                   .findElements(By.className("content-box"));

        for (WebElement contentBox : contentBoxList) {
            String[] urlSplit = contentBox.findElement(By.tagName("a"))
                                          .getAttribute("href")
                                          .split("/");
            countryAbbreviations.put(contentBox.findElement(By.tagName("h3"))
                                               .getText(), urlSplit[urlSplit.length - 1].split("[.]")[0]);
        }
    }

    private void updateCoinImages() {
        for (Map.Entry<String, String> countryAbbreviation : countryAbbreviations.entrySet()) {
            webDriver.get(baseLink + countryAbbreviation.getValue() + baseLinkEnd);

            List<WebElement> boxElements = webDriver.findElement(By.className("boxes")).findElements(By.className("box"));

            for (WebElement boxElement : boxElements) {
                List<WebElement> pictureElements = boxElement.findElements(By.tagName("div")).get(0).findElements(By.tagName("picture"));

                for (WebElement pictureElement : pictureElements) {
                    String pictureUrl = pictureElement.findElement(By.tagName("img")).getAttribute("src");
                    List<Integer> yearList = extractYearsFromText(pictureUrl);
                    Edition edition;
                    if (yearList.size() > 1) {
                        System.out.println("Too Many Years;");
                        continue;
                    }
                    else if (yearList.size() == 0) {
                        edition = editionDao.findByCountryAndEdition(countryAbbreviation.getValue(), 1);
                    }
                    else {
                        List<Edition> editionList = editionDao.findByCountry(countryAbbreviation.getValue());
                        edition = editionService.getEditionByYear(editionList, yearList.get(0));
                    }

                    int coinValueInteger = -1;
                    for (Map.Entry<String, Integer> coinValue : coinValues.entrySet()) {
                        if (pictureUrl.contains(coinValue.getKey())) {
                            coinValueInteger = coinValue.getValue();
                            break;
                        }
                    }

                    if (coinValueInteger != -1) {
                        Coin coin = coinDao.findByEditionAndSize(edition, coinValueInteger);
                        if (coin != null) {
                            coin.setImagePath(pictureUrl);
                            coinDao.save(coin);
                        }
                        else {
                            System.out.printf("Coin Is Null: %s;%d", edition.getCountry(), coinValueInteger);
                        }
                    }
                    else {
                        System.out.printf("Coin Value Not Found: %s", pictureUrl);
                    }
                }
            }
        }
    }

    private List<Integer> extractYearsFromText(String text) {
        Pattern integerPattern = Pattern.compile("-?\\d+");
        Matcher matcher = integerPattern.matcher(text);

        List<Integer> integerList = new ArrayList<>();

        while (matcher.find()) {
            int year = Integer.parseInt(matcher.group());
            if (year >= 1999) {
                integerList.add(year);
            }
        }

        return integerList;
    }
}
