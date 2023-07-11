package com.coinlibrary.backend.repository;

import com.coinlibrary.backend.model.Coin;
import com.coinlibrary.backend.model.Edition;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface CoinRepository<T, S> extends PagingAndSortingRepository<Coin, Long>, JpaSpecificationExecutor<Coin> {

    public Optional<Coin> findByEditionAndSizeAndSpecialAndName(Edition edition, int size, boolean special, String name);

    public Optional<Coin> findByEditionAndSizeAndSpecial(Edition edition, int size, boolean special);
}
