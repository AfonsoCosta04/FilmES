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
            Integer tipo = jwtUtil.extractClaim(token, claims -> Integer.parseInt(claims.get("tipoUtilizador").toString()));
            return tipo != null && tipo.equals(1);
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isFuncionario(HttpServletRequest request) {
        String token = extractToken(request);
        if (token == null) return false;

        try {
            Integer tipo = jwtUtil.extractClaim(token, claims -> Integer.parseInt(claims.get("tipoUtilizador").toString()));
            return tipo != null && tipo.equals(2);
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isCliente(HttpServletRequest request) {
        String token = extractToken(request);
        if (token == null) return false;

        try {
            Integer tipo = jwtUtil.extractClaim(token, claims -> Integer.parseInt(claims.get("tipoUtilizador").toString()));
            return tipo != null && tipo.equals(3);
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

    public static Integer getUserId(HttpServletRequest request) {
        String token = extractToken(request);
        if (token == null) return null;
        try {
            // supondo que o seu JWT inclui a claim "idCliente"
            return jwtUtil.extractClaim(token,
                    claims -> Integer.parseInt(claims.get("idCliente").toString()));
        } catch (Exception ex) {
            return null;
        }
    }
}
