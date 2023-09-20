package com.coinlibrary.backend.controller;

import com.coinlibrary.backend.service.GoogleTokenVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class GoogleSignInController {

    private final GoogleTokenVerifier googleTokenVerifier;

    @Autowired
    public GoogleSignInController(GoogleTokenVerifier googleTokenVerifier) {
        this.googleTokenVerifier = googleTokenVerifier;
    }


    @GetMapping("/api/authenticate")
    public ResponseEntity<?> signInWithGoogle(@RequestParam String idToken) {
        return new ResponseEntity<>(googleTokenVerifier.verify(idToken), HttpStatus.OK);
        // TODO create user if verify returned true
    }
}
