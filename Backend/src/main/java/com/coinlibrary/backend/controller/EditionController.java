package com.coinlibrary.backend.controller;

import com.coinlibrary.backend.model.Edition;
import com.coinlibrary.backend.service.EditionService;
import com.coinlibrary.backend.service.SeleniumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/edition")
public class EditionController {

    private final EditionService editionService;

    private final SeleniumService seleniumService;

    @Autowired
    public EditionController(EditionService editionService, SeleniumService seleniumService) {
        this.editionService = editionService;
        this.seleniumService = seleniumService;

        seleniumService.init();
        List<String> countryUrls = seleniumService.getEuroCountryLinks();
//        seleniumService.saveCountries(countryUrls);
        seleniumService.getEditions(countryUrls);
        seleniumService.quit();
    }

    @GetMapping
    public ResponseEntity<List<Edition>> getEditions() {
        return new ResponseEntity<>(editionService.getEditions(), HttpStatus.OK);
    }
}
