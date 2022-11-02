package com.coinlibrary.backend.service;

import com.coinlibrary.backend.model.Edition;
import com.coinlibrary.backend.repository.EditionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class EditionService {

    @Autowired
    private EditionRepository editionRepository;

    public void addEditions() {
        // TODO scrape data from wikipedia
    }

    public List<Edition> getEditions() {
        return StreamSupport.stream(editionRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }
}
