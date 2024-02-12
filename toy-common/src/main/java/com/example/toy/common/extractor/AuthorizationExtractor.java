package com.example.toy.common.extractor;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class AuthorizationExtractor {
    public static final String BEARER_TYPE = "Bearer ";

    public static String extractFromRequest(final HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_TYPE)) {
            return extractFromTokenIncludingBearer(bearerToken);
        }
        return null;
    }

    public static String extractFromTokenIncludingBearer(final String jwt) {
        if (StringUtils.hasText(jwt) && jwt.startsWith(BEARER_TYPE)) {
            return jwt.substring(7);
        }
        return null;
    }
}