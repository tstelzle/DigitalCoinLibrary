package com.coinlibrary.backend.component;

import com.coinlibrary.backend.model.Coin;
import com.coinlibrary.backend.model.Edition;
import com.coinlibrary.backend.repository.CoinRepository;
import com.coinlibrary.backend.repository.EditionRepository;
import com.coinlibrary.backend.service.CoinService;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@Slf4j
public class EcbComponent extends SeleniumExtraction {
    private final CoinRepository<Coin, Long> coinRepository;

    private final String baseLink = "https://www.ecb.europa.eu/euro/coins/html/";
    private final String baseLinkEnd = ".de.html";
    private final String twoEuroCoins = "https://www.ecb.europa.eu/euro/coins/2euro/html/index.de.html";
    private final String SPECIAL_COIN_BASE_LINK = "https://www.ecb.europa.eu/euro/coins/comm/html/comm_%d.de.html";
    private final String BASE_LINK_SPECIAL_COINS = "https://www.ecb.europa.eu";
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
    private final Map<String, String> countryAbbreviations = new HashMap<>();
    private final CoinService coinService;
    private final EditionRepository<Edition, Integer> editionRepository;

    @Autowired
    public EcbComponent(CoinService coinService, EditionRepository<Edition, Integer> editionRepository,
                        CoinRepository<Coin, Long> coinRepository) {
        this.coinService = coinService;
        this.editionRepository = editionRepository;
        this.coinRepository = coinRepository;
    }

    public void run() {
        log.info("Starting ECB extraction.");
        updateCountryAbbreviations();
        init();
        start();
        quit();
    }

    private void updateCountryAbbreviations() {
        countryAbbreviations.put("Spain", "es");
        countryAbbreviations.put("Slovenia", "sl");
        countryAbbreviations.put("Portugal", "pt");
        countryAbbreviations.put("Netherlands", "nl");
        countryAbbreviations.put("Nederland", "nl");
        countryAbbreviations.put("Luxembourg", "lu");
        countryAbbreviations.put("Italy", "it");
        countryAbbreviations.put("Ireland", "ie");
        countryAbbreviations.put("Greece", "gr");
        countryAbbreviations.put("Germany", "de");
        countryAbbreviations.put("France", "fr");
        countryAbbreviations.put("Finland", "fi");
        countryAbbreviations.put("Belgium", "be");
        countryAbbreviations.put("Austria", "at");
        countryAbbreviations.put("Cyprus", "cy");
        countryAbbreviations.put("Malta", "mt");
        countryAbbreviations.put("Slovakia", "sk");
        countryAbbreviations.put("Estonia", "et");
        countryAbbreviations.put("San_Marino", "sm");
        countryAbbreviations.put("Latvia", "lv");
        countryAbbreviations.put("Lithuania", "lt");
        countryAbbreviations.put("SanMarino", "sm");
    }

    private void start() {
        extractCountryAbbreviations();
        updateCoinImages();
        getSpecialCoins();
        getSpecialCoinsAfter2020();
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

    private void getSpecialCoinsAfter2020() {
        for (int i = 2020; i <= 2023; i++) {
            String specialCoinLink = String.format(SPECIAL_COIN_BASE_LINK, i);
            getWebDriver().get(specialCoinLink);
            List<WebElement> boxElements = getWebDriver().findElement(By.className("boxes")).findElements(By.className("box"));

            for (WebElement boxElement : boxElements) {
                List<String> pictureElements = boxElement.findElements(By.tagName("span")).stream().filter(v -> v.getAttribute("class").equals("coin-cropper loaded -attribution")).map(v -> v.getAttribute("data-image")).map(v -> String.format("%s%s", BASE_LINK_SPECIAL_COINS, v)).toList();
                String country = boxElement.findElement(By.tagName("h3")).getText().replaceAll("\\s", "");
                String name = boxElement.findElements(By.tagName("p")).get(0).getText().replace("Anlass: ", "");
                // TODO: Coin: 25. Jahrestag der Mitgliedschaft von Großherzog Henri im Internationalen Olympischen Komitee is not inside p-tag

                Coin coin = new Coin();
                coin.setSpecial(true);
                coin.setName(name);
                coin.setSize(200);
                coin.setYear(i);

                if (pictureElements.isEmpty()) {
                    pictureElements = boxElement.findElements(By.tagName("div")).get(0).findElements(By.tagName("picture")).stream().map(v -> v.findElement(By.tagName("img")).getAttribute("src")).toList();
                    if (pictureElements.isEmpty()) {
                        log.info("No Pictures.");
                    }
                }

                if (country.equals("Euro-Länder")) {
                    if (name.equals("35 Jahre Erasmus-Programm")) {
                        List<String> countries = List.of("be", "de", "et", "fi", "fr", "gr", "it", "ie", "lv", "lt", "lu", "mt", "nl", "at", "pt", "sk", "sl", "es", "cy");
                        for (String erasmusCountry : countries) {
                            coin = new Coin();
                            coin.setSpecial(true);
                            coin.setName(name);
                            coin.setSize(200);
                            coin.setYear(i);
                            Optional<Edition> optionalEdition = editionRepository.findByCountryAndEdition(erasmusCountry, 0);
                            if (erasmusCountry.equals("de")) {
                                coin.setImagePath(pictureElements.get(0));
                            } else {
                                coin.setImagePath(null);
                            }
                            if (optionalEdition.isPresent()) {
                                coin.setEdition(optionalEdition.get());
                                coinService.updateOrInsert(coin);
                            }
                        }
                    } else {
                        log.error("Not Parseable Coin: {}", name);
                    }
                } else {
                    coin.setImagePath(pictureElements.get(0));
                    Optional<Edition> optionalEdition = editionRepository.findByCountryAndEdition(countryAbbreviations.get(country), 0);
                    if (optionalEdition.isPresent()) {
                        coin.setEdition(optionalEdition.get());
                        coinService.updateOrInsert(coin);
                    } else {
                        log.info("Edition not found.");
                    }
                }
            }
        }
    }

    private void getSpecialCoins() {
        for (int i = 2004; i < 2020; i++) {
            getWebDriver().get(String.format(SPECIAL_COIN_BASE_LINK, i));
            List<WebElement> boxElements = getWebDriver().findElement(By.className("boxes")).findElements(By.className("box"));

            for (WebElement boxElement : boxElements) {
                List<String> pictureElements = boxElement.findElements(By.tagName("div")).get(0).findElements(By.tagName("picture")).stream().map(v -> v.findElement(By.tagName("img")).getAttribute("src")).toList();
                String country = boxElement.findElement(By.tagName("h3")).getText().replaceAll("\\s", "");
                String name = boxElement.findElements(By.tagName("p")).get(0).getText().replace("Anlass der Ausgabe: ", "");

                Coin coin = new Coin();
                coin.setSpecial(true);
                coin.setName(name);
                coin.setSize(200);
                coin.setYear(i);

                if (pictureElements.isEmpty()) {
                    log.info("No Pictures.");
                } else if (country.equals("LänderdesEuroraums")) {
                    for (String pictureElement : pictureElements) {
                        coin.setImagePath(pictureElement);

                        String countryAbbreviation = null;

                        for (Map.Entry<String, String> entry : countryAbbreviations.entrySet()) {
                            if (pictureElement.contains(entry.getKey())) {
                                countryAbbreviation = entry.getValue();
                                break;
                            }
                        }

                        if (countryAbbreviation != null) {
                            Optional<Edition> optionalEdition = editionRepository.findByCountryAndEdition(countryAbbreviation, 0);
                            if (optionalEdition.isPresent()) {
                                coin.setEdition(optionalEdition.get());
                                coinService.updateOrInsert(coin);
                            }
                        } else {
                            log.info("Country in pictureElement String not found: {}", pictureElement);
                        }
                    }
                } else {
                    coin.setImagePath(pictureElements.get(0));
                    Optional<Edition> optionalEdition = editionRepository.findByCountryAndEdition(countryAbbreviations.get(country), 0);
                    if (optionalEdition.isPresent()) {
                        coin.setEdition(optionalEdition.get());
                        coinService.updateOrInsert(coin);
                    } else {
                        log.info("Edition not found.");
                    }
                }

            }

        }
    }

    private void updateCoinImages() {
        for (Map.Entry<String, String> countryAbbreviation : countryAbbreviations.entrySet()) {
            getWebDriver().get(baseLink + countryAbbreviation.getValue() + baseLinkEnd);

            List<WebElement> boxElements = getWebDriver().findElement(By.className("boxes")).findElements(By.className("box"));

            for (WebElement boxElement : boxElements) {
                List<WebElement> pictureElements = boxElement.findElements(By.tagName("div")).get(0).findElements(By.tagName("picture"));

                for (int index=0; index < pictureElements.size(); index++) {
                    WebElement pictureElement = pictureElements.get(index);
                    int editionIndex = index + 1;

                    if (countryAbbreviation.getValue().equals("be") && editionIndex >= 2) {
                        editionIndex += 1;
                    }

                    Edition edition = null;
                    Optional<Edition> optionalEdition = editionRepository.findByCountryAndEdition(countryAbbreviation.getValue(), editionIndex);
                    if (optionalEdition.isPresent()) {
                        edition = optionalEdition.get();
                    }

                    String pictureUrl = pictureElement.findElement(By.tagName("img")).getAttribute("src");
                    if (edition == null) {
                        log.error("Edition not found.");
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
                        Optional<Coin> optionalCoin = coinRepository.findByEditionAndSizeAndSpecial(edition, coinValueInteger, false);
                        if (optionalCoin.isPresent()) {
                            Coin coin = optionalCoin.get();
                            coin.setImagePath(pictureUrl);
                            coinService.updateOrInsert(coin);
                        } else {
                            log.error("Coin is null: {};{}", edition.getCountry(), coinValueInteger);
                        }
                    } else {
                        log.error("Coin value not found: {}", pictureUrl);
                    }
                }
            }
        }
    }
}
