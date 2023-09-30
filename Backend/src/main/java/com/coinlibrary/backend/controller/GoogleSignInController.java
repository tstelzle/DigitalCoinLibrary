package com.coinlibrary.backend.controller;

import com.coinlibrary.backend.model.Librarian;
import com.coinlibrary.backend.service.GoogleTokenVerifier;
import com.coinlibrary.backend.service.LibrarianService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping
public class GoogleSignInController {

    private final GoogleTokenVerifier googleTokenVerifier;
    private final LibrarianService librarianService;

    @Autowired
    public GoogleSignInController(GoogleTokenVerifier googleTokenVerifier, LibrarianService librarianService) {
        this.googleTokenVerifier = googleTokenVerifier;
        this.librarianService = librarianService;
    }


    @GetMapping("/api/authenticate")
    public ResponseEntity<?> signInWithGoogle(@RequestParam String idToken) {
        Optional<Payload> optionalPayload = googleTokenVerifier.verify(idToken);
        if (optionalPayload.isPresent()) {
            Payload payload = optionalPayload.get();
            Librarian librarian = new Librarian();
            librarian.setLibrarianEmail(payload.getEmail());

            librarianService.updateOrInsertLibrarian(librarian);

            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
