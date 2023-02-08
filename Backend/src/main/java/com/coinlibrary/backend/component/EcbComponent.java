package com.coinlibrary.backend.component;

import com.coinlibrary.backend.model.Coin;
import com.coinlibrary.backend.model.Edition;
import com.coinlibrary.backend.repository.CoinDao;
import com.coinlibrary.backend.repository.EditionDao;
import com.coinlibrary.backend.service.CoinService;
import com.coinlibrary.backend.service.EditionService;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Slf4j
public class EcbComponent extends SeleniumExtraction {

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

    private final CoinDao coinDao;
    private final CoinService coinService;
    private final EditionService editionService;
    private final Map<String, String> countryAbbreviations = new HashMap<>();
    private final EditionDao editionDao;

    @Autowired
    public EcbComponent(CoinService coinService, CoinDao coinDao, EditionService editionService, EditionDao editionDao) {
        this.coinDao = coinDao;
        this.coinService = coinService;
        this.editionService = editionService;
        this.editionDao = editionDao;
    }

    public void run() {
        log.info("Starting ECB extraction.");
        init();
        start();
        quit();
    }

    private void start() {
        extractCountryAbbreviations();
        updateCoinImages();
    }

    private void extractCountryAbbreviations() {
        getWebDriver().get(twoEuroCoins);

        List<WebElement> contentBoxList = getWebDriver().findElement(By.className("boxes"))
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
            getWebDriver().get(baseLink + countryAbbreviation.getValue() + baseLinkEnd);

            List<WebElement> boxElements = getWebDriver().findElement(By.className("boxes")).findElements(By.className("box"));

            for (WebElement boxElement : boxElements) {
                List<WebElement> pictureElements = boxElement.findElements(By.tagName("div")).get(0).findElements(By.tagName("picture"));

                for (WebElement pictureElement : pictureElements) {
                    String pictureUrl = pictureElement.findElement(By.tagName("img")).getAttribute("src");
                    List<Integer> yearList = extractYearsFromText(pictureUrl);
                    Edition edition = null;
                    if (yearList.size() > 1) {
                        log.debug("Too many years.");
                        continue;
                    }
                    else if (yearList.size() == 0) {
                        Optional<Edition> optionalEdition = editionDao.findByCountryAndEdition(countryAbbreviation.getValue(), 1);
                        if (optionalEdition.isPresent()) {
                            edition = optionalEdition.get();
                        }
                    }
                    else {
                        Optional<Edition> optionalEdition = editionDao.findByCountryAndYearFromGreaterThanAndYearToLessThan(countryAbbreviation.getValue(), yearList.get(0), yearList.get(0));
                        if (optionalEdition.isPresent()) {
                            edition = optionalEdition.get();
                        }
                    }

                    if (edition == null) {
                        log.debug("Edition not found.");
                        continue;
                    }

                    int coinValueInteger = -1;
                    for (Map.Entry<String, Integer> coinValue : coinValues.entrySet()) {
                        if (pictureUrl.contains(coinValue.getKey())) {
                            coinValueInteger = coinValue.getValue();
                            break;
                        }
                    }

                    if (coinValueInteger != -1) {
                        Optional<Coin> optionalCoin = coinDao.findByEditionAndSizeAndSpecial(edition, coinValueInteger, false);
                        if (optionalCoin.isPresent()) {
                            Coin coin = optionalCoin.get();
                            coin.setImagePath(pictureUrl);
                            coinService.updateOrInsertCoin(coin);
                        }
                        else {
                            log.info("Coin is null: {};{}", edition.getCountry(), coinValueInteger);
                        }
                    }
                    else {
                        log.info("Coin value not found: {}", pictureUrl);
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
