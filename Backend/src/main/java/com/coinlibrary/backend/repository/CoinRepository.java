package com.coinlibrary.backend.repository;

import com.coinlibrary.backend.model.Coin;
import com.coinlibrary.backend.model.Edition;
import com.coinlibrary.backend.model.Librarian;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface CoinRepository<T, S> extends PagingAndSortingRepository<Coin, Long>, JpaSpecificationExecutor<Coin> {

    Coin save(Coin coin);

    Optional<Coin> findById(long id);

    public Optional<Coin> findByEditionAndSizeAndSpecialAndName(Edition edition, int size, boolean special, String name);

    public Optional<Coin> findByEditionAndSizeAndSpecial(Edition edition, int size, boolean special);

    public List<Coin> findCoinsByLibrarians(Librarian librarian);
}
