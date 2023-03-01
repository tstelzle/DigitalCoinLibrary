package com.coinlibrary.backend.service;

import com.coinlibrary.backend.model.Edition;
import com.coinlibrary.backend.repository.EditionDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Slf4j
public class EditionService {

    private final EditionDao editionDao;

    @Autowired
    public EditionService(EditionDao editionDao) {
        this.editionDao = editionDao;
    }

    public List<Edition> getEditions() {
        return StreamSupport.stream(editionDao.findAll()
                                              .spliterator(), false)
                            .collect(Collectors.toList());
    }

    public void updateOrInsert(Edition edition) {
        Optional<Edition> optionalEdition = editionDao.findByCountryAndEdition(edition.getCountry(), edition.getEdition());
        if (optionalEdition.isPresent()) {
            Edition dbEdition = optionalEdition.get();
            dbEdition.setYearFrom(edition.getYearFrom());
            dbEdition.setYearTo(edition.getYearTo());
            log.info("Updating value: {}, {}, {}", edition.getCountry(), edition.getYearFrom(), edition.getYearTo());
            editionDao.save(dbEdition);
        } else {
            log.info("Inserting value: {}, {}, {}", edition.getCountry(), edition.getYearFrom(), edition.getYearTo());
            editionDao.save(edition);
        }
    }
}
