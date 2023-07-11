package com.coinlibrary.backend.controller;

import com.coinlibrary.backend.model.Coin;
import com.coinlibrary.backend.repository.CoinRepository;
import com.coinlibrary.backend.service.CoinService;
import com.coinlibrary.backend.specification.CoinSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<?> getCoins(@RequestParam(required = false) Integer editionId, @RequestParam(required = false) Integer size) {
        Specification<Coin> spec = Specification.where(null);

        if (editionId != null) {
            spec = spec.and(CoinSpecification.isEdition(editionId));
        }

        if (size != null) {
            spec = spec.and(CoinSpecification.isSize(size));
        }

        List<Coin> coins = coinRepository.findAll(spec);

        return new ResponseEntity<>(coins, HttpStatus.OK);
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
