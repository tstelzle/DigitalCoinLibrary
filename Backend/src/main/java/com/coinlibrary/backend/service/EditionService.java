package com.coinlibrary.backend.service;

import com.coinlibrary.backend.model.Edition;
import com.coinlibrary.backend.repository.EditionRepository;
import com.coinlibrary.backend.util.CountryLookUp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
@Slf4j
public class EditionService {

    private final EditionRepository<Edition, Integer> editionRepository;

    @Autowired
    public EditionService(EditionRepository<Edition, Integer> editionRepository) {
        this.editionRepository = editionRepository;
    }

    public List<String> getCountries() {
        return StreamSupport.stream(editionRepository.findAll().spliterator(), false).map(Edition::getCountry).distinct().map(c -> CountryLookUp.getInstance().getCountryName(c)).toList();
    }

    public void updateOrInsert(Edition edition) {
        Optional<Edition> optionalEdition = editionRepository.findByCountryAndEdition(edition.getCountry(), edition.getEdition());
        if (optionalEdition.isPresent()) {
            Edition dbEdition = optionalEdition.get();
            dbEdition.setYearFrom(edition.getYearFrom());
            dbEdition.setYearTo(edition.getYearTo());
            log.info("Updating value: {}, {}, {}", edition.getCountry(), edition.getYearFrom(), edition.getYearTo());
            editionRepository.save(dbEdition);
        } else {
            log.info("Inserting value: {}, {}, {}", edition.getCountry(), edition.getYearFrom(), edition.getYearTo());
            editionRepository.save(edition);
        }
    }
}
