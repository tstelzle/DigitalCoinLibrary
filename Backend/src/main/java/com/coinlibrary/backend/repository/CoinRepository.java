package com.coinlibrary.backend.repository;

import com.coinlibrary.backend.model.Coin;
import com.coinlibrary.backend.model.Edition;

import java.util.Optional;

public interface CoinRepository<T, S> {

    public Optional<Coin> findByEditionAndSizeAndSpecialAndName(Edition edition, int size, boolean special, String name);
    public Optional<Coin> findByEditionAndSizeAndSpecial(Edition edition, int size, boolean special);
}
