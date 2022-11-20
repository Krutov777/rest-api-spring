package ru.blog.security.utils;

import org.springframework.security.core.Authentication;

import java.util.Map;

public interface JwtUtil {
    Map<String, String> generateTokens(String subject, String authority, String issuer);

    Authentication buildAuthentication(String token);
}

