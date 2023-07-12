//package com.coinlibrary.backend.controller;
//
//import com.coinlibrary.backend.service.GoogleSignInService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping
//public class GoogleSignInController {
//
//    private final GoogleSignInService googleSignInService;
//
//    @Autowired
//    public GoogleSignInController(GoogleSignInService googleSignInService) {
//        this.googleSignInService = googleSignInService;
//    }
//
//
//    @GetMapping("/api/authenticate")
//    public ResponseEntity<?> signInWithGoogle(@RequestParam String idToken) {
//        return new ResponseEntity<>(googleSignInService.validGoogleUser(idToken), HttpStatus.OK);
//    }
//}

//TODO breaks selenium
//<dependency>
//<groupId>com.google.api-client</groupId>
//<artifactId>google-api-client</artifactId>
//<version>1.31.1</version>
//</dependency>
//<dependency>
//<groupId>com.fasterxml.jackson.core</groupId>
//<artifactId>jackson-core</artifactId>
//<version>2.12.4</version>
//</dependency>
//<dependency>
//<groupId>com.fasterxml.jackson.core</groupId>
//<artifactId>jackson-databind</artifactId>
//<version>2.12.4</version>
//</dependency>