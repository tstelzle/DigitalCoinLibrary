package com.coinlibrary.backend.controller;

import com.coinlibrary.backend.model.Coin;
import com.coinlibrary.backend.service.CoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coin")
public class CoinController {

    private final CoinService coinService;

    @Autowired
    public CoinController(CoinService coinService) {
        this.coinService = coinService;
    }

    @GetMapping
    public ResponseEntity<List<Coin>> getCoins() {
        return new ResponseEntity<>(coinService.listCoins(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Integer> postCoin(@RequestBody Coin coin) {
        int id = coinService.addCoin(coin);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCoin(@RequestBody Coin coin) {
        coinService.removeCoin(coin);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
