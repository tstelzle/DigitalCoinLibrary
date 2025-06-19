package com.coinlibrary.backend.controller;

import com.coinlibrary.backend.dto.EditionDto;
import com.coinlibrary.backend.model.Edition;
import com.coinlibrary.backend.repository.EditionRepository;
import com.coinlibrary.backend.service.CoinService;
import com.coinlibrary.backend.service.EditionService;
import com.coinlibrary.backend.specification.EditionSpecification;
import com.coinlibrary.backend.util.CountryLookUp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@RestController
@CrossOrigin
public class EditionController {

    private final EditionService editionService;
    private final CoinService coinService;

    @Autowired
    public EditionController(EditionService editionService, CoinService coinService) {
        this.editionService = editionService;
        this.coinService = coinService;
    }

    @GetMapping("/api/edition")
    public ResponseEntity<List<EditionDto>> getEditions() {

        Iterable<Edition> editions = editionService.findAll();

        List<EditionDto> editionDtos = new ArrayList<>();

        for (Edition edition : editions) {
            EditionDto editionDto = new EditionDto();
            editionDto.id = edition.getId();
            editionDto.country = edition.getCountry();
            editionDto.edition = edition.getEdition();
            editionDto.yearFrom = edition.getYearFrom();
            editionDto.yearTo = edition.getYearTo();
            editionDto.coins = coinService.findCoinsByEdition(edition);
            editionDto.editionString = edition.getEditionString();

            editionDtos.add(editionDto);
        }

        return new ResponseEntity<List<EditionDto>>(editionDtos, HttpStatus.OK);
    }

    @GetMapping("/api/edition/page/{pageKey}")
    public ResponseEntity<?> getPagedEditions(@PathVariable(name = "pageKey") Integer pageKey, @RequestParam(name = "country", required = false) String country, @RequestParam(name = "special", required = false) Boolean special) {
        // TODO Move To Service
        org.springframework.data.domain.Pageable pageable = PageRequest.of(pageKey, 5);

        Specification<Edition> spec = Specification.where(null);

        if (country != null) {
            String countrycode = CountryLookUp.getInstance().getCountryCode(country);
            spec = spec.and(EditionSpecification.hasCountry(countrycode));
        }

        if (special != null && special) {
            spec = spec.and(EditionSpecification.isSpecial());
        }

        Page<Edition> editions = editionService.findAll(spec, pageable);

        return new ResponseEntity<>(editions, HttpStatus.OK);
    }

    @GetMapping("/api/edition/countries")
    public ResponseEntity<List<String>> getEditionCountries() {
        return new ResponseEntity<>(Stream.concat(Stream.of("all"), editionService.getCountries().stream()).toList(), HttpStatus.OK);
    }
}
