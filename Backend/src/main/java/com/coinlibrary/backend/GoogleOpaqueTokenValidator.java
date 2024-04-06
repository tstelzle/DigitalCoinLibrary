package com.coinlibrary.backend;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.DefaultOAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;
import java.util.Collections;
import java.util.Map;

public class GoogleOpaqueTokenValidator implements OpaqueTokenIntrospector {

    @Override
    public OAuth2AuthenticatedPrincipal introspect(String token) {
        String url = "https://oauth2.googleapis.com/tokeninfo?access_token=" + token;

        try {
            URL obj = new URL(url);

            // Open a connection using HttpURLConnection
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // Set the request method to GET
            con.setRequestMethod("GET");

            // Read the response
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> jsonMap = objectMapper.readValue(response.toString(), new TypeReference<Map<String, Object>>() {
            });
            jsonMap.put("expires_in", Integer.parseInt(String.valueOf(jsonMap.get("expires_in"))));
            jsonMap.put("exp", Instant.ofEpochSecond(Long.parseLong(String.valueOf(jsonMap.get("exp")))));

            if ((int) jsonMap.get("expires_in") > 0) {
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(String.valueOf(jsonMap.get("email")));
                return new DefaultOAuth2AuthenticatedPrincipal(String.valueOf(jsonMap.get("email")), jsonMap, Collections.singleton(authority));
            } else {
                return null;
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

