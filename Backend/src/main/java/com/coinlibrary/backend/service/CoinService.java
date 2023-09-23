package com.coinlibrary.backend.service;

import com.coinlibrary.backend.model.Coin;
import com.coinlibrary.backend.model.Librarian;
import com.coinlibrary.backend.repository.CoinRepository;
import com.coinlibrary.backend.repository.LibrarianRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class CoinService {

    private final CoinRepository<Coin, Long> coinRepository;
    private final LibrarianRepository<Librarian, Long> librarianRepository;

    @Autowired
    public CoinService(CoinRepository<Coin, Long> coinRepository, LibrarianRepository<Librarian, Long> librarianRepository) {
        this.coinRepository = coinRepository;
        this.librarianRepository = librarianRepository;
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

    public long setAvailable(long coinId, String librarianEmail, boolean available) {
        Optional<Coin> coinOptional = coinRepository.findById(coinId);
        Optional<Librarian> librarianOptional = librarianRepository.findByLibrarianEmail(librarianEmail);

        if (coinOptional.isPresent() && librarianOptional.isPresent()) {
            Coin coin = coinOptional.get();
            Librarian librarian = librarianOptional.get();
            if (available) {
                coin.addLibrarian(librarian);
            } else {
                coin.removeLibrarian(librarian);
            }

            return coin.getId();
        }

        return -1L;
    }

}
