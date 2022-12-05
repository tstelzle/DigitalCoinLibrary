package com.coinlibrary.backend.service;

import com.coinlibrary.backend.model.Edition;
import com.coinlibrary.backend.repository.EditionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class EditionService {

    @Autowired
    private EditionDao editionDao;

    public void addEditions() {
        // TODO scrape data from wikipedia
    }

    public List<Edition> getEditions() {
        return StreamSupport.stream(editionDao.findAll().spliterator(), false).collect(Collectors.toList());
    }

    public Edition getEditionByYear(List<Edition> editionList, int year) {
        for (Edition edition : editionList) {
            if (edition.getYear_from() <= year && year <= edition.getYear_to()) {
                return edition;
            }
        }

        return null;
    }
}
