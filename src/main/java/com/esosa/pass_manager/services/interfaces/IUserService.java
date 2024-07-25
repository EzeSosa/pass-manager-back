package com.esosa.pass_manager.services.interfaces;

import com.esosa.pass_manager.controllers.request.AuthRequest;
import com.esosa.pass_manager.controllers.response.PasswordResponse;
import com.esosa.pass_manager.data.model.User;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

public interface IUserService {
    Page<PasswordResponse> getUserPasswords(UUID userId, int pageNumber, int size);
    User findUserByIdOrThrowException(UUID userId);
    void saveUser(AuthRequest authRequest, PasswordEncoder passwordEncoder);
    User findUserByUsernameOrThrowException(String username);
    boolean userHasPasswordName(User user, String passwordName);
}