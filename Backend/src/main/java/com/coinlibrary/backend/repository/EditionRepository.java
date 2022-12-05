package com.coinlibrary.backend.repository;

import com.coinlibrary.backend.model.Edition;

import java.util.List;

public interface EditionRepository<T, S> {
        public List<Edition> findByCountry(String country);

        public Edition findByCountryAndEdition(String country, int edition);
}