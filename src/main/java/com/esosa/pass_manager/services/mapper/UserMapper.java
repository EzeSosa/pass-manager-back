package com.esosa.pass_manager.services.mapper;

import com.esosa.pass_manager.controllers.request.AuthRequest;
import com.esosa.pass_manager.data.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserMapper {
    public static User buildUser(AuthRequest registerRequest, PasswordEncoder passwordEncoder) {
        String encodedPassword = passwordEncoder.encode(registerRequest.password());
        return new User(registerRequest.username(), encodedPassword);
    }
}