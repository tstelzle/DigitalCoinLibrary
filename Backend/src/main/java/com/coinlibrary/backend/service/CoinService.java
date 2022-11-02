package com.coinlibrary.backend.service;

import com.coinlibrary.backend.model.Coin;
import com.coinlibrary.backend.model.Edition;
import com.coinlibrary.backend.repository.CoinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CoinService {

    @Autowired
    private CoinRepository coinRepository;

    public Long addCoin(Coin coin) {
        Coin coinEntity = coinRepository.save(coin);
        return coinEntity.getId();
    }

    public Map<Edition, List<Coin>> listCoins() {
        Iterable<Coin> coinIterable = coinRepository.findAll();

        return StreamSupport.stream(coinIterable.spliterator(), false).collect(Collectors.groupingBy(Coin::getEdition));
    }
}
