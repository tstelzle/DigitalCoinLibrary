package com.coinlibrary.backend.controller;

import com.coinlibrary.backend.model.Edition;
import com.coinlibrary.backend.service.CoinService;
import com.coinlibrary.backend.service.EditionService;
import com.coinlibrary.backend.service.WikipediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/edition")
public class EditionController {

    private final EditionService editionService;

    @Autowired
    public EditionController(EditionService editionService, WikipediaService wikipediaService, CoinService coinService) throws MalformedURLException {
        this.editionService = editionService;

        if (Boolean.parseBoolean(System.getenv("DOWNLOAD_DATA"))) {
            wikipediaService.run();
            coinService.generateAllCoins();
        }
    }

    @GetMapping
    public ResponseEntity<List<Edition>> getEditions() {
        return new ResponseEntity<>(editionService.getEditions(), HttpStatus.OK);
    }
}
