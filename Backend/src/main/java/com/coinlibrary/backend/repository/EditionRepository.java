package com.coinlibrary.backend.repository;

import com.coinlibrary.backend.model.Edition;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface EditionRepository<T, S> extends PagingAndSortingRepository<Edition, Integer>, JpaSpecificationExecutor<Edition> {
    Edition save(Edition edition);

    Optional<Edition> findById(long id);

    public List<Edition> findByCountry(String country);

    public Optional<Edition> findByCountryAndEdition(String country, int edition);

    Iterable<Edition> findAll();
}