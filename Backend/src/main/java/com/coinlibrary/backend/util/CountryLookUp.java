package com.coinlibrary.backend.util;

import org.checkerframework.checker.units.qual.C;

import java.util.HashMap;
import java.util.Map;

public class CountryLookUp {
    private final Map<String, String> countryCodeMap;
    private static CountryLookUp countryLookUpInstance;

    private CountryLookUp() {
        countryCodeMap = new HashMap<>();
        initializeCountryCodeMap();
    }

    public static CountryLookUp getInstance() {
        if (countryLookUpInstance == null) {
            countryLookUpInstance = new CountryLookUp();
        }

        return countryLookUpInstance;
    }

    private void initializeCountryCodeMap() {
        countryCodeMap.put("es", "Spanien");
        countryCodeMap.put("gr", "Griechenland");
        countryCodeMap.put("lv", "Lettland");
        countryCodeMap.put("ad", "Andorra");
        countryCodeMap.put("be", "Belgien");
        countryCodeMap.put("cy", "Zypern");
        countryCodeMap.put("pt", "Portugal");
        countryCodeMap.put("va", "Vatikan");
        countryCodeMap.put("mt", "Malta");
        countryCodeMap.put("at", "Österreich");
        countryCodeMap.put("nl", "Niederlande");
        countryCodeMap.put("mo", "Monaco");
        countryCodeMap.put("ie", "Irland");
        countryCodeMap.put("lu", "Luxemburg");
        countryCodeMap.put("fi", "Finnland");
        countryCodeMap.put("it", "Italien");
        countryCodeMap.put("sm", "San-Marino");
        countryCodeMap.put("sk", "Slowakei");
        countryCodeMap.put("sl", "Slowenien");
        countryCodeMap.put("fr", "Frankreich");
        countryCodeMap.put("lt", "Litauen");
        countryCodeMap.put("de", "Deutschland");
        countryCodeMap.put("et", "Estland");
        countryCodeMap.put("hr", "Kroatien");
    }

    public String getCountryName(String countryCode) {
        return countryCodeMap.get(countryCode);
    }
}
