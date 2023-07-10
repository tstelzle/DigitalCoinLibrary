package com.coinlibrary.backend.controller;

import com.coinlibrary.backend.model.Edition;
import com.coinlibrary.backend.repository.EditionRepository;
import com.coinlibrary.backend.service.EditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class EditionController {

    private final EditionService editionService;
    private final EditionRepository<Edition, Integer> editionRepository;

    @Autowired
    public EditionController(EditionService editionService, EditionRepository<Edition, Integer> editionRepository) {
        this.editionService = editionService;
        this.editionRepository = editionRepository;
    }

    @GetMapping("/api/edition/page/{pageKey}")
    public ResponseEntity<?> getPagedEditions(@PathVariable Integer pageKey) {
        org.springframework.data.domain.Pageable pageable = PageRequest.of(pageKey, 5);
        Page<Edition> editions = editionRepository.findAll(pageable);

        return new ResponseEntity<>(editions, HttpStatus.OK);
    }



    @GetMapping("/api/edition")
    public ResponseEntity<List<Edition>> getEditions(@RequestParam(required = false) Integer editionId) {
        if (editionId != null) {
            Optional<Edition> optionalEdition = editionRepository.findById(editionId);
            if (optionalEdition.isPresent()) {
                List<Edition> editionList = List.of(optionalEdition.get());

                return new ResponseEntity<>(editionList, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
        } else {
            return new ResponseEntity<>(editionService.getEditions(), HttpStatus.OK);
        }
    }
}
