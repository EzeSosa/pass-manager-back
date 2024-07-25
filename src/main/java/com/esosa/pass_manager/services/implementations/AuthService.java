package com.esosa.pass_manager.services.implementations;

import com.esosa.pass_manager.controllers.request.AuthRequest;
import com.esosa.pass_manager.controllers.response.AuthResponse;
import com.esosa.pass_manager.security.jwt.JWTService;
import com.esosa.pass_manager.services.interfaces.IAuthService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class AuthService implements IAuthService {
    private final UserService userService;
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthService(
            UserService userService,
            UserDetailsService userDetailsService,
            AuthenticationManager authenticationManager,
            JWTService jwtService, PasswordEncoder passwordEncoder
    ) {
        this.userService = userService;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    @Value("${jwt.access-token-expiration}")
    private Long accessTokenExpiration;

    @Override
    public void register(AuthRequest registerRequest) {
        userService.saveUser(registerRequest, passwordEncoder);
    }

    @Override
    public AuthResponse login(AuthRequest authRequest) {
        authenticateUser(authRequest);

        UUID userId = extractIdFromUsername(authRequest.username());
        UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.username());
        String accessToken = generateAccessToken(userDetails);

        return new AuthResponse(userId, accessToken);
    }

    private void authenticateUser(AuthRequest authRequest) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password());
        authenticationManager.authenticate(authToken);
    }

    private String generateAccessToken(UserDetails userDetails) {
        Date expirationDate = new Date(System.currentTimeMillis() + accessTokenExpiration);
        return jwtService.generateToken(userDetails, expirationDate);
    }

    private UUID extractIdFromUsername(String username) {
        return userService.findUserByUsernameOrThrowException(username)
                .getId();
    }
}