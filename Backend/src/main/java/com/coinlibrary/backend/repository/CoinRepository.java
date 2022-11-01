package com.coinlibrary.backend.repository;

import com.coinlibrary.backend.model.Coin;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoinRepository extends CrudRepository<Coin, Integer> {
}
