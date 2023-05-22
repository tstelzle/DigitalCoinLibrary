package com.coinlibrary.backend.controller;

import com.coinlibrary.backend.service.CoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/coin")
public class CoinController {

    private final CoinService coinService;

    @Autowired
    public CoinController(CoinService coinService) {
        this.coinService = coinService;
    }

    @GetMapping
    public ResponseEntity<?> getCoinsByEdition(@RequestParam(required = false) Integer editionId) {
        if (editionId == null) {
            return new ResponseEntity<>(coinService.listCoins(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(coinService.listCoinsByEditionId(editionId), HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<Void> postCoin(@RequestParam Long coinId) {
        long status = coinService.setAvailable(coinId);

        if (status == -1) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
