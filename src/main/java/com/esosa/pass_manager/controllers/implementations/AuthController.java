package com.esosa.pass_manager.controllers.implementations;

import com.esosa.pass_manager.controllers.interfaces.IAuthController;
import com.esosa.pass_manager.controllers.request.AuthRequest;
import com.esosa.pass_manager.controllers.response.AuthResponse;
import com.esosa.pass_manager.services.interfaces.IAuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController implements IAuthController {
    private final IAuthService authService;

    public AuthController(IAuthService authService) {
        this.authService = authService;
    }

    @Override
    public void register(@RequestBody @Valid AuthRequest registerRequest) {
        authService.register(registerRequest);
    }

    @Override
    public AuthResponse login(@RequestBody @Valid AuthRequest authRequest) {
        return authService.login(authRequest);
    }
}