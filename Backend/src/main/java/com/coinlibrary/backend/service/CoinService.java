package com.coinlibrary.backend.service;

import com.coinlibrary.backend.model.Coin;
import com.coinlibrary.backend.repository.CoinRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class CoinService {

    private final CoinRepository<Coin, Long> coinRepository;

    @Autowired
    public CoinService(CoinRepository<Coin, Long> coinRepository) {
        this.coinRepository = coinRepository;
    }

    public void updateOrInsertCoin(Coin coin) {
        Optional<Coin> optionalCoin = coinRepository.findByEditionAndSizeAndSpecialAndName(coin.getEdition(), coin.getSize(), coin.isSpecial(), coin.getName());
        if (optionalCoin.isPresent()) {
            Coin dbCoin = optionalCoin.get();
            dbCoin.setImagePath(coin.getImagePath());
            dbCoin.setSpecial(coin.isSpecial());
            log.info("Updating value: {}, {}, {}", coin.getSize(), coin.getEdition()
                    .getCountry(), coin.getName());
            coinRepository.save(dbCoin);
        } else {
            log.info("Inserting value: {}, {}, {}", coin.getSize(), coin.getEdition()
                    .getCountry(), coin.getName());
            coinRepository.save(coin);
        }
    }

    public int setAvailable(long coinId) {
        Optional<Coin> coin = coinRepository.findById(coinId);

        if (coin.isPresent()) {
            coin.get()
                    .setAvailable(true);
            coinRepository.save(coin.get());

            return Math.toIntExact(coin.get()
                    .getId());
        }

        return -1;
    }

}
