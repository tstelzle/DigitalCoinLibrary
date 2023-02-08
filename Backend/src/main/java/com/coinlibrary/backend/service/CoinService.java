package com.coinlibrary.backend.service;

import com.coinlibrary.backend.model.Coin;
import com.coinlibrary.backend.repository.CoinDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Slf4j
public class CoinService {

    private final CoinDao coinDao;

    @Autowired
    public CoinService(CoinDao coinDao) {
        this.coinDao = coinDao;
    }

    public void updateOrInsertCoin(Coin coin) {
        Optional<Coin> optionalCoin = coinDao.findByEditionAndSizeAndSpecialAndName(coin.getEdition(), coin.getSize(), coin.isSpecial(), coin.getName());
        if (optionalCoin.isPresent()) {
            Coin dbCoin = optionalCoin.get();
            dbCoin.setImagePath(coin.getImagePath());
            dbCoin.setSpecial(coin.isSpecial());
            log.info("Updating value: {}, {}, {}", coin.getSize(), coin.getEdition()
                                                                       .getCountry(), coin.getName());
            coinDao.save(dbCoin);
        } else {
            log.info("Inserting value: {}, {}, {}", coin.getSize(), coin.getEdition()
                                                                        .getCountry(), coin.getName());
            coinDao.save(coin);
        }
    }

    public int setAvailable(int coinId) {
        Optional<Coin> coin = coinDao.findById(coinId);

        if (coin.isPresent()) {
            coin.get()
                .setAvailable(true);
            coinDao.save(coin.get());

            return Math.toIntExact(coin.get()
                                       .getId());
        }

        return -1;
    }

    public Map<String, List<Coin>> listCoins() {
        Iterable<Coin> coinIterable = coinDao.findAll();

        return StreamSupport.stream(coinIterable.spliterator(), false)
                            .collect(Collectors.groupingBy(Coin::getEditionString));
    }

}
