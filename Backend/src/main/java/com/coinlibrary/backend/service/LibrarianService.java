package com.coinlibrary.backend.service;

import com.coinlibrary.backend.model.Coin;
import com.coinlibrary.backend.model.Librarian;
import com.coinlibrary.backend.repository.CoinRepository;
import com.coinlibrary.backend.repository.LibrarianRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LibrarianService {

    private final LibrarianRepository<Librarian, Long> librarianRepository;
    private final CoinRepository<Coin, Long> coinRepository;

    public LibrarianService(LibrarianRepository<Librarian, Long> librarianRepository,
                            CoinRepository<Coin, Long> coinRepository) {
        this.librarianRepository = librarianRepository;
        this.coinRepository = coinRepository;
    }

    public Optional<List<Long>> getAvailableCoinIds(String librarianName) {
        Optional<Librarian> librarianOptional = librarianRepository.findByLibrarianName(librarianName);
        if (librarianOptional.isPresent()) {
            List<Coin> availableCoins = coinRepository.findCoinsByLibrarians(librarianOptional.get());

            List<Long> availableCoinIds = availableCoins.stream().map(Coin::getId).toList();

            return Optional.of(availableCoinIds);
        } else {
            return Optional.empty();
        }
    }
}
