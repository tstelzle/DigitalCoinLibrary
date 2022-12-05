package com.coinlibrary.backend.repository;

import com.coinlibrary.backend.model.Coin;
import com.coinlibrary.backend.model.Edition;

public interface CoinRepository<T, S> {

    public Coin findByEditionAndSize(Edition edition, int size);
}
