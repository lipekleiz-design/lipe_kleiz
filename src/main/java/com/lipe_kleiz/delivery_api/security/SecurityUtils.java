package com.lipe_kleiz.delivery_api.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.lipe_kleiz.delivery_api.entity.Usuario;

public final class SecurityUtils {

    private SecurityUtils() {
    }

    public static Usuario getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null &&
                authentication.getPrincipal() instanceof Usuario usuario) {

            return usuario;
        }

        throw new IllegalStateException("Usuário não autenticado.");

    }

    public static Long getCurrentUserId() {

        return getCurrentUser().getId();

    }

    public static String getCurrentUserEmail() {

        return getCurrentUser().getEmail();

    }

    public static String getCurrentUserRole() {

        return getCurrentUser().getRole().name();

    }

    public static boolean hasRole(String role) {

        return getCurrentUser()
                .getRole()
                .name()
                .equalsIgnoreCase(role);

    }

    public static boolean isAdmin() {

        return hasRole("ADMIN");

    }

    public static boolean isCliente() {

        return hasRole("CLIENTE");

    }

    public static boolean isRestaurante() {

        return hasRole("RESTAURANTE");

    }

    public static boolean isEntregador() {

        return hasRole("ENTREGADOR");

    }

}