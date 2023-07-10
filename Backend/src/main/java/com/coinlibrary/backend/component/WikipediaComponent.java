package com.coinlibrary.backend.component;

import com.coinlibrary.backend.model.Coin;
import com.coinlibrary.backend.model.Edition;
import com.coinlibrary.backend.repository.EditionRepository;
import com.coinlibrary.backend.service.CoinService;
import com.coinlibrary.backend.service.EditionService;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Component
@Slf4j
public class WikipediaComponent extends SeleniumExtraction {

    private final static String WIKIPEDIA_EURO_COINS_URL = "https://de.wikipedia.org/wiki/Eurom%C3%BCnzen";
    public final Map<String, String> countryAbbreviations = Map.ofEntries(
            Map.entry("Spanische Euromünzen", "es"),
            Map.entry("Griechische Euromünzen", "gr"),
            Map.entry("Lettische Euromünzen", "lv"),
            Map.entry("Andorranische Euromünzen", "ad"),
            Map.entry("Belgische Euromünzen", "be"),
            Map.entry("Zyprische Euromünzen", "cy"),
            Map.entry("Portugiesische Euromünzen", "pt"),
            Map.entry("Vatikanische Euromünzen", "va"),
            Map.entry("Maltesische Euromünzen", "mt"),
            Map.entry("Österreichische Euromünzen", "at"),
            Map.entry("Niederländische Euromünzen", "nl"),
            Map.entry("Monegassische Euromünzen", "mo"),
            Map.entry("Irische Euromünzen", "ie"),
            Map.entry("Luxemburgische Euromünzen", "lu"),
            Map.entry("Finnische Euromünzen", "fi"),
            Map.entry("Italienische Euromünzen", "it"),
            Map.entry("San-marinesische Euromünzen", "sm"),
            Map.entry("Slowakische Euromünzen", "sk"),
            Map.entry("Slowenische Euromünzen", "sl"),
            Map.entry("Französische Euromünzen", "fr"),
            Map.entry("Litauische Euromünzen", "lt"),
            Map.entry("Deutsche Euromünzen", "de"),
            Map.entry("Estnische Euromünzen", "et"),
            Map.entry("Kroatische Euromünzen", "hr")
    );


    private final EditionRepository<Edition, Integer> editionRepository;
    private final CoinService coinService;
    private final EditionService editionService;

    @Autowired
    public WikipediaComponent(CoinService coinService, EditionRepository editionRepository, EditionService editionService) {
        this.editionRepository = editionRepository;
        this.coinService = coinService;
        this.editionService = editionService;
    }

    public void run() {
        log.info("Starting Wikipedia extraction.");
        init();
        getEditions(getEuroCountryLinks());
        addMissingYearsForEdition();
//        getSpecialCoins(getEuroCountryLinks());
        quit();
    }

    private List<String> getEuroCountryLinks() {
        getWebDriver().get(WIKIPEDIA_EURO_COINS_URL);

        WebElement column = getWebDriver().findElement(By.className("column-multiple"));
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
                                            .replace("%C3%96", "Ö")
                                            .replace("%C3%B6", "ö");
    }

    private void getEditions(List<String> urls) {
        urls.forEach(this::getCountryEditions);
    }

    private void getCountryEditions(String countryUrl) {
        getWebDriver().get(countryUrl);

        List<WebElement> editions = getWebDriver().findElements(By.partialLinkText("Prägeserie"));

        Edition specialEdition = new Edition();
        specialEdition.setCountry(countryAbbreviations.get(getCountryNameFromUrl(countryUrl)));
        specialEdition.setEdition(0);
        editionService.updateOrInsert(specialEdition);

        if (editions.isEmpty()) {
            Edition edition = new Edition();
            edition.setCountry(countryAbbreviations.get(getCountryNameFromUrl(countryUrl)));
            edition.setEdition(1);
            editionService.updateOrInsert(edition);
        } else {
            extractPrägeserieEdition(editions, getCountryNameFromUrl(countryUrl));
        }
    }

    private void extractPrägeserieEdition(List<WebElement> editions, String countryName) {
        for (WebElement edition : editions) {
            String[] valueParts = edition.getText()
                    .split(" ");

            Edition edition1 = new Edition();
            edition1.setCountry(countryAbbreviations.get(countryName));

            switch (valueParts[1]) {
                case "Erste", "erste" -> edition1.setEdition(1);
                case "Zweite", "zweite" -> edition1.setEdition(2);
                case "Dritte", "dritte" -> edition1.setEdition(3);
                case "Vierte", "vierte" -> edition1.setEdition(4);
                case "Fünfte", "fünfte" -> edition1.setEdition(5);
                case "Sechste", "sechste" -> edition1.setEdition(6);
                case "Siebte", "siebte" -> edition1.setEdition(7);
            }

            String[] years = valueParts[3].split("–");
            if (years.length > 1) {
                edition1.setYearFrom(Integer.parseInt(years[0].substring(1)));
                edition1.setYearTo(Integer.parseInt(years[1].substring(0, years[0].length() - 1)));
            } else if (years[0].length() == 6) {
                edition1.setYearFrom(Integer.parseInt(years[0].substring(1, years[0].length() - 1)));
                edition1.setYearTo(Integer.parseInt(years[0].substring(1, years[0].length() - 1)));
            } else {
                edition1.setYearFrom(Integer.parseInt(valueParts[4].substring(0, valueParts[4].length() - 1)));
                edition1.setYearTo(2100);
                editionService.updateOrInsert(edition1);
                break;
            }

            editionService.updateOrInsert(edition1);
        }
    }

    public void addMissingYearsForEdition() {
        for (String countryAbbreviation : countryAbbreviations.values()) {
            List<Edition> editionList = editionRepository.findByCountry(countryAbbreviation);

            for (Edition edition : editionList) {
                if (edition.getYearFrom() == 0) {
                    edition.setYearFrom(1800);
                }

                if (edition.getEdition() == 0) {
                    edition.setYearTo(2100);
                }

                if (edition.getYearTo() == 0 && edition.getEdition() != 0) {
                    Optional<Edition> optionalEdition = editionRepository.findByCountryAndEdition(countryAbbreviation, edition.getEdition() + 1);
                    if (optionalEdition.isPresent()) {
                        edition.setYearTo(optionalEdition.get().getYearFrom() - 1);
                    } else {
                        edition.setYearTo(2100);
                    }
                }
                editionService.updateOrInsert(edition);
            }
        }
    }

    private void getSpecialCoins(List<String> urls) {
        urls.forEach(this::getSpecialCoinsForCountry);
    }

    private void getSpecialCoinsForCountry(String countryUrl) {
        getWebDriver().get(countryUrl);

        boolean themaTable = false;

        WebElement specialCoinTable = null;

        List<WebElement> tables = getWebDriver().findElements(By.tagName("table"));

        for (WebElement table : tables) {
            List<WebElement> tableHeaders = table.findElements(By.tagName("th"));
            for (WebElement tableHeader : tableHeaders) {
                if (tableHeader.getText().equals("Anlass")) {
                    specialCoinTable = table;
                    break;
                } else if (tableHeader.getText().equals("Thema")) {
                    specialCoinTable = table;
                    themaTable = true;
                    break;
                }
            }

            if (specialCoinTable != null) {
                break;
            }
        }

        if (specialCoinTable == null) {
            log.info("SpecialCoinTable is null.");
            return;
        }

        List<WebElement> tableRows = specialCoinTable.findElements(By.tagName("tr"));
        tableRows.remove(0);
        if (themaTable) {
            tableRows.remove(1);
        }

        List<Edition> editionList = StreamSupport.stream(editionRepository.findAll().spliterator(), false)
                .filter(edition -> countryAbbreviations.get(getCountryNameFromUrl(countryUrl)).equals(edition.getCountry()))
                .filter(edition -> edition.getEdition() == 0)
                .toList();

        if (editionList.size() != 1) {
            log.info("Multiple Special Editions Found For Country");
            return;
        }

        Edition edition = editionList.get(0);


        for (WebElement tableRow : tableRows) {
            List<WebElement> tableRowEntries = tableRow.findElements(By.tagName("td"));

            // TODO set year

            if (tableRowEntries.size() > 2) {
                Coin specialCoin = new Coin();
                specialCoin.setEdition(edition);
                specialCoin.setSize(200);
                specialCoin.setSpecial(true);
                if (themaTable) {
                    try {
                        specialCoin.setImagePath(tableRowEntries.get(0).findElement(By.tagName("img")).getAttribute("src"));
                    } catch (Exception e) {
                        log.debug(e.getMessage(), "No picture for coin.");
                    }
                    specialCoin.setName(tableRowEntries.get(1).getText());
                } else {
                    try {
                        specialCoin.setImagePath(tableRowEntries.get(1).findElement(By.tagName("img")).getAttribute("src"));
                    } catch (Exception e) {
                        log.debug(e.getMessage(), "No picture for coin.");
                    }
                    specialCoin.setName(tableRowEntries.get(3).getText());
                }
                coinService.updateOrInsertCoin(specialCoin);
            }
        }
    }

}
