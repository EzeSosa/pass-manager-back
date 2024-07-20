package com.esosa.pass_manager.controllers;

import com.esosa.pass_manager.controllers.request.AuthRequest;
import com.esosa.pass_manager.controllers.response.AuthResponse;
import com.esosa.pass_manager.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@CrossOrigin("http://localhost:3000")
@Validated
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody @Valid AuthRequest registerRequest) {
        authService.register(registerRequest);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthResponse login(@RequestBody @Valid AuthRequest authRequest) {
        return authService.login(authRequest);
    }
}