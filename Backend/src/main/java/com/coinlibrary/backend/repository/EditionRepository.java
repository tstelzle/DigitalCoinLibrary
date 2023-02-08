package com.coinlibrary.backend.repository;

import com.coinlibrary.backend.model.Edition;

import java.util.List;
import java.util.Optional;

public interface EditionRepository<T, S> {
    public List<Edition> findByCountry(String country);

    public Optional<Edition> findByCountryAndEdition(String country, int edition);

    public Optional<Edition> findByCountryAndYearFromGreaterThanAndYearToLessThan(String country, int year_from, int year_to);
}