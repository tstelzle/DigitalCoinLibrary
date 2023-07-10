package com.coinlibrary.backend.controller;

import com.coinlibrary.backend.model.Coin;
import com.coinlibrary.backend.repository.CoinRepository;
import com.coinlibrary.backend.service.CoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class CoinController {

    private final CoinService coinService;
    private final CoinRepository<Coin, Long> coinRepository;

    @Autowired
    public CoinController(CoinService coinService, CoinRepository<Coin, Long> coinRepository) {
        this.coinService = coinService;
        this.coinRepository = coinRepository;
    }

    @GetMapping("/api/coin/page/{pageKey}")
    public ResponseEntity<?> getPagedCoins(@PathVariable Integer pageKey) {
        org.springframework.data.domain.Pageable pageable = PageRequest.of(pageKey, 50);
        Page<Coin> coins = coinRepository.findAll(pageable);

        return new ResponseEntity<>(coins, HttpStatus.OK);
    }

    @GetMapping("/api/coin")
    public ResponseEntity<?> getCoinsByEdition(@RequestParam(required = false) Integer editionId) {
        if (editionId == null) {
            return new ResponseEntity<>(coinService.listCoins(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(coinService.listCoinsByEditionId(editionId), HttpStatus.OK);
        }
    }

    @PostMapping("/api/coin")
    public ResponseEntity<Void> postCoin(@RequestParam Long coinId) {
        long status = coinService.setAvailable(coinId);

        if (status == -1) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
