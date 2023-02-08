package com.coinlibrary.backend.service;

import com.coinlibrary.backend.component.EcbComponent;
import com.coinlibrary.backend.component.WikipediaComponent;
import com.coinlibrary.backend.model.Coin;
import com.coinlibrary.backend.model.Edition;
import com.coinlibrary.backend.repository.EditionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ExtractionService {

    private final WikipediaComponent wikipediaComponent;
    private final CoinService coinService;
    private final EcbComponent ecbComponent;
    private final EditionDao editionDao;

    @Autowired
    public ExtractionService(WikipediaComponent wikipediaComponent, CoinService coinService, EcbComponent ecbComponent, EditionDao editionDao) {
        this.wikipediaComponent = wikipediaComponent;
        this.coinService = coinService;
        this.ecbComponent = ecbComponent;
        this.editionDao = editionDao;
    }

    @Scheduled(initialDelay = 0, fixedRate = 24 * 60 * 60 * 1000)
    public void run() {
        wikipediaComponent.run();
        generateAllCoins();
        ecbComponent.run();
    }

    public void generateAllCoins() {
        Iterable<Edition> editionIterator = editionDao.findAll();
        List<Integer> coinSizes = Arrays.asList(1, 2, 5, 10, 20, 50, 100, 200);

        for (Edition edition : editionIterator) {
            if (edition.getEdition() != 0) {
                for (Integer coinSize : coinSizes) {
                    Coin coin = new Coin();
                    coin.setEdition(edition);
                    coin.setSize(coinSize);
                    coin.setSpecial(false);

                    coinService.updateOrInsertCoin(coin);
                }
            }
        }
    }
}
