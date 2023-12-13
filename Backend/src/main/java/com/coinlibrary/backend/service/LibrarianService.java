package com.coinlibrary.backend.service;

import com.coinlibrary.backend.model.Coin;
import com.coinlibrary.backend.model.Librarian;
import com.coinlibrary.backend.repository.CoinRepository;
import com.coinlibrary.backend.repository.LibrarianRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class LibrarianService {

    private final LibrarianRepository<Librarian, Long> librarianRepository;
    private final CoinRepository<Coin, Long> coinRepository;

    public LibrarianService(LibrarianRepository<Librarian, Long> librarianRepository,
                            CoinRepository<Coin, Long> coinRepository) {
        this.librarianRepository = librarianRepository;
        this.coinRepository = coinRepository;
    }

    public void updateOrInsert(Librarian librarian) {
        Optional<Librarian> optionalLibrarian = librarianRepository.findByLibrarianEmail(librarian.getLibrarianEmail());
        if (optionalLibrarian.isPresent()) {
            Librarian dbLibrarian = optionalLibrarian.get();
            dbLibrarian.setLibrarianEmail(librarian.getLibrarianEmail());
            log.info("Updating value: {}, {}", dbLibrarian.getUuid(), dbLibrarian.getLibrarianEmail());
            librarianRepository.save(dbLibrarian);
        } else {
            librarian.setUuid(UUID.randomUUID());
            log.info("Inserting value: {}, {}", librarian.getUuid(), librarian.getLibrarianEmail());
            librarianRepository.save(librarian);
        }
    }


    public Optional<List<Long>> getAvailableCoinIds(String librarianEmail) {
        Optional<Librarian> librarianOptional = librarianRepository.findByLibrarianEmail(librarianEmail);
        if (librarianOptional.isPresent()) {
            Librarian librarian = librarianOptional.get();
            List<Long> availableCoinIds = librarian.getCoins().stream().map(Coin::getId).toList();

            return Optional.of(availableCoinIds);
        } else {
            return Optional.empty();
        }
    }
}
