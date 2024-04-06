package com.coinlibrary.backend;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.DefaultOAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class GoogleOpaqueTokenValidator implements OpaqueTokenIntrospector {

    @Override
    public OAuth2AuthenticatedPrincipal introspect(String token) {

/*        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://oauth2.googleapis.com/tokeninfo"))
                .build();*/

/*        URL url = new URL("https://oauth2.googleapis.com/tokeninfo");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        Map<String, String> parameters = new HashMap<>();
        parameters.put("access_token", token);

        con.setDoOutput(true);
        DataOutputStream out = new DataOutputStream(con.getOutputStream());
        out.writeBytes(ParameterStringBuilder.getParamsString(parameters));
        out.flush();
        out.close();

        System.out.println("what is happening here!");
        // Implement logic to validate the opaque token issued by Google
        // This may involve sending a request to Google's token introspection endpoint
        // and parsing the response to extract token metadata
        // Return null if the token is invalid, otherwise return token metadata
        return null;*/

        // URL of the endpoint
        String url = "https://oauth2.googleapis.com/tokeninfo?access_token=" + token;

        // Create a URL object
        URL obj = null;
        try {
            obj = new URL(url);


            // Open a connection using HttpURLConnection
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // Set the request method to GET
            con.setRequestMethod("GET");

            // Get the response code
            int responseCode = con.getResponseCode();
            System.out.println("Response Code : " + responseCode);

            // Read the response
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> jsonMap = objectMapper.readValue(response.toString(), new TypeReference<Map<String, Object>>() {});

            jsonMap.put("expires_in", Integer.parseInt(String.valueOf(jsonMap.get("expires_in"))));
            jsonMap.put("exp", Instant.ofEpochSecond(Long.parseLong(String.valueOf(jsonMap.get("exp")))));

            if ((int) jsonMap.get("expires_in") > 0) {
                OAuth2AccessToken accessToken = new OAuth2AccessToken(
                        OAuth2AccessToken.TokenType.BEARER, token, null, null);

                SimpleGrantedAuthority authority = new SimpleGrantedAuthority("GOOGLE_ACCESS_TOKEN");

                return new DefaultOAuth2AuthenticatedPrincipal(jsonMap, Collections.singleton(authority));
            } else {
                return null;
            }
            // Print the response

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

