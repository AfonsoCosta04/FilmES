package com.filmees.backend.security;

import jakarta.servlet.http.HttpServletRequest;

public class SecurityUtil {

    public static boolean isAdmin(HttpServletRequest request) {
        Integer tipo = (Integer) request.getAttribute("tipoUtilizador");
        return tipo != null && tipo == 1;
    }

    public static boolean isFuncionario(HttpServletRequest request) {
        Integer tipo = (Integer) request.getAttribute("tipoUtilizador");
        return tipo != null && tipo == 2;
    }

    public static boolean isCliente(HttpServletRequest request) {
        Integer tipo = (Integer) request.getAttribute("tipoUtilizador");
        return tipo != null && tipo == 3;
    }

    public static boolean isProprio(HttpServletRequest request, String emailDono) {
        String email = (String) request.getAttribute("emailAutenticado");
        return email != null && email.equals(emailDono);
    }
}
