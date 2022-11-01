package com.coinlibrary.backend.service;

import com.coinlibrary.backend.model.Coin;
import com.coinlibrary.backend.repository.CoinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class CoinService {

    @Autowired
    private CoinRepository coinRepository;

    public int addCoin(Coin coin) {
        Coin coinEntity = coinRepository.save(coin);
        return coinEntity.getId();
    }

    public void removeCoin(Coin coin) {
        coinRepository.delete(coin);
    }

    public List<Coin> listCoins() {
        return StreamSupport.stream(coinRepository.findAll().spliterator(), false).toList();
    }
}
