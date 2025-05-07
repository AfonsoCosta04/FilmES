package com.filmees.backend.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtil {

    private static JwtUtil jwtUtil;

    @Autowired
    public SecurityUtil(JwtUtil jwtUtil) {
        SecurityUtil.jwtUtil = jwtUtil;
    }

    private static String extractToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

    public static boolean isAdmin(HttpServletRequest request) {
        String token = extractToken(request);
        if (token == null) return false;

        try {
            Integer tipo = jwtUtil.extractClaim(token, "tipoUtilizador", Integer.class);
            return tipo != null && tipo == 1;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isFuncionario(HttpServletRequest request) {
        String token = extractToken(request);
        if (token == null) return false;

        try {
            Integer tipo = jwtUtil.extractClaim(token, "tipoUtilizador", Integer.class);
            return tipo != null && tipo == 2;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isCliente(HttpServletRequest request) {
        String token = extractToken(request);
        if (token == null) return false;

        try {
            Integer tipo = jwtUtil.extractClaim(token, "tipoUtilizador", Integer.class);
            return tipo != null && tipo == 3;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isProprio(HttpServletRequest request, String emailDono) {
        String token = extractToken(request);
        if (token == null) return false;

        try {
            String email = jwtUtil.extractUsername(token);
            return email != null && email.equals(emailDono);
        } catch (Exception e) {
            return false;
        }
    }
}
