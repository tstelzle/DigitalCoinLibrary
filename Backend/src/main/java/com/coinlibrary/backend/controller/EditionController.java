package com.coinlibrary.backend.controller;

import com.coinlibrary.backend.model.Edition;
import com.coinlibrary.backend.repository.EditionRepository;
import com.coinlibrary.backend.service.EditionService;
import com.coinlibrary.backend.specification.EditionSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Stream;

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
    public ResponseEntity<?> getPagedEditions(@PathVariable Integer pageKey, @RequestParam(required = false) String country, @RequestParam(required = false) Boolean special) {
        org.springframework.data.domain.Pageable pageable = PageRequest.of(pageKey, 5);

        Specification<Edition> spec = Specification.where(null);

        if (country != null) {
            spec = spec.and(EditionSpecification.hasCountry(country));
        }

        if (special != null) {
            spec = spec.and(EditionSpecification.isSpecial());
        }

        Page<Edition> editions = editionRepository.findAll(spec, pageable);

        return new ResponseEntity<>(editions, HttpStatus.OK);
    }

    @GetMapping("/api/edition/countries")
    public ResponseEntity<List<String>> getEditionCountries() {
        return new ResponseEntity<>(Stream.concat(Stream.of("all"), editionService.getCountries().stream()).toList(), HttpStatus.OK);
    }
}
