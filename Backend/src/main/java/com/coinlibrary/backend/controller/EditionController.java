package com.coinlibrary.backend.controller;

import com.coinlibrary.backend.model.Edition;
import com.coinlibrary.backend.service.EditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/edition")
public class EditionController {

    private final EditionService editionService;

    @Autowired
    public EditionController(EditionService editionService) {
        this.editionService = editionService;
    }

    @GetMapping
    public ResponseEntity<List<Edition>> getEditions() {
        return new ResponseEntity<>(editionService.getEditions(), HttpStatus.OK);
    }
}
