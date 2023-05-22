package com.coinlibrary.backend.service;

import com.coinlibrary.backend.model.Coin;
import com.coinlibrary.backend.model.Edition;
import com.coinlibrary.backend.repository.CoinDao;
import com.coinlibrary.backend.repository.EditionDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Slf4j
public class CoinService {

    private final CoinDao coinDao;
    private final EditionDao editionDao;

    @Autowired
    public CoinService(CoinDao coinDao, EditionDao editionDao) {
        this.coinDao = coinDao;
        this.editionDao = editionDao;
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

    public int setAvailable(long coinId) {
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

    public List<Coin> listCoinsByEditionId(int editionId) {
        Optional<Edition> optionalEdition = editionDao.findById(editionId);

        if (optionalEdition.isPresent()) {
            Edition edition = optionalEdition.get();
            Iterable<Coin> coinIterable = coinDao.findByEdition(edition);

            return StreamSupport.stream(coinIterable.spliterator(), false)
                    .toList();
        }

        return new ArrayList<>();
    }

}
