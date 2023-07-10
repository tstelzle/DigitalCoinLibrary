package com.coinlibrary.backend.repository;

import com.coinlibrary.backend.model.Edition;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface EditionRepository<T, S> extends PagingAndSortingRepository<Edition, Integer> {
    public List<Edition> findByCountry(String country);

    public Optional<Edition> findByCountryAndEdition(String country, int edition);
}