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
import java.util.List;
import java.util.Map;
import java.util.stream.StreamSupport;

@Service
public class WikipediaService extends SeleniumExtraction {

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
            Map.entry("Estnische Euromünzen", "et")
    );


    @Autowired
    private EditionDao editionDao;

    @Autowired
    private CoinDao coinDao;

    public void run() throws MalformedURLException {
        init();
        getEditions(getEuroCountryLinks());
        addMissingYearsForEdition();
        getSpecialCoins(getEuroCountryLinks());
        quit();
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
                                            .replace("%C3%96", "Ö")
                                            .replace("%C3%B6", "ö");
    }

    private void getEditions(List<String> urls) {
        urls.forEach(this::getCountryEditions);
    }

    private void getCountryEditions(String countryUrl) {
        webDriver.get(countryUrl);

        List<WebElement> editions = webDriver.findElements(By.partialLinkText("Prägeserie"));

        Edition specialEdition = new Edition();
        specialEdition.setCountry(getCountryNameFromUrl(countryUrl));
        specialEdition.setEdition(0);
        editionDao.save(specialEdition);

        if (editions.isEmpty()) {
            Edition edition = new Edition();
            edition.setCountry(countryAbbreviations.get(getCountryNameFromUrl(countryUrl)));
            edition.setEdition(1);
            editionDao.save(edition);
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
                editionDao.save(edition1);
                break;
            }

            editionDao.save(edition1);
        }
    }

    public void addMissingYearsForEdition() {
        for (String countryAbbreviation : countryAbbreviations.values()) {
            List<Edition> editionList = editionDao.findByCountry(countryAbbreviation);

            for (Edition edition : editionList) {
                if (edition.getYear_from() == 0) {
                    edition.setYear_from(1800);
                }

                if (edition.getYear_to() == 0) {
                    Edition nextEdition = editionDao.findByCountryAndEdition(countryAbbreviation, edition.getEdition() + 1);
                    if (nextEdition != null) {
                        edition.setYear_to(nextEdition.getYear_from() - 1);
                    } else {
                        edition.setYear_to(2100);
                    }
                }
                editionDao.save(edition);
            }
        }
    }

    private void getSpecialCoins(List<String> urls) {
        urls.forEach(this::getSpecialCoinsForCountry);
    }

    private void getSpecialCoinsForCountry(String countryUrl) {
        webDriver.get(countryUrl);

        boolean themaTable = false;

        WebElement specialCoinTable = null;

        List<WebElement> tables = webDriver.findElements(By.tagName("table"));

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

        List<WebElement> tableRows = specialCoinTable.findElements(By.tagName("tr"));
        tableRows.remove(0);
        if (themaTable) {
            tableRows.remove(1);
        }

        List<Edition> editionList = StreamSupport.stream(editionDao.findAll().spliterator(), false)
                .filter(edition -> getCountryNameFromUrl(countryUrl).equals(edition.getCountry()))
                .filter(edition -> edition.getEdition() == 0)
                .toList();

        if (editionList.size() != 1) {
            System.out.println("Multiple Special Edtions Found For Country");
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
                        System.out.println("No Picture For Coin.");
                    }
                    specialCoin.setName(tableRowEntries.get(1).getText());
                } else {
                    try {
                        specialCoin.setImagePath(tableRowEntries.get(1).findElement(By.tagName("img")).getAttribute("src"));
                    } catch (Exception e) {
                        System.out.println("No Picture For Coin.");
                    }
                    specialCoin.setName(tableRowEntries.get(3).getText());
                }
                coinDao.save(specialCoin);
            }
        }
    }

}
