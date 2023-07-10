package com.coinlibrary.backend.service;

import com.coinlibrary.backend.model.Coin;
import com.coinlibrary.backend.model.Edition;
import com.coinlibrary.backend.repository.CoinRepository;
import com.coinlibrary.backend.repository.EditionRepository;
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

    private final CoinRepository<Coin, Long> coinRepository;
    private final EditionRepository<Edition, Integer> editionRepository;

    @Autowired
    public CoinService(CoinRepository<Coin, Long> coinRepository, EditionRepository<Edition, Integer> editionRepository) {
        this.coinRepository = coinRepository;
        this.editionRepository = editionRepository;
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

    public Map<String, List<Coin>> listCoins() {
        Iterable<Coin> coinIterable = coinRepository.findAll();

        return StreamSupport.stream(coinIterable.spliterator(), false)
                .collect(Collectors.groupingBy(coin -> coin.getEdition().getEditionString()));
    }

    public List<Coin> listCoinsByEditionId(int editionId) {
        Optional<Edition> optionalEdition = editionRepository.findById(editionId);

        if (optionalEdition.isPresent()) {
            Edition edition = optionalEdition.get();
            Iterable<Coin> coinIterable = coinRepository.findByEdition(edition);

            return StreamSupport.stream(coinIterable.spliterator(), false)
                    .toList();
        }

        return new ArrayList<>();
    }

}
