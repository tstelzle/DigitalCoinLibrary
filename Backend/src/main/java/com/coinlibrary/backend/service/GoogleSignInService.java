//package com.coinlibrary.backend.service;
//
//import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
//import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
//import com.google.api.client.http.HttpTransport;
//import com.google.api.client.http.javanet.NetHttpTransport;
//import com.google.api.client.json.jackson2.JacksonFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//import java.security.GeneralSecurityException;
//import java.util.Collections;
//
//@Service
//public class GoogleSignInService {
//
//    @Value("${google.client.id}")
//    private String googleClientId;
//
//    public boolean validGoogleUser(String googleIdToken) {
//        // Create an HTTP transport and JSON factory
//        HttpTransport httpTransport = new NetHttpTransport();
//        JacksonFactory jacksonFactory = new JacksonFactory();
//
//        // Create a GoogleIdTokenVerifier with the HTTP transport and JSON factory
//        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(httpTransport, jacksonFactory)
//                .setAudience(Collections.singletonList(googleClientId)) // Replace with your own client ID
//                .build();
//
//        // Verify the token
//        try {
//            GoogleIdToken idToken = verifier.verify(googleIdToken);
//            if (idToken != null) {
//                // Token is valid
//                String userId = idToken.getPayload().getSubject();
//                // Additional validation or processing can be done here
//
//                return true;
//            }
//        } catch (GeneralSecurityException | IOException e) {
//            e.printStackTrace();
//        }
//
//        return false;
//    }
//
//}