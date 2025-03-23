package com.coinlibrary.backend;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FailureEvents {
    @EventListener
    public void onFailure(AuthenticationFailureBadCredentialsEvent badCredentials) {
        if (badCredentials.getAuthentication() instanceof BearerTokenAuthenticationToken) {
            log.error(badCredentials.getException().getMessage());
        }
    }
}