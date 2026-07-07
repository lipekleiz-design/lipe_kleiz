package com.lipe_kleiz.delivery_api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lipe_kleiz.delivery_api.dto.auth.AuthRequest;
import com.lipe_kleiz.delivery_api.dto.auth.AuthResponse;
import com.lipe_kleiz.delivery_api.security.JwtUtil;
import com.lipe_kleiz.delivery_api.service.UserDetailsServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final UserDetailsServiceImpl userDetailsService;

    private final JwtUtil jwtUtil;

    public AuthController(
            AuthenticationManager authenticationManager,
            UserDetailsServiceImpl userDetailsService,
            JwtUtil jwtUtil) {

        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody AuthRequest authRequest) {

        authenticationManager.authenticate(

                new UsernamePasswordAuthenticationToken(

                        authRequest.getEmail(),

                        authRequest.getSenha()));

        UserDetails userDetails =
                userDetailsService.loadUserByUsername(
                        authRequest.getEmail());

        String token =
                jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(

                new AuthResponse(token));
    }

}