package com.coinlibrary.backend.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Optional;

@Service
@Slf4j
public class GoogleTokenVerifier {

    private final NetHttpTransport transport;
    private final JsonFactory jsonFactory;
    @Value("${google.client.id}")
    private String googleClientId;

    public GoogleTokenVerifier() {
        this.transport = new NetHttpTransport();
        this.jsonFactory = GsonFactory.getDefaultInstance();
    }

    public Optional<Payload> verify(String idTokenString) {
        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                    .setAudience(Collections.singletonList(googleClientId))
                    // Or, if multiple clients access the backend:
                    //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
                    .build();

            GoogleIdToken idToken = verifier.verify(idTokenString);
            if (idToken != null) {
                return Optional.of(idToken.getPayload());
//                String userId = payload.getSubject();
//                String email = payload.getEmail();
//                boolean emailVerified = payload.getEmailVerified();
//                String name = (String) payload.get("name");
//                String pictureUrl = (String) payload.get("picture");
//                String locale = (String) payload.get("locale");
//                String familyName = (String) payload.get("family_name");
//                String givenName = (String) payload.get("given_name");
            } else {
                log.error("INVALID ID TOKEN");
                return Optional.empty();
            }
        } catch (GeneralSecurityException | IOException exception) {
            log.error(exception.getMessage());
            return Optional.empty();
        }
    }
}
