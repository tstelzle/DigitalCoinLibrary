package com.coinlibrary.backend.controller;

import com.coinlibrary.backend.service.LibrarianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping
public class LibrarianController {

    private final LibrarianService librarianService;

    @Autowired
    public LibrarianController(LibrarianService librarianService) {
        this.librarianService = librarianService;
    }

    @GetMapping("/api/user")
    public ResponseEntity<List<Long>> getAvailableCoins(@RequestParam String librarianName) {
        Optional<List<Long>> availableCoinIdsOptionals = librarianService.getAvailableCoinIds(librarianName);
        return availableCoinIdsOptionals.map(integers -> new ResponseEntity<>(integers, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST));
    }
}
