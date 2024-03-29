package com.coinlibrary.backend.controller;

import com.coinlibrary.backend.model.Coin;
import com.coinlibrary.backend.repository.CoinRepository;
import com.coinlibrary.backend.service.CoinService;
import com.coinlibrary.backend.service.LibrarianService;
import com.coinlibrary.backend.specification.CoinSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class CoinController {

    private final CoinService coinService;
    private final CoinRepository<Coin, Long> coinRepository;
    private final LibrarianService librarianService;

    @Autowired
    public CoinController(CoinService coinService, CoinRepository<Coin, Long> coinRepository, LibrarianService librarianService) {
        this.coinService = coinService;
        this.coinRepository = coinRepository;
        this.librarianService = librarianService;
    }

    @GetMapping("/api/coin/page/{pageKey}")
    public ResponseEntity<?> getPagedCoins(@PathVariable("pageKey") Integer pageKey) {
        org.springframework.data.domain.Pageable pageable = PageRequest.of(pageKey, 50);
        Page<Coin> coins = coinRepository.findAll(pageable);

        return new ResponseEntity<>(coins, HttpStatus.OK);
    }

    @GetMapping("/api/coin")
    public ResponseEntity<?> getCoins(@RequestParam(name = "editionId", required = false) Integer editionId, @RequestParam(name = "size", required = false) Integer size, @RequestParam(name = "librarianIdentification") String librarianIdentification) {
        Specification<Coin> spec = Specification.where(null);

        // TODO not return librarians

        if (editionId != null) {
            spec = spec.and(CoinSpecification.isEdition(editionId));
        }

        if (size != null) {
            spec = spec.and(CoinSpecification.isSize(size));
        }

        List<Coin> coins = coinRepository.findAll(spec);

        if (!"".equals(librarianIdentification)) {
            Optional<List<Long>> coinsAvailableOptional = librarianService.getAvailableCoinIds(librarianIdentification);

            if (coinsAvailableOptional.isPresent()) {
                List<Long> coinsAvailable = coinsAvailableOptional.get();

                for (Coin coin : coins) {
                    if (coinsAvailable.contains(coin.getId())) {
                        coin.setAvailable(true);
                    }
                }
            }
        }

        return new ResponseEntity<>(coins, HttpStatus.OK);
    }

    // TODO CORS Error with post mapping
    // https://stackoverflow.com/questions/64621885/spring-boot-cors-working-with-get-request-but-not-post-request
    @PostMapping("/api/coin")
//    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Boolean> postCoin(@RequestParam(name = "coinID") Long coinId, @RequestParam(name = "librarianIdentification") String librarianIdentification, @RequestParam(name = "available") boolean available) {
        long status = coinService.setAvailable(coinId, librarianIdentification, available);

        if (status == -1) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(available, HttpStatus.OK);
    }
}
